package cn.edu.chd.sms.controller;


import cn.edu.chd.sms.entity.Course;
import cn.edu.chd.sms.service.CourseService;
import cn.edu.chd.sms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 课程控制类
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;//课程服务层

    //主键查询
    @GetMapping("/{cid}")
    public JsonResult doFindCourseById(@PathVariable("cid") Long cid) {
        Course course = courseService.findCourseByCid(cid);
        return new JsonResult(course);
    }

    //非主键查询
    @GetMapping("/")
    public JsonResult doFindCourse(Course course) {
        List<Course> courses = courseService.findCourse(course);
        return new JsonResult(courses);
    }
}
