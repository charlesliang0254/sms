package cn.edu.chd.sms.controller;

import cn.edu.chd.sms.entity.User;
import cn.edu.chd.sms.service.UserService;
import cn.edu.chd.sms.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 用户控制类
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;//用户服务层

    //登录
    @PostMapping("/login")
    public JsonResult doLogin(User user, HttpSession session){
        LOGGER.debug("登录模块：user = "+user.toString());
        User u=userService.login(user);
        if(u!=null){
            session.setAttribute("uid",u.getUid());
            session.setAttribute("username",u.getUsername());
            session.setAttribute("type",u.getType());
        }
        LOGGER.debug(user.getUsername()+"登录成功");
        return new JsonResult("ok");
    }

    //注册
    //参数分别为用户名、密码、重复输入密码
    @PostMapping("/reg")
    public JsonResult doReg(String username,String password,String repeatedPassword,Integer type){
        userService.reg(username,password,repeatedPassword,type);
        LOGGER.debug(username+"注册成功");
        return new JsonResult("注册成功");
    }


}
