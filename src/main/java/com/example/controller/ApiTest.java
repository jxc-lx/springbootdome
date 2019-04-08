package com.example.controller;


import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/Api")
public class ApiTest {

    @RequestMapping(value="/GetCookies",method = RequestMethod.GET)
    public String GetCookies(HttpServletResponse response){

        Cookie cookie = new Cookie("login","true");
        //返回cookies信息
        response.addCookie(cookie);

        return "这是返回的cookies信息！！！！";
    }
    @RequestMapping(value = "/GetWithCookies",method = RequestMethod.POST)
    //HttpServletRequest request请求参数,HttpServletResponse response返回参数
    public String GetWithCookies(HttpServletRequest request,HttpServletResponse response){
        System.out.println("---------------"+request.getParameter("name"));
        System.out.println("222222222");
        //可以返回json格式的数据
        JSONObject para = new JSONObject();
        para.put("ID","100001");
        para.put("sex","1");
        //返回header信息
        response.addHeader("xxoo","jiangyi");
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            return para.toString();
        }
        for (Cookie c:cookies) {
            if (c.getName().equals("login")&&c.getValue().equals("true")){
                return para.toString();
            }
        }


        return para.toString();
    }
}
