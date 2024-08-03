package com.deepak.zipextracter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/open")
public class Test {

    @GetMapping
    public String give(){
        System.out.println("hrtr ioidsh");
        return "open";
    }
}
