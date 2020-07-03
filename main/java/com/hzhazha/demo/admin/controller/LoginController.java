package com.hzhazha.demo.admin.controller;

import com.hzhazha.demo.admin.bean.User;
import com.hzhazha.demo.admin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/sys")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @ResponseBody
    @RequestMapping(value = "/login")
    public Map<String, Object> login(@RequestBody Map<String,String> map, BindingResult bindingResult) {
        Map<String, Object> result = new HashMap<>();
        if (bindingResult.hasErrors()) {
            result.put("status", 400);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return result;
        }

        String username = map.get("username");
        String password = map.get("password");
        //用户信息
        User user = loginService.findByUsername(username);
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(password)) {
            result.put("status", 400);
            result.put("msg", "账号或密码有误");
        } else {
            //生成token，并保存到数据库
            result = loginService.createToken(user.getUserId());
            result.put("status", 200);
            result.put("msg", "登陆成功");
            result.put("user",user);
        }
        return result;
    }

    /**
     * 退出
     */
    @ResponseBody
    @RequestMapping(value = "/logout")
    public Map<String, Object> logout(@RequestHeader("token")String token) {
        Map<String, Object> result = new HashMap<>();
        loginService.logout(token);
        result.put("status", 200);
        result.put("msg", "您已安全退出系统");
        return result;
    }
}
