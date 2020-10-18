package com.kislichenko.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MainApplication {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        //использует функцию сильного хеширования BCrypt
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }


}
