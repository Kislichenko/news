package com.kislichenko.news.controller;

import com.kislichenko.news.dto.ChangeRole;
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

    public ReqDataController(ReqDataService reqDataService){
        this.reqDataService = reqDataService;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/requests", method = RequestMethod.POST)
    public ResponseEntity<String> changeRole(@RequestBody ReqDataDto reqDataDto) {
        logger.debug("Adding new request by " + reqDataDto.getCreator());
        reqDataService.addNewRequest(reqDataDto);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("requests")
    public ResponseEntity<Object> getRoles() {
        logger.debug("Getting all requests");
        return new ResponseEntity<>(reqDataService.getAllRequests(), HttpStatus.OK);
    }
}