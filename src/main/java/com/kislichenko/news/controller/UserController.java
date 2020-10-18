package com.kislichenko.news.controller;

import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.services.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private AppUserService appUserService;

    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@Valid @RequestBody AppUser user) {
        System.out.println("SIGN-UP");
        appUserService.registration(user);
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String registration() {
        return "Hello World";
    }
}

