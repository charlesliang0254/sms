package cn.edu.chd.sms.controller;


import cn.edu.chd.sms.entity.Course;
import cn.edu.chd.sms.entity.Score;
import cn.edu.chd.sms.service.CourseService;
import cn.edu.chd.sms.service.ScoreService;
import cn.edu.chd.sms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 课程控制类
 */
@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;//课程服务层
    @Autowired
    private ScoreService scoreService;//成绩服务层

    //主键查询
    @GetMapping("/course/{cid}")
    public JsonResult doFindCourseById(@PathVariable("cid") Long cid) {
        Course course = courseService.findCourseByCid(cid);
        return new JsonResult(course);
    }

    //根据教师ID查找课程
    @GetMapping("/teacher/{uid}/course")
    public JsonResult doFindCourseByUid(@PathVariable("uid") Long uid) {
        //TODO 根据教师ID查找课程
        return new JsonResult();
    }

    //非主键查询
    @GetMapping("/course")
    public JsonResult doFindCourse(Course course) {
        List<Course> courses = courseService.findCourse(course);
        return new JsonResult(courses);
    }

    //修改课程
    @PutMapping("/course/{cid}")
    public JsonResult doUpdateCourse(Course course, @PathVariable("cid") Long cid, HttpSession session) {
        Long uid = (Long) session.getAttribute("uid");
        course.setCid(cid);
        courseService.updateCourse(uid, course);
        return new JsonResult("修改成功");
    }
}
