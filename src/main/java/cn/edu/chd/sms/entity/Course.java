package cn.edu.chd.sms.entity;

/**
 * 课程实体类
 */
public class Course {
    private Long cid;//课程id
    private Long teacherId;//教师id
    private Integer term;//学期
    private String sequenceNo; //课程序号
    private String courseNo; //课程代码
    private String name; //课程名称
    private Integer type; //课程类型，0=未分类，1=通识教育课程，2=学科基础课程，3=专业发展课程，4=实践环节
    private Double credit; //学分
    private Integer scoreType;
    private Double usualWeight; //平时成绩权重
    private Double attendanceWeight; //考勤成绩权重
    private Double assignmentWeight; //作业成绩权重
    private Double experimentWeight; //实验成绩权重
    private Double midtermWeight; //期中考试成绩权重
    private Double finalexamWeight; //期末考试成绩权重
    private String analysisTable;//成绩分析表的路径
    private Integer isSubmitted;//成绩分析表是否提交

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

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

    public Double getUsualWeight() {
        return usualWeight;
    }

    public void setUsualWeight(Double usualWeight) {
        this.usualWeight = usualWeight;
    }

    public Double getAttendanceWeight() {
        return attendanceWeight;
    }

    public void setAttendanceWeight(Double attendanceWeight) {
        this.attendanceWeight = attendanceWeight;
    }

    public Double getAssignmentWeight() {
        return assignmentWeight;
    }

    public void setAssignmentWeight(Double assignmentWeight) {
        this.assignmentWeight = assignmentWeight;
    }

    public Double getExperimentWeight() {
        return experimentWeight;
    }

    public void setExperimentWeight(Double experimentWeight) {
        this.experimentWeight = experimentWeight;
    }

    public Double getMidtermWeight() {
        return midtermWeight;
    }

    public void setMidtermWeight(Double midtermWeight) {
        this.midtermWeight = midtermWeight;
    }

    public Double getFinalexamWeight() {
        return finalexamWeight;
    }

    public void setFinalexamWeight(Double finalexamWeight) {
        this.finalexamWeight = finalexamWeight;
    }

    public String getAnalysisTable() {
        return analysisTable;
    }

    public void setAnalysisTable(String analysisTable) {
        this.analysisTable = analysisTable;
    }

    public Integer getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Integer isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cid=" + cid +
                ", teacherId=" + teacherId +
                ", term=" + term +
                ", sequenceNo='" + sequenceNo + '\'' +
                ", courseNo='" + courseNo + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", credit=" + credit +
                ", scoreType=" + scoreType +
                ", usualWeight=" + usualWeight +
                ", attendanceWeight=" + attendanceWeight +
                ", assignmentWeight=" + assignmentWeight +
                ", experimentWeight=" + experimentWeight +
                ", midtermWeight=" + midtermWeight +
                ", finalexamWeight=" + finalexamWeight +
                ", analysisTable='" + analysisTable + '\'' +
                ", isSubmitted=" + isSubmitted +
                '}';
    }
}
