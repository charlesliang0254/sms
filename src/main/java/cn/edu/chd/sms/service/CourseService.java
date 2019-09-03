package cn.edu.chd.sms.service;

import cn.edu.chd.sms.entity.Course;

import java.util.List;

public interface CourseService {
    public Course findCourseByCid(Long uid);
    public List<Course> findCourse(Course course);
}
