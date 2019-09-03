package cn.edu.chd.sms.entity;

/**
 * 用户实体类，包括管理员、教师、学生
 */
public class User {
    private Long uid;//用户id
    private String username;//账户
    private String password;//密码
    private String salt;//盐值
    private Integer type;//用户类型，0=管理员，1=教师，2=学生
    private Integer isDelete;//禁用

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", type=" + type +
                ", isDelete=" + isDelete +
                '}';
    }
}
