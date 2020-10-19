package com.kislichenko.news.controller;

import com.kislichenko.news.dto.ChangeRole;
import com.kislichenko.news.services.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/change-role", method = RequestMethod.POST)
    public ResponseEntity<String> changeRole(@RequestBody ChangeRole changeRole) {
        logger.debug("Changing role for " + changeRole.getUsername());
        adminService.changeRoles(changeRole.getUsername(), changeRole.getRoles());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("roles")
    public ResponseEntity<Object> getRoles() {
        logger.debug("Getting all roles");
        return new ResponseEntity<>(adminService.getAllRoles(), HttpStatus.OK);
    }
}
