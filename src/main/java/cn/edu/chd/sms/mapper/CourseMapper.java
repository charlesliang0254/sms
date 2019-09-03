package cn.edu.chd.sms.mapper;

import cn.edu.chd.sms.entity.Course;
import cn.edu.chd.sms.vo.TeacherCourseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 课程mapper
 */
@Mapper
public interface CourseMapper {
    Course findCourseByCid(Long cid);//根据主键查询课程
    List<Course> findCourse(Course course);//根据非主键属性查询课程，非查询条件的属性设为null
//    List<TeacherCourseVO> findTeacherCourseVO(TeacherCourseVO teacherCourseVO);//多表查询
}
