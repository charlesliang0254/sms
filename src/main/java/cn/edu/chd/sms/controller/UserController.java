package cn.edu.chd.sms.controller;

import cn.edu.chd.sms.entity.User;
import cn.edu.chd.sms.service.UserService;
import cn.edu.chd.sms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 用户控制类
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;//用户服务层

    //登录
    @PostMapping("/login")
    public JsonResult doLogin(User user, HttpSession session){
        User u=userService.login(user);
        if(u!=null){
            session.setAttribute("uid",u.getUid());
            session.setAttribute("username",u.getUsername());
            session.setAttribute("type",u.getType());
        }
        return new JsonResult("ok");
    }

    //注册
    //参数分别为用户名、密码、重复输入密码
    @PostMapping("/reg")
    public JsonResult doReg(String username,String password,String repeatedPassword,Integer type){
        userService.reg(username,password,repeatedPassword,type);
        return new JsonResult("注册成功");
    }


}
