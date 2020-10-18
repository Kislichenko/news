package com.kislichenko.news.controller;

import com.kislichenko.news.dao.RoleRepository;
import com.kislichenko.news.dto.ChangeRole;
import com.kislichenko.news.entity.Client;
import com.kislichenko.news.entity.Role;
import com.kislichenko.news.exceptions.ClientNotFoundException;
import com.kislichenko.news.services.AdminService;
import com.kislichenko.news.services.AppUserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
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
