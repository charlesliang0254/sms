package cn.edu.chd.sms.controller;

import cn.edu.chd.sms.entity.User;
import cn.edu.chd.sms.service.UserService;
import cn.edu.chd.sms.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制类
 */
@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;//用户服务层

    //登录
    @PostMapping("/user/login")
    public JsonResult doLogin(User user, HttpSession session){
        LOGGER.debug("登录模块：user = "+user.toString());
        User u=userService.login(user);
        if(u!=null){
            session.setAttribute("uid",u.getUid());
            session.setAttribute("username",u.getUsername());
            session.setAttribute("type",u.getType());
        }
        LOGGER.debug(user.getUsername()+"登录成功");
        Map<String,Object> map=new HashMap<>();
        map.put("uid",u.getUid());
        map.put("username",u.getUsername());
        map.put("type",u.getType());
        return new JsonResult("ok",map);
    }

    //注册
    //参数分别为用户名、密码、重复输入密码
    @PostMapping("/user/reg")
    public JsonResult doReg(String username,String password,String repeatedPassword,Integer type){
        userService.reg(username,password,repeatedPassword,type);
        LOGGER.debug(username+"注册成功");
        return new JsonResult("注册成功");
    }

    //登出
    @PostMapping("/user/logout")
    public JsonResult doLogout(HttpSession session){
        session.removeAttribute("uid");
        session.removeAttribute("username");
        session.removeAttribute("type");
        return new JsonResult("已退出系统");
    }

    //获得当前登录用户的uid
    @GetMapping("/user")
    public JsonResult getCurrentUser(HttpSession session){
        Map<String,Object> map = new HashMap<>();
        map.put("uid", session.getAttribute("uid"));
        map.put("username", session.getAttribute("username"));
        map.put("type",session.getAttribute("type"));
        return new JsonResult(map);
    }
    //根据课程查找学生
    @Deprecated
    @GetMapping("/course/{cid}/student")
    public JsonResult getStudentIdByCid(HttpSession session,@PathVariable("cid")Long cid){
        return new JsonResult();
    }
}
