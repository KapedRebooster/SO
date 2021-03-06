/*-
 * ============LICENSE_START=======================================================
 * OPENECOMP - SO
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Modifications Copyright (C) 2018 IBM.
 * Modifications Copyright (c) 2019 Samsung
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

package org.onap.so.adapters.cloudregion;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.onap.so.db.catalog.beans.CloudSite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Path("/v1/cloud-region")
@Api(value = "/v1/cloud-region", description = "root of cloud region adapter")
@Component
public class CloudRegionRestV1 {
    private static Logger logger = LoggerFactory.getLogger(CloudRegionRestV1.class);

    @Autowired
    private CloudRestImpl cloudRestImpl;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "CreateCloudRegion", response = Response.class,
            notes = "Create a cloud site in MSO and Region In AAI")
    @ApiResponses({@ApiResponse(code = 201, message = "Cloud Region has been created"),
            @ApiResponse(code = 500, message = "Create Cloud Region has failed")})
    public Response createCloudRegion(
            @ApiParam(value = "cloud-region-id", required = true) @PathParam("cloud-region-id") String cloudRegionId,
            @ApiParam(value = "CloudSite", required = true) final CloudSite cloudSite) {
        cloudRestImpl.createCloudRegion(cloudSite);
        return Response.status(HttpStatus.SC_CREATED).build();
    }

    @DELETE
    @Path("{cloud-region-id}/{cloud-owner}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "CreateCloudRegion", response = Response.class, notes = "Delete an cloud Region in SO")
    @ApiResponses({@ApiResponse(code = 204, message = "cloud Region has been deleted"),
            @ApiResponse(code = 500, message = "Cloud Region delete has failed")})
    public Response deleteCloudRegion(
            @ApiParam(value = "cloud-region-id", required = true) @PathParam("cloud-region-id") String cloudRegionId,
            @ApiParam(value = "cloud-owner", required = true) @PathParam("cloud-owner") String cloudOwner) {
        cloudRestImpl.deleteCloudRegion(cloudRegionId);
        return Response.status(HttpStatus.SC_NO_CONTENT).build();
    }

    @PUT
    @Path("{cloud-region-id}/{cloud-owner}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "CreateCloudRegion", response = Response.class, notes = "Update an existing Cloud Region")
    @ApiResponses({@ApiResponse(code = 200, message = "Cloud Region has been updated"),
            @ApiResponse(code = 500, message = "Update Cloud Region has failed examine entity object for details")})
    public Response updateCloudRegion(
            @ApiParam(value = "cloud-region-id", required = true) @PathParam("cloud-region-id") String cloudRegionId,
            @ApiParam(value = "cloud-owner", required = true) @PathParam("cloud-owner") String cloudOwner,
            @ApiParam(value = "CloudSite", required = true) final CloudSite cloudSite) {
        cloudRestImpl.updateCloudRegion(cloudSite);
        return Response.status(HttpStatus.SC_OK).build();
    }
}
