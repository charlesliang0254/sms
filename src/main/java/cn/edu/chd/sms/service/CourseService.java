package cn.edu.chd.sms.service;

import cn.edu.chd.sms.entity.Course;

import java.util.List;

public interface CourseService {
    Course findCourseByCid(Long cid);
    List<Course> findCourse(Course course);
}
