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

package com.github.talberto.easybeans.gen;

import com.google.common.base.CaseFormat;

/**
 * 
 * @author Tomás Rodríguez (rstomasalberto@gmail.com)
 * 
 */
public class MetaSetter {
  MetaProperty mProperty;
  MetaInstanceVar mInstanceVariable;
  String mName;
  MetaType mType;
  String mArgument;

  public MetaSetter(MetaProperty pProperty, MetaInstanceVar pInstanceVariable) {
    mProperty = pProperty;
    mInstanceVariable = pInstanceVariable;
    mName = "set" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, getPropertyName());
    mType = mInstanceVariable.getType();
    mArgument = getInstanceVariableName().replaceFirst("m", "p");
  }

  public MetaInstanceVar getInstanceVariable() {
    return mInstanceVariable;
  }

  public String getInstanceVariableName() {
    return mInstanceVariable.getName();
  }
  
  public String getName() {
    return mName;
  }

  public String getArgument() {
    return mArgument;
  }
  
  public String getTypeName() {
    return mType.getName();
  }
  
  public String getPropertyName() {
    return mProperty.getName();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "MetaSetter [mProperty=" + mProperty + ", mInstanceVariable=" + mInstanceVariable + ", mName=" + mName + ", mType=" + mType
        + ", mArgument=" + mArgument + "]";
  }
}
