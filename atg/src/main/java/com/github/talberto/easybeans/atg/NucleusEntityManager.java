/**
 * Copyright 2014 Tomas Rodriguez 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *  
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License. 
 */

package com.github.talberto.easybeans.atg;

import java.util.Map;

import javax.transaction.TransactionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import atg.dtm.TransactionDemarcation;
import atg.dtm.TransactionDemarcationException;
import atg.nucleus.GenericService;
import atg.repository.RepositoryItem;

import com.github.talberto.easybeans.api.EntityManager;
import com.github.talberto.easybeans.api.MappingException;
import com.google.common.collect.Maps;

/**
 * 
 * @author Tomas Rodriguez (rodriguez@progiweb.com)
 *
 */
public class NucleusEntityManager extends GenericService implements EntityManager {

  protected Logger mLog = LoggerFactory.getLogger(this.getClass());
  protected Map<Class<?>, BeanMapper<?>> mMapperForType = Maps.newConcurrentMap();
  protected TransactionManager mTransactionManager;
  
  @Override
  public <T> T find(Class<T> pType, Object pPk) {
    mLog.trace("Entering find");
    return findMapperFor(pType).find(pPk);
  }

  protected <T> BeanMapper<T> findMapperFor(Class<T> pType) {
    mLog.trace("Entering findMapperFor({})", pType);
    // Look first in the map
    mLog.debug("Looking for a RepositoryBeanMapper for the type {}", pType);
    @SuppressWarnings("unchecked")
    BeanMapper<T> mapper = (BeanMapper<T>) mMapperForType.get(pType);
    if(mapper == null) {
      mLog.debug("RepositoryBeanMapper for type {} not found, creating one", pType);
      mapper = BeanMapper.create(this, pType);
      mMapperForType.put(pType, mapper);
    }
    return mapper;
  }
  
  @Override
  public <T> String create(T pBean) {
    mLog.trace("Entering create");
    TransactionDemarcation td = new TransactionDemarcation();
    boolean rollback = true;
    try {
      td.begin(mTransactionManager, TransactionDemarcation.REQUIRED);
      @SuppressWarnings("unchecked")
      Class<T> type = (Class<T>) pBean.getClass();
      String id = findMapperFor(type).create(pBean);
      rollback = false;
      return id;
    } catch (TransactionDemarcationException e) {
      throw new MappingException(e);
    } finally {
      try {
        td.end(rollback);
      } catch (TransactionDemarcationException e) {
        throw new MappingException(e);
      }
    }
  }

  @Override
  public <T> void update(T pBean) {
    mLog.trace("Entering update");
    TransactionDemarcation td = new TransactionDemarcation();
    boolean rollback = true;
    try {
      td.begin(mTransactionManager, TransactionDemarcation.REQUIRED);
      @SuppressWarnings("unchecked")
      Class<T> type = (Class<T>) pBean.getClass();
      findMapperFor(type).update(pBean);
      rollback = false;
    } catch (TransactionDemarcationException e) {
      throw new MappingException(e);
    } finally {
      try {
        td.end(rollback);
      } catch (TransactionDemarcationException e) {
        throw new MappingException(e);
      }
    }
  }

  /**
   * @return the transactionManager
   */
  public TransactionManager getTransactionManager() {
    return mTransactionManager;
  }

  /**
   * @param pTransactionManager the transactionManager to set
   */
  public void setTransactionManager(TransactionManager pTransactionManager) {
    mTransactionManager = pTransactionManager;
  }

  @Override
  public <T> void delete(T pBean) {
    delete(pBean, false);
  }

  @Override
  public <T> void delete(T pBean, boolean pDeleteNested) {
    mLog.trace("Entering delete");
    TransactionDemarcation td = new TransactionDemarcation();
    boolean rollback = true;
    try {
      td.begin(mTransactionManager, TransactionDemarcation.REQUIRED);
      @SuppressWarnings("unchecked")
      Class<T> type = (Class<T>) pBean.getClass();
      findMapperFor(type).delete(pBean, pDeleteNested);
      rollback = false;
    } catch (TransactionDemarcationException e) {
      throw new MappingException(e);
    } finally {
      try {
        td.end(rollback);
      } catch (TransactionDemarcationException e) {
        throw new MappingException(e);
      }
    }
  }
  
  protected String getBeanId(Object pBean) {
    @SuppressWarnings("unchecked")
    BeanMapper<Object> mapper = findMapperFor((Class<Object>) pBean.getClass());
    return mapper.getBeanId(pBean);
  }

  protected RepositoryItem repositoryItemForBean(Object pBean) {
    @SuppressWarnings("unchecked")
    BeanMapper<Object> mapper = findMapperFor((Class<Object>) pBean.getClass());
    
    return mapper.repositoryItemForBean(pBean);
  }

  public Object toBean(RepositoryItem pItem, Class<?> pType) {
    BeanMapper<?> beanMapper = findMapperFor(pType);
    
    return beanMapper.toBean(pItem);
  }
}
