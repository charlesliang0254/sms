package cn.edu.chd.sms.controller;


import cn.edu.chd.sms.entity.Course;
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
    public JsonResult doFindCourseById(HttpSession session,@PathVariable("cid") Long cid) {
        Long uid = (Long) session.getAttribute("uid");
        Course course = courseService.findCourseByCid(uid, cid);
        return new JsonResult(course);
    }

    //根据教师ID查找课程
    @GetMapping("/teacher/{uid}/course")
    public JsonResult doFindCourseByUid(HttpSession session,@PathVariable("uid") Long teacherId) {
        Long uid = (Long) session.getAttribute("uid");
        List<Course> courses=courseService.findCourseByTeacherId(uid,teacherId);
        return new JsonResult();
    }

    //非主键查询
    @GetMapping("/course")
    public JsonResult doFindCourse(HttpSession session,Course course) {
        Long uid = (Long) session.getAttribute("uid");
        List<Course> courses = courseService.findCourse(uid,course);
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
