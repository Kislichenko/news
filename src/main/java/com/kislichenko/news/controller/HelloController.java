package com.kislichenko.news.controller;

import com.kislichenko.news.dao.ClientRepository;
import com.kislichenko.news.entity.Client;
import com.kislichenko.news.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";

    }

    @GetMapping("clients/{id}")
    public Client get(@PathVariable(name = "id") int id) throws ClientNotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

}
