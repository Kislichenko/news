package com.kislichenko.news.controller;

import com.kislichenko.news.dto.ReqDataDto;
import com.kislichenko.news.services.ReqDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReqDataController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);

    ReqDataService reqDataService;

    public ReqDataController(ReqDataService reqDataService) {
        this.reqDataService = reqDataService;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/requests", method = RequestMethod.POST)
    public ResponseEntity<String> addNewRequest(@RequestBody ReqDataDto reqDataDto) {
        logger.debug("Adding new request by " + reqDataDto.getCreator());
        reqDataService.addNewRequest(reqDataDto);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/requests/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<String> patchRequestById(@PathVariable Long id, @RequestBody ReqDataDto reqDataDto) {
        logger.debug("Patching the request by " + reqDataDto.getCreator());
        reqDataService.addNewRequest(reqDataDto);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_AD_MANAGER"})
    @GetMapping("requests")
    public ResponseEntity<Object> getRequests() {
        logger.debug("Getting all requests");
        return new ResponseEntity<>(reqDataService.getAllRequests(), HttpStatus.OK);
    }

    @Secured({"ROLE_USER", "ROLE_AD_MANAGER"})
    @DeleteMapping("requests/{id}")
    public ResponseEntity<Object> deleteRequest(@PathVariable Long id) {
        logger.debug("Deleting request by id = " + id);
        reqDataService.deleteRequestById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("requests/{id}")
    public ResponseEntity<ReqDataDto> getRequestById(@PathVariable Long id) {
        logger.debug("Getting request by id = " + id);
        ReqDataDto reqDataDto = reqDataService.getRequestById(id);
        return new ResponseEntity<>(reqDataDto, HttpStatus.OK);
    }
}
