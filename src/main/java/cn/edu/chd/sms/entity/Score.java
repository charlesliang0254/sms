package cn.edu.chd.sms.entity;

import java.io.Serializable;

/**
 * 成绩实体类
 */
public class Score implements Serializable {
    private Long sid;//成绩id
    private Long studentId;//学生id
    private Long courseId;//课程id
    private Double usualScore; //平时成绩
    private Double attendanceScore; //考勤成绩
    private Double assignmentScore; //作业成绩
    private Double experimentScore; //实验成绩
    private Double midtermScore; //期中考试成绩
    private Double finalexamScore; //期末考试成绩
    private Double totalScore; //总评成绩
    private Double reexamScore; //补考成绩
    private Integer isSubmitted;//是否提交

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Double getUsualScore() {
        return usualScore;
    }

    public void setUsualScore(Double usualScore) {
        this.usualScore = usualScore;
    }

    public Double getAttendanceScore() {
        return attendanceScore;
    }

    public void setAttendanceScore(Double attendanceScore) {
        this.attendanceScore = attendanceScore;
    }

    public Double getAssignmentScore() {
        return assignmentScore;
    }

    public void setAssignmentScore(Double assignmentScore) {
        this.assignmentScore = assignmentScore;
    }

    public Double getExperimentScore() {
        return experimentScore;
    }

    public void setExperimentScore(Double experimentScore) {
        this.experimentScore = experimentScore;
    }

    public Double getMidtermScore() {
        return midtermScore;
    }

    public void setMidtermScore(Double midtermScore) {
        this.midtermScore = midtermScore;
    }

    public Double getFinalexamScore() {
        return finalexamScore;
    }

    public void setFinalexamScore(Double finalexamScore) {
        this.finalexamScore = finalexamScore;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Double getReexamScore() {
        return reexamScore;
    }

    public void setReexamScore(Double reexamScore) {
        this.reexamScore = reexamScore;
    }

    public Integer getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Integer isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    @Override
    public String toString() {
        return "Score{" +
                "sid=" + sid +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", usualScore=" + usualScore +
                ", attendanceScore=" + attendanceScore +
                ", assignmentScore=" + assignmentScore +
                ", experimentScore=" + experimentScore +
                ", midtermScore=" + midtermScore +
                ", finalexamScore=" + finalexamScore +
                ", totalScore=" + totalScore +
                ", reexamScore=" + reexamScore +
                ", isSubmitted=" + isSubmitted +
                '}';
    }
}