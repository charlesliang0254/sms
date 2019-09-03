package cn.edu.chd.sms.vo;

import cn.edu.chd.sms.entity.Course;

public class TeacherCourseVO extends Course {
    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "TeacherCourseVO{" +
                "uid=" + uid +
                "} " + super.toString();
    }
}
