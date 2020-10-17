package com.kislichenko.news.controller;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.dao.RoleRepository;
import com.kislichenko.news.entity.AppUser;
import com.kislichenko.news.entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    private AppUserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(AppUserRepository userRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }


    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@Valid @RequestBody AppUser user) {
        //userValidator.validate(userForm, bindingResult);
        System.out.println("SIGN-UP");

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(1L));
        user.setRoles(roles);

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

