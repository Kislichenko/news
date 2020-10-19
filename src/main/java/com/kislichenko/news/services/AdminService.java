package com.kislichenko.news.services;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.dao.RoleRepository;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {
    private RoleRepository roleRepository;
    private AppUserRepository appUserRepository;

    public AdminService(
            RoleRepository roleRepository,
            AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
    }

    public List<String> getAllRoles(){
        List<String> roleNames = new ArrayList<>();
        List<Role> roles =  roleRepository.findAll();
        roles.forEach(role -> roleNames.add(role.getName()));
        return roleNames;
    }

    public boolean changeRoles(String username, List<String> newRoles){
        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser == null) {
            return false;
        }

        Set<Role> roles = new HashSet<>();

        for(String newRole : newRoles){
            Role role = roleRepository.findByName(newRole);
            if(role == null){
                continue;
            }
            roles.add(role);
        }
        appUser.setRoles(roles);
        appUserRepository.save(appUser);

        return true;
    }
}
