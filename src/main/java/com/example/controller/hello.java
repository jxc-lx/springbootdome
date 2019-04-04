package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/hello")
public class hello {

    @RequestMapping(value = "/test")
    public String test(){
        System.out.print("sfdsfdsfds");
        return "test11";
    }

}
