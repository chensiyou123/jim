package com.csy.jim_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/")
public class indexController {

    @GetMapping
    public String index(){
        return "/page/index";
    }
}
