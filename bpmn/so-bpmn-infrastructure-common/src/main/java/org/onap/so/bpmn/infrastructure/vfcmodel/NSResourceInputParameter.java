/*-
 * ============LICENSE_START=======================================================
 * ONAP - SO
 * ================================================================================
 * Copyright (C) 2018 CMCC All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.so.bpmn.infrastructure.vfcmodel;



/**
 * NS Create Input Parameter For VFC Adapter<br>
 * <p>
 * </p>
 *
 * @version ONAP Beijing Release 2018/2/5
 */
public class NSResourceInputParameter {

    private NsOperationKey nsOperationKey;

    private String nsServiceName;

    private String nsServiceDescription;

    private NsParameters nsParameters;

    private NsScaleParameters nsScaleParameters;



    /**
     * @return Returns the nsServiceName.
     */
    public String getNsServiceName() {
        return nsServiceName;
    }


    /**
     * @param nsServiceName The nsServiceName to set.
     */
    public void setNsServiceName(String nsServiceName) {
        this.nsServiceName = nsServiceName;
    }


    /**
     * @return Returns the nsServiceDescription.
     */
    public String getNsServiceDescription() {
        return nsServiceDescription;
    }


    /**
     * @param nsServiceDescription The nsServiceDescription to set.
     */
    public void setNsServiceDescription(String nsServiceDescription) {
        this.nsServiceDescription = nsServiceDescription;
    }

    /**
     * @return Returns the nsParameters.
     */
    public NsParameters getNsParameters() {
        return nsParameters;
    }

    /**
     * @param nsParameters The nsParameters to set.
     */
    public void setNsParameters(NsParameters nsParameters) {
        this.nsParameters = nsParameters;
    }

    public NsOperationKey getNsOperationKey() {
        return nsOperationKey;
    }

    public void setNsOperationKey(NsOperationKey nsOperationKey) {
        this.nsOperationKey = nsOperationKey;
    }

    /**
     * @return Returns the nsScaleParameters.
     */
    public NsScaleParameters getNsScaleParameters() {
        return nsScaleParameters;
    }

    /**
     * @param nsScaleParameters The nsScaleParameters to set.
     */
    public void setNsScaleParameters(NsScaleParameters nsScaleParameters) {
        this.nsScaleParameters = nsScaleParameters;
    }

}
