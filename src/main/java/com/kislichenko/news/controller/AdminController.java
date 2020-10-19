package com.kislichenko.news.controller;

import com.kislichenko.news.dto.ChangeRole;
import com.kislichenko.news.services.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/change-role", method = RequestMethod.POST)
    public ResponseEntity<String> changeRole(@RequestBody ChangeRole changeRole){
        adminService.changeRoles(changeRole.getUsername(), changeRole.getRoles());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("roles")
    public ResponseEntity<Object> getRoles()  {
        return new ResponseEntity<>(adminService.getAllRoles(), HttpStatus.OK);
    }
}
