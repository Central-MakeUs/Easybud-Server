package com.friends.easybud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EasybudApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasybudApplication.class, args);
    }

}