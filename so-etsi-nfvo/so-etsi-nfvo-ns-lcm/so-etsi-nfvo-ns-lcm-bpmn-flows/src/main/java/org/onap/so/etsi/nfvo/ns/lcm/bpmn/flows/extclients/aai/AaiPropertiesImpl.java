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

package org.onap.so.etsi.nfvo.ns.lcm.bpmn.flows.extclients.aai;

import org.onap.aaiclient.client.aai.AAIProperties;
import org.onap.aaiclient.client.aai.AAIVersion;
import org.onap.so.spring.SpringContextHelper;
import org.springframework.context.ApplicationContext;
import java.net.MalformedURLException;
import java.net.URL;

public class AaiPropertiesImpl implements AAIProperties {

    private final String endpoint;
    private final String encryptedBasicAuth;
    private final String encryptionKey;
    private final String aaiVersion;

    public AaiPropertiesImpl() {

        final ApplicationContext context = SpringContextHelper.getAppContext();
        this.endpoint = context.getEnvironment().getProperty("aai.endpoint");
        this.encryptedBasicAuth = context.getEnvironment().getProperty("aai.auth");
        this.encryptionKey = context.getEnvironment().getProperty("mso.key");
        this.aaiVersion = context.getEnvironment().getProperty("aai.version");
    }

    @Override
    public URL getEndpoint() throws MalformedURLException {
        return new URL(endpoint);
    }

    @Override
    public String getSystemName() {
        return "MSO";
    }

    @Override
    public AAIVersion getDefaultVersion() {
        for (final AAIVersion version : AAIVersion.values()) {
            if (version.toString().equalsIgnoreCase(this.aaiVersion)) {
                return version;
            } ;

        }
        return AAIVersion.LATEST;
    }

    @Override
    public String getAuth() {
        return encryptedBasicAuth;
    }

    @Override
    public String getKey() {
        return encryptionKey;
    }
}
