package cn.edu.chd.sms.mapper;

import cn.edu.chd.sms.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 课程mapper
 */
@Mapper
public interface CourseMapper {
    Course findCourseByCid(Long cid);//根据主键查询课程
    List<Course> findCourse(Course course);//根据非主键属性查询课程，非查询条件的属性设为null
    Integer updateCourse(Course course);
    List<Course> findCourseByTeacherId(Long teacherId);//根据教师id查询课程
}
