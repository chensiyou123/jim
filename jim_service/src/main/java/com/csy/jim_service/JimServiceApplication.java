package com.csy.jim_service;

import org.jim.common.ImConfig;
import org.jim.common.config.PropertyImConfigBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JimServiceApplication {

    public static void main(String[] args) {
        ImConfig imConfig = new PropertyImConfigBuilder("jim.properties").build();
        SpringApplication.run(JimServiceApplication.class, args);
    }

}
