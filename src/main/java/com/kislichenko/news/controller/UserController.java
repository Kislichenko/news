package com.kislichenko.news.controller;

import com.kislichenko.news.dto.AppUserDTO;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.services.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    private AppUserService appUserService;

    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@Valid @RequestBody AppUser user) {
        logger.debug("SIGN-UP");
        appUserService.registration(user);
    }

    @Secured({"ROLE_USER", "ROLE_AD_MANAGER", "ROLE_REPORTER", "ROLE_INFO_MANAGER"})
    @GetMapping("users/{username}")
    public ResponseEntity<AppUserDTO> getUserById(@PathVariable String username) {
        logger.debug("Getting user by username = " + username);
        AppUserDTO appUserDTO = appUserService.getUserByUsername(username);
        return new ResponseEntity<>(appUserDTO, HttpStatus.OK);
    }

}

