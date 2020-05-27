package com.lzp.aas.controller;

import com.lzp.aas.config.Constants;
import com.lzp.aas.controller.form.RequestCreationForm;
import com.lzp.aas.model.Request;
import com.lzp.aas.model.enums.RequestStatus;
import com.lzp.aas.service.request.RequestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @ApiOperation(value = "index requests", authorizations = {@Authorization(Constants.JWT_AUTH)})
    @GetMapping("/requests")
    public ResponseEntity<List<Request>> indexRequests(
            @RequestHeader("authorization") String authorization) {
        return new ResponseEntity<>(requestService.index(), HttpStatus.OK);
    }

    @ApiOperation(value = "open request", authorizations = {@Authorization(Constants.JWT_AUTH)})
    @PostMapping("/requests/{userId}")
    public ResponseEntity<Request> createRequest(
            @RequestHeader("authorization") String authorization,
            @PathVariable("userId") Long authorId,
            @RequestBody RequestCreationForm requestCreationForm) {
        return new ResponseEntity<>(requestService.createRequest(authorId, requestCreationForm), HttpStatus.OK);
    }

    @ApiOperation(value = "open request", authorizations = {@Authorization(Constants.JWT_AUTH)})
    @PatchMapping("/requests/{id}")
    public ResponseEntity<Void> updateRequestStatus(
            @RequestHeader("authorization") String authorization,
            @PathVariable("id") Long requestId,
            @RequestParam RequestStatus newRequestStatus) {
        requestService.updateRequestStatus(requestId, newRequestStatus);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
