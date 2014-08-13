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

package com.github.talberto.easybeans.api;

/**
 * 
 * @author Tomas Rodriguez (rodriguez@progiweb.com)
 *
 */
public class MappingException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 5921314140734021682L;

  public MappingException() {
    super();
  }

  public MappingException(String pMessage, Throwable pCause) {
    super(pMessage, pCause);
  }

  public MappingException(String pMessage) {
    super(pMessage);
  }

  public MappingException(Throwable pCause) {
    super(pCause);
  }

}
