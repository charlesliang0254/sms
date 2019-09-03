package cn.edu.chd.sms.service.impl;

import cn.edu.chd.sms.entity.Course;
import cn.edu.chd.sms.mapper.CourseMapper;
import cn.edu.chd.sms.service.CourseService;
import cn.edu.chd.sms.service.ex.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Course findCourseByCid(Long cid) {
        if(cid == null) {
            throw new ServiceException("课程id不能为空");
        }
        return courseMapper.findCourseByCid(cid);
    }

    @Override
    public List<Course> findCourse(Course course) {
        if(course == null) {
            throw new ServiceException("查询错误！");
        }
        return courseMapper.findCourse(course);
    }
}
