package cn.edu.chd.sms.vo;


import cn.edu.chd.sms.entity.Score;

public class CourseScore extends Score {
    private Integer term;//学期
    private String sequenceNo; //课程序号
    private String courseNo; //课程代码
    private String name; //课程名称
    private Integer type; //课程类型，0=未分类，1=通识教育课程，2=学科基础课程，3=专业发展课程，4=实践环节
    private Double credit; //学分
    private Integer ranking;

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return "CourseScore{" +
                "term=" + term +
                ", sequenceNo='" + sequenceNo + '\'' +
                ", courseNo='" + courseNo + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", credit=" + credit +
                ", ranking=" + ranking +
                "} " + super.toString();
    }
}
