package org.onap.so.adapters.nssmf.controller;

import org.onap.so.adapters.nssmf.service.NssmfManagerService;
import org.onap.so.beans.nsmf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping(value = "/api/rest/provMns/v1", produces = {APPLICATION_JSON}, consumes = {APPLICATION_JSON})
public class NssmfAdapterController {

    private static final Logger logger = LoggerFactory.getLogger(NssmfAdapterController.class);

    @Autowired
    private NssmfManagerService nssmfManagerService;

    @PostMapping(value = "/NSS/SliceProfiles")
    public ResponseEntity allocateNssi(@RequestBody NssmfAdapterNBIRequest allocateRequest) {
        return nssmfManagerService.allocateNssi(allocateRequest);
    }

    @PostMapping(value = "/NSS/SliceProfiles/{sliceProfileId}")
    public ResponseEntity deAllocateNssi(@RequestBody NssmfAdapterNBIRequest deAllocateRequest,
            @PathVariable("sliceProfileId") final String sliceId) {
        return nssmfManagerService.deAllocateNssi(deAllocateRequest, sliceId);
    }


    @PostMapping(value = "/NSS/{snssai}/activation")
    public ResponseEntity activateNssi(@RequestBody NssmfAdapterNBIRequest actDeActRequest,
            @PathVariable("snssai") String snssai) {
        return nssmfManagerService.activateNssi(actDeActRequest, snssai);
    }

    @PostMapping(value = "/NSS/{snssai}/deactivation")
    public ResponseEntity deactivateNssi(@RequestBody NssmfAdapterNBIRequest deActRequest,
            @PathVariable("snssai") String snssai) {
        return nssmfManagerService.deActivateNssi(deActRequest, snssai);
    }

    @PostMapping(value = "/NSS/jobs/{jobId}")
    public ResponseEntity queryJobStatus(@RequestBody JobStatusRequest jobStatusReq,
            @PathVariable("jobId") String jobId) {
        return nssmfManagerService.queryJobStatus(jobStatusReq, jobId);
    }

    @PutMapping(value = "/NSS/SliceProfiles/{sliceProfileId}")
    public ResponseEntity modifyNssi(@RequestBody NssmfAdapterNBIRequest updateRequest,
            @PathVariable("sliceProfileId") String sliceId) {
        return nssmfManagerService.modifyNssi(updateRequest, sliceId);
    }

}
