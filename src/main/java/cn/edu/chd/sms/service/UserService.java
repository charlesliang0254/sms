package cn.edu.chd.sms.service;

import cn.edu.chd.sms.entity.User;

public interface UserService {
    public User login(User user);
    public Integer reg(String username, String password, String repeatedPassword, Integer type);
}
