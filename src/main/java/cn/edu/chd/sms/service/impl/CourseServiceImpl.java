package cn.edu.chd.sms.service.impl;

import cn.edu.chd.sms.entity.Course;
import cn.edu.chd.sms.entity.Score;
import cn.edu.chd.sms.entity.User;
import cn.edu.chd.sms.mapper.CourseMapper;
import cn.edu.chd.sms.mapper.ScoreMapper;
import cn.edu.chd.sms.mapper.UserMapper;
import cn.edu.chd.sms.service.CourseService;
import cn.edu.chd.sms.service.ex.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static cn.edu.chd.sms.service.util.ServiceUtil.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Course findCourseByCid(Long uid, Long cid) {
        verifyUser(userMapper, uid);
        return verifyCourse(courseMapper,cid);
    }

    @Override
    public List<Course> findCourseByTeacherId(Long uid, Long teacherId) {
        User user = verifyUser(userMapper, uid);
        return courseMapper.findCourseByTeacherId(teacherId);
    }

    @Override
    public List<Course> findCourse(Long uid,Course course) {
        User user = verifyUser(userMapper,uid);
        switch(user.getType()){
            case 0:
                break;
            case 1:
                course.setTeacherId(uid);
                break;
            case 2:
            default:
                throw new ServiceException("没有访问权限");
        }
        if (course == null) {
            throw new ServiceException("查询错误！");
        }
        LOGGER.debug("name = "+ Arrays.toString((course.getName()==null?"":course.getName()).getBytes()));
        List<Course> courseList = courseMapper.findCourse(course);
        if(courseList==null||courseList.isEmpty()){
            throw new ServiceException("查询不到课程信息");
        }
        return courseList;
    }

    @Override
    public void updateCourse(Long uid, Course course) {
        //用户的校验
        User user = verifyUser(userMapper, uid);
        //课程校验
        Course oldCourse = verifyCourse(courseMapper,course.getCid());

        //权限判断
        switch(user.getType()){
            case 0://管理员
                break;
            case 1://教师
                if(!oldCourse.getTeacherId().equals(uid)){
                    throw new ServiceException("不能修改其他教师负责的课程");
                }
                break;
            default://学生、其他
                throw new ServiceException("没有修改权限");
        }

        //如果已经有提交的成绩则不能修改
        Score score = new Score();
        score.setCourseId(course.getCid());
        score.setIsSubmitted(1);
        List<Score> result = scoreMapper.getScore(score);
        if (result != null && !result.isEmpty()) {
            throw new ServiceException("已经有提交的成绩，不允许修改课程");
        }

        Integer row = courseMapper.updateCourse(course);
        if (row != 1) {
            throw new ServiceException("课程修改失败");
        }

        if (isWeightUpdate(oldCourse, course)) {
            Score tempScore = new Score();
            tempScore.setCourseId(course.getCid());
            List<Score> scores = scoreMapper.getScore(tempScore);
            for (Score s : scores) {
                Double totalScore = recalculateTotalScore(course, s, tempScore);
                LOGGER.debug("totalScore = "+totalScore);
                s.setTotalScore(totalScore);
                scoreMapper.updateScore(s);
            }
        }
    }

    private boolean isWeightUpdate(Course oldCourse, Course newCourse) {
        return !oldCourse.getUsualWeight().equals(newCourse.getUsualWeight())
                || !oldCourse.getAssignmentWeight().equals(newCourse.getAssignmentWeight())
                || !oldCourse.getAttendanceWeight().equals(newCourse.getAttendanceWeight())
                || !oldCourse.getExperimentWeight().equals(newCourse.getExperimentWeight())
                || !oldCourse.getFinalexamWeight().equals(newCourse.getFinalexamWeight())
                || !oldCourse.getMidtermWeight().equals(newCourse.getMidtermWeight());
    }
}
