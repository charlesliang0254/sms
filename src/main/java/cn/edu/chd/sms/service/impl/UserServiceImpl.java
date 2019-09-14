package cn.edu.chd.sms.service.impl;

import cn.edu.chd.sms.entity.Course;
import cn.edu.chd.sms.entity.User;
import cn.edu.chd.sms.mapper.CourseMapper;
import cn.edu.chd.sms.mapper.UserMapper;
import cn.edu.chd.sms.service.UserService;
import cn.edu.chd.sms.service.ex.ServiceException;
import cn.edu.chd.sms.service.util.ServiceUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;

    public String getMd5Password(String password,String salt){
        String str = password + salt;
        str = DigestUtils.md2Hex(str).toUpperCase();
        return str;
    }
    @Override
    public User login(User user) {
        if(user==null||StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())){
            throw new ServiceException("登录信息错误或不完整");
        }
        User u = userMapper.getUserByName(user.getUsername());
        if(u==null){
            throw new ServiceException("用户不存在");
        }
        if(u.getIsDelete()==1){
            throw new ServiceException("用户被禁用");
        }
        String password = getMd5Password(user.getPassword(),u.getSalt());
        if(!password.equals(u.getPassword())){
            throw new ServiceException("输入的密码不正确");
        }
        u.setPassword(null);
        u.setSalt(null);
        return u;
    }

    @Override
    public Integer reg(String username, String password, String repeatedPassword, Integer type) {
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)||StringUtils.isEmpty(repeatedPassword)){
            throw new ServiceException("注册信息不完整");
        }

        if(userMapper.getUserByName(username)!=null){
            throw new ServiceException("用户名已被占用");
        }
        if (!password.equals(repeatedPassword)) {
            throw new ServiceException("两次输入的密码不一致");
        }

        User user = new User();
        user.setUsername(username);
        user.setSalt(UUID.randomUUID().toString());
        password=getMd5Password(password,user.getSalt());
        user.setPassword(password);
        user.setType(type);
        user.setIsDelete(0);
        Integer row = userMapper.addUser(user);
        if(row != 1){
            throw new ServiceException("添加用户失败");
        }
        return row;
    }

    @Deprecated
    @Override
    public List<User> getStudentListByCid(Long uid, Long cid) {
        User user = ServiceUtil.verifyUser(userMapper,uid);
        Course course = ServiceUtil.verifyCourse(courseMapper,cid);
        switch(user.getType()){
            case 0:
                break;
            case 1:
                if(!user.getUid().equals(course.getTeacherId())){
                    throw new ServiceException("教师只能查看自己课程的学生名单");
                }
                break;
            case 2:
                throw new ServiceException("没有权限查看学生名单");
        }


        return null;
    }
}
