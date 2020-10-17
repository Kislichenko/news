package com.kislichenko.news.controller;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.model.AppUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    private AppUserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(AppUserRepository userRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public void registration(@RequestBody AppUser user) {
        //userValidator.validate(userForm, bindingResult);
        System.out.println("SIGN-UP");

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String registration() {
       return "Hello World";


    }


//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login(Model model, String error, String logout) {
//        if (error != null) {
//            model.addAttribute("error", "Username or password is incorrect.");
//        }
//
//        if (logout != null) {
//            model.addAttribute("message", "Logged out successfully.");
//        }
//
//        return "login";
//    }
//
//    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
//    public String welcome(Model model) {
//        return "welcome";
//    }
//
//    @RequestMapping(value = "/admin", method = RequestMethod.GET)
//    public String admin(Model model) {
//        return "admin";
//    }
}

