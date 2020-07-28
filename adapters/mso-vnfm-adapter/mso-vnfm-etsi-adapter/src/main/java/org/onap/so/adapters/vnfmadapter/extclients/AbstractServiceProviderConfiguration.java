/*-
 * ============LICENSE_START=======================================================
 *  Copyright (C) 2019 Nordix Foundation.
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

package org.onap.so.adapters.vnfmadapter.extclients;

import java.time.LocalDateTime;
import com.google.gson.Gson;
import java.util.Iterator;
import org.onap.so.adapters.vnfmadapter.extclients.vnfm.packagemanagement.JSON;
import org.onap.so.adapters.vnfmadapter.rest.EtsiSubscriptionNotificationController;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.threeten.bp.OffsetDateTime;

/**
 * A base class that can be extended by classes for configuring HttpRestServiceProvider classes. Provides common methods
 * that will be useful to some such classes.
 *
 * @author gareth.roper@est.tech
 */
public abstract class AbstractServiceProviderConfiguration {
    private final JSON.OffsetDateTimeTypeAdapter offsetDateTimeTypeAdapter = new JSON.OffsetDateTimeTypeAdapter();

    public void setGsonMessageConverter(final RestTemplate restTemplate) {
        final Iterator<HttpMessageConverter<?>> iterator = restTemplate.getMessageConverters().iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof MappingJackson2HttpMessageConverter) {
                iterator.remove();
            }
        }
        final Gson gson = JSON.createGson().registerTypeAdapter(OffsetDateTime.class, offsetDateTimeTypeAdapter)
                .registerTypeAdapter(LocalDateTime.class,
                        new EtsiSubscriptionNotificationController.LocalDateTimeTypeAdapter())
                .create();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter(gson));
    }
}
