/*-
 * ============LICENSE_START=======================================================
 * ONAP - SO
 * ================================================================================
 * Copyright (C) 2020 Huawei Technologies Co., Ltd. All rights reserved.
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
package org.onap.so.adapters.cnf.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = "true")
public class InstanceStatusResponse extends Response {

    public InstanceStatusResponse(String errorMsg) {
        super(errorMsg);
    }

    private MulticloudInstanceRequest request;

    private boolean ready;

    private String resourceCount;

    private List<PodStatus> podStatuses;

    private List<?> servicesStatuses;

    public MulticloudInstanceRequest getRequest() {
        return request;
    }

    public void setRequest(MulticloudInstanceRequest request) {
        this.request = request;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(String resourceCount) {
        this.resourceCount = resourceCount;
    }

    public List<PodStatus> getPodStatuses() {
        return podStatuses;
    }

    public void setPodStatuses(List<PodStatus> podStatuses) {
        this.podStatuses = podStatuses;
    }

    public List<?> getServicesStatuses() {
        return servicesStatuses;
    }

    public void setServicesStatuses(List<?> servicesStatuses) {
        this.servicesStatuses = servicesStatuses;
    }

}
