package cn.edu.chd.sms.vo;

import cn.edu.chd.sms.entity.Score;

public class StudentScore extends Score {
    private String studentName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "StudentScore{" +
                "studentName='" + studentName + '\'' +
                "} " + super.toString();
    }
}
