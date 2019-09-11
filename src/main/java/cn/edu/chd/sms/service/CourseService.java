package cn.edu.chd.sms.service;

import cn.edu.chd.sms.entity.Course;

import java.util.List;

public interface CourseService {
    Course findCourseByCid(Long uid, Long cid);
    List<Course> findCourse(Long uid,Course course);
    void updateCourse(Long uid, Course course);
    List<Course> findCourseByTeacherId(Long uid, Long teacherId);
}
