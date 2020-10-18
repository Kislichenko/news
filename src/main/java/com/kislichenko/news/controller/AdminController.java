package com.kislichenko.news.controller;

import com.kislichenko.news.dto.ChangeRole;
import com.kislichenko.news.services.AdminService;
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
    public void changeRole(@RequestBody ChangeRole changeRole){
        System.out.println(changeRole.getUsername());
        adminService.changeRoles(changeRole.getUsername(), changeRole.getRoles());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("roles")
    public List<String> getRoles()  {
        return adminService.getAllRoles();
    }
}
