package cn.edu.chd.sms.service.impl;

import cn.edu.chd.sms.entity.Course;
import cn.edu.chd.sms.entity.Score;
import cn.edu.chd.sms.entity.User;
import cn.edu.chd.sms.mapper.CourseMapper;
import cn.edu.chd.sms.mapper.ScoreMapper;
import cn.edu.chd.sms.mapper.UserMapper;
import cn.edu.chd.sms.service.CourseService;
import cn.edu.chd.sms.service.ex.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.edu.chd.sms.service.util.ServiceUtil.recalculateTotalScore;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Course findCourseByCid(Long cid) {
        if (cid == null) {
            throw new ServiceException("课程id不能为空");
        }
        return courseMapper.findCourseByCid(cid);
    }

    @Override
    public List<Course> findCourse(Course course) {
        if (course == null) {
            throw new ServiceException("查询错误！");
        }
        return courseMapper.findCourse(course);
    }

    @Override
    public void updateCourse(Long uid, Course course) {
        //用户的校验
        if (course == null) {
            throw new ServiceException("无课程信息");
        }
        if (course.getCid() == null) {
            throw new ServiceException("课程号为空");
        }
        if (uid == null) {
            throw new ServiceException("用户登录错误");
        }
        User user = userMapper.getUserById(uid);
        if (user.getIsDelete() != 0) {
            throw new ServiceException("该用户已经被删除");
        }

        Course oldCourse = courseMapper.findCourseByCid(course.getCid());
        if (oldCourse == null) {
            throw new ServiceException("无此课程");
        }

        //权限判断
        int type = user.getType();
        if (type == 0) {
            ;//管理员
        } else if (type == 1) {
            if (oldCourse.getTeacherId() != uid) {
                throw new ServiceException("不能修改其他教师负责的课程");
            }
        } else {
            throw new ServiceException("没有修改权限");
        }

        //如果已经有提交的成绩则不能修改
        Score cond = new Score();
        cond.setCourseId(course.getCid());
        cond.setIsSubmitted(1);
        List<Score> result = scoreMapper.getScore(cond);
        if (result != null && !result.isEmpty()) {
            throw new ServiceException("已经有提交的成绩，不允许修改课程");
        }

        Integer row = courseMapper.updateCourse(course);
        if (row != 1) {
            throw new ServiceException("课程修改失败");
        }

        course = findCourseByCid(course.getCid());
        if (isWeightUpdate(oldCourse, course)) {
            Score forFind = new Score();
            forFind.setCourseId(course.getCid());
            List<Score> scores = scoreMapper.getScore(forFind);
            for (Score s : scores) {
                Double totalScore = recalculateTotalScore(course, s, forFind);
                System.out.println(totalScore);
                s.setTotalScore(totalScore);
                scoreMapper.updateScore(s);
            }
        }
    }

    boolean isWeightUpdate(Course oldcourse, Course newcourse) {
        return oldcourse.getUsualWeight() != newcourse.getUsualWeight()
                || oldcourse.getAssignmentWeight() != newcourse.getAssignmentWeight()
                || oldcourse.getAttendanceWeight() != newcourse.getAttendanceWeight()
                || oldcourse.getExperimentWeight() != newcourse.getExperimentWeight()
                || oldcourse.getFinalexamWeight() != newcourse.getFinalexamWeight()
                || oldcourse.getMidtermWeight() != newcourse.getMidtermWeight();
    }
}
