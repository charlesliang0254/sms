package cn.edu.chd.sms.mapper;

import cn.edu.chd.sms.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户mapper
 */
@Mapper
public interface UserMapper {
    User getUserById(Long uid);//根据主键查询用户
    User getUserByName(String username);//根据用户名查询
    Integer addUser(User user);//添加用户
}
