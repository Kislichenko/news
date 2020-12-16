package com.kislichenko.news.services;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.dao.RoleRepository;
import com.kislichenko.news.dto.AppUserDTO;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserService {
    ModelMapper modelMapper = new ModelMapper();
    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    public AppUserService(
            AppUserRepository appUserRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            RoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;

        if (appUserRepository.findByUsername("admin") == null) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("password"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
            System.out.println(roleRepository.findByName("ROLE_ADMIN").getId());
            admin.setRoles(roles);
            appUserRepository.save(admin);
        }
    }

    public void registration(AppUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roles);

        appUserRepository.save(user);
    }

    public AppUserDTO getUserByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser != null) {
            AppUserDTO appUserDTO = modelMapper.map(appUser, AppUserDTO.class);
            return appUserDTO;
        }
        return null;
    }
}
