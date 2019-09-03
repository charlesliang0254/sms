package cn.edu.chd.sms.service.impl;

import cn.edu.chd.sms.entity.Course;
import cn.edu.chd.sms.entity.Score;
import cn.edu.chd.sms.entity.User;
import cn.edu.chd.sms.mapper.CourseMapper;
import cn.edu.chd.sms.mapper.ScoreMapper;
import cn.edu.chd.sms.mapper.UserMapper;
import cn.edu.chd.sms.service.ScoreService;
import cn.edu.chd.sms.service.ex.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;

    //添加成绩
    @Override
    public Integer addScore(Score score, Long uid) {
        //只能由教师添加课程
        if (uid == null) {
            throw new ServiceException("用户id为空");
        }
        User user = userMapper.getUserById(uid);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        if (user.getIsDelete() == 1) {
            throw new ServiceException("用户被禁用");
        }
        Integer type = user.getType();
        if (type != 1) {
            throw new ServiceException("只有教师才能添加成绩");
        }

        //属性值不能为空
        if (score == null || score.getStudentId() == null || score.getCourseId() == null) {
            throw new ServiceException("成绩信息不完整");
        }
        //查询成绩对应课程
        Course course = courseMapper.findCourseByCid(score.getCourseId());
        if (course == null) {
            throw new ServiceException("考试成绩对应的课程不存在");
        }

        //教师只能添加自己学生的成绩
        if (!course.getTeacherId().equals(uid)) {
            throw new ServiceException("教师只能添加自己学生的成绩");
        }

        //求加权平均后的总评成绩
        Double[] weights = {
                course.getUsualWeight(),
                course.getAssignmentWeight(),
                course.getAttendanceWeight(),
                course.getExperimentWeight(),
                course.getMidtermWeight(),
                course.getFinalexamWeight()
        };
        Double[] scores = {
                score.getUsualScore(),
                score.getAssignmentScore(),
                score.getAttendanceScore(),
                score.getExperimentScore(),
                score.getMidtermScore(),
                score.getFinalexamScore()
        };
        double sumScore = 0;
        double sumWeight = 0;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] != null) {
                sumWeight += weights[i];
                if (scores[i] != null) {
                    sumScore += weights[i] * scores[i];
                }
            }
        }
        try {
            score.setTotalScore(sumScore / sumWeight);
        } catch (ArithmeticException e) {
            score.setTotalScore(null);
        }

        //将成绩对象持久化
        Integer row = scoreMapper.addScore(score);
        if (row != 1) {
            throw new ServiceException("数据库插入操作失败");
        }
        return row;
    }

    //删除成绩
    @Override
    public Integer removeScore(Long sid) {
        if (sid == null) {
            throw new ServiceException("待删除的成绩id为空");
        }
        Integer row = scoreMapper.removeScore(sid);
        if (row != 1) {
            throw new ServiceException("数据库删除操作失败");
        }
        return row;
    }

    @Override
    public Score getOneScore(Long sid) {

        //TODO 判断用户能查看成绩吗
        Score s = scoreMapper.getScoreBySid(sid);
        if (s == null) {
            throw new ServiceException("该成绩不存在！");
        }

        return s;
    }

    @Override
    public List<Score> getAllScore(Score score) {
        if (score == null) {
            throw new ServiceException("查询选项错误！");
        }

        List<Score> s = scoreMapper.getScore(score);
        if (s.isEmpty()) {
            throw new ServiceException("成绩不存在！");
        }

        return s;
    }

    @Override
    public void updateScore(Score score) {

        int num = scoreMapper.updateScore(score);
        if (num != 1) {
            throw new ServiceException("修改错误");
        }
    }
}
