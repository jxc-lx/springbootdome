package com.example.controller;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/hello")
public class hello {

    @Autowired
    private SqlSessionTemplate template;
    @RequestMapping(value = "/test")
    public String test(){
        System.out.print("sfdsfdsfds");

        int i=template.selectOne("getUserCount");

        System.out.print("------------------"+i);
        System.out.print("------------------"+i);
        return "test11";

    }

}
