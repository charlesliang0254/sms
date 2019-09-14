package cn.edu.chd.sms.service;

import cn.edu.chd.sms.entity.User;

import java.util.List;

public interface UserService {
    User login(User user);
    Integer reg(String username, String password, String repeatedPassword, Integer type);
    List<User> getStudentListByCid(Long uid,Long cid);
}
