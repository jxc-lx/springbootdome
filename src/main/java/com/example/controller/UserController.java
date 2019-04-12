/**
 * 作者：chao
 * 时间：2019/4/10 15:07
 * 描述：
 */
package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

//import org.json.JSONObject;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/gerUser",method = RequestMethod.GET)
    public void getUser(){
        List <User> list = userService.getUser();
        for (User u:list) {
            System.out.println(u.toString());
        }
    }

    @RequestMapping(value = "/insertUser",method =RequestMethod.GET )
    public void insertUser(HttpServletRequest request){
        User user = new User();
        user.setEmail("11111@qq.com");
        user.setId("2");
        user.setName("张三");
        user.setUsername("zhangsan");
        user.setPassword("12121212");
        userService.insert(user);
        System.out.println("--------新增成功-------");
    }
    @RequestMapping(value = "/insertUserTest",method = RequestMethod.POST)
    public void  insertUserTest(@RequestBody String body, HttpServletRequest request) throws IOException {
      // Map map = request.getParameterMap();
       /* BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String jsonStr = null;
        StringBuilder result = new StringBuilder();
        try {
            while ((jsonStr = reader.readLine()) != null) {
                result.append(jsonStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader.close();// 关闭输入流
        JSONObject jsonObject =new JSONObject(result.toString());
        System.out.println(jsonObject.toString());*/
       if (null != body &&!"".equals(body)) {
           JSONObject jsonObject = new JSONObject(body);
           User user =new User();
           user.setName(jsonObject.get("Name").toString());
           user.setEmail(jsonObject.get("Email").toString());
           user.setId(jsonObject.get("Id").toString());
           user.setUsername(jsonObject.get("Username").toString());
           user.setPassword(jsonObject.get("Password").toString());
           userService.insert(user);

       }
    }
}
