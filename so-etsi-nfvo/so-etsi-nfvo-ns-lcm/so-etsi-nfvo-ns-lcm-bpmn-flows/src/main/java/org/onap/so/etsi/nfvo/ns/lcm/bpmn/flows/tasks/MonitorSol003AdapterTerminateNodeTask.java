/*-
 * ============LICENSE_START=======================================================
 *  Copyright (C) 2020 Nordix Foundation.
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
 *
 * SPDX-License-Identifier: Apache-2.0
 * ============LICENSE_END=========================================================
 */
package org.onap.so.etsi.nfvo.ns.lcm.bpmn.flows.tasks;

import org.onap.so.etsi.nfvo.ns.lcm.bpmn.flows.extclients.aai.AaiServiceProvider;
import org.onap.so.etsi.nfvo.ns.lcm.database.service.DatabaseServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Waqas Ikram (waqas.ikram@est.tech)
 * @author Andrew Lamb (andrew.a.lamb@est.tech)
 *
 */
@Component
public class MonitorSol003AdapterTerminateNodeTask extends MonitorSol003AdapterNodeTask {

    public static final String DELETE_VNF_NODE_STATUS = "deleteVnfNodeStatus";
    public static final String VNF_ASSIGNED = "Assigned";

    @Autowired
    public MonitorSol003AdapterTerminateNodeTask(final DatabaseServiceProvider databaseServiceProvider,
            final AaiServiceProvider aaiServiceProvider) {
        super(databaseServiceProvider, aaiServiceProvider);
    }

    @Override
    public String getNodeStatusVariableName() {
        return DELETE_VNF_NODE_STATUS;
    }

    @Override
    public boolean isOrchestrationStatusValid(final String orchestrationStatus) {
        return VNF_ASSIGNED.equalsIgnoreCase(orchestrationStatus);
    }

}
