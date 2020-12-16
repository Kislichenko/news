package com.kislichenko.news.controller;

import com.kislichenko.news.dto.ReportageDTO;
import com.kislichenko.news.services.ReportageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportageController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    ReportageService reportageService;

    public ReportageController(ReportageService reportageService) {
        this.reportageService = reportageService;
    }

    @Secured("ROLE_INFO_MANAGER")
    @RequestMapping(value = "/reportages", method = RequestMethod.POST)
    public ResponseEntity<String> addNewReportage(@RequestBody ReportageDTO reportageDto) {
        logger.debug("Adding new reportage by " + reportageDto.getInfoManager());
        reportageService.addNewReportage(reportageDto);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Secured({"ROLE_REPORTER", "ROLE_INFO_MANAGER"})
    @RequestMapping(value = "/reportages/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<String> patchReportageById(@PathVariable Long id, @RequestBody ReportageDTO reportageDto) {
        logger.debug("Patching the reportage");
        reportageService.addNewReportage(reportageDto);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Secured({"ROLE_REPORTER", "ROLE_INFO_MANAGER"})
    @GetMapping("reportages")
    public ResponseEntity<Object> getReportages() {
        logger.debug("Getting all reportages");
        //System.out.println(reportageService.getAllReportages());
        return new ResponseEntity<>(reportageService.getAllReportages(), HttpStatus.OK);
    }

    @Secured({"ROLE_REPORTER", "ROLE_INFO_MANAGER"})
    @DeleteMapping("reportages/{id}")
    public ResponseEntity<Object> deleteReportage(@PathVariable Long id) {
        logger.debug("Deleting reportage by id = " + id);
        reportageService.deleteReportageById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Secured({"ROLE_REPORTER", "ROLE_INFO_MANAGER"})
    @GetMapping("reportages/{id}")
    public ResponseEntity<ReportageDTO> getReportageById(@PathVariable Long id) {
        logger.debug("Getting reportage by id = " + id);
        ReportageDTO reportageDto = reportageService.getReportageById(id);
        return new ResponseEntity<>(reportageDto, HttpStatus.OK);
    }
}
