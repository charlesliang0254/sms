function addScore(){
    // private Long sid;//成绩id
    // private Long studentId;//学生id
    // private Long courseId;//课程id
    // private Double usualScore; //平时成绩
    // private Double attendanceScore; //考勤成绩
    // private Double assignmentScore; //作业成绩
    // private Double experimentScore; //实验成绩
    // private Double midtermScore; //期中考试成绩
    // private Double finalexamScore; //期末考试成绩
    // private Double totalScore; //总评成绩
    // private Double reexamScore; //补考成绩
    // private Integer isSubmitted;//是否提交
    var data = {
        "studentId":$("#studentId").val(),
        "courseId":$("#courseId").val(),
        "usualScore":$("#usualScore").val(),
        "attendanceScore":$("#attendanceScore").val(),
        "assignmentScore":$("#assignmentScore").val(),
        "experimentScore":$("#experimentScore").val(),
        "midtermScore":$("#midtermScore").val(),
        "finalexamScore":$("#finalexamScore").val(),
        "totalScore":$("#totalScore").val(),
        "reexamScore":$("#reexamScore").val()
    };
    $.ajax({
        "url":"/score",
        "type":"post",
        "dataType":"json",
        "data":data,
        "success":function(obj){
            if(obj.state===1){
                alert("添加成绩成功");
            }
            else{
                alert(obj.message);
            }
        }
    })
}