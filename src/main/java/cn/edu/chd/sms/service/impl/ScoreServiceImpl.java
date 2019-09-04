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
        User user = verifyUser(uid);
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

        //计算总评成绩
        score.setTotalScore(calculateTotalScore(course,score));

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
    public void updateScore(Long uid, Score score) {
        //身份的验证
        User user = verifyUser(uid);

        if (user.getType() != 0 && user.getType() != 1) {
            throw new ServiceException("用户没有修改的权限");
        }

        if (score.getSid() == null) {
            throw new ServiceException("要修改成绩记录的id未知");
        }

        Score oldScore = scoreMapper.getScoreBySid(score.getSid());
        if (oldScore == null) {
            throw new ServiceException("要修改的成绩记录不存在");
        }

        if (user.getType() != 0 && oldScore.getIsSubmitted() == 1) {
            throw new ServiceException("成绩记录已经被提交，无法修改");
        }

        Course course = courseMapper.findCourseByCid(oldScore.getCourseId());
        if(course==null){
            throw new ServiceException("考试对应的课程信息不存在");
        }

        Double totalScore = recalculateTotalScore(course, oldScore, score);
        score.setTotalScore(totalScore);

        System.out.println("score = " + score);
        Integer row = scoreMapper.updateScore(score);
        if (row != 1) {
            throw new ServiceException("执行数据库更新操作失败");
        }
    }

    @Override
    public Integer getTotalScorePostion(Long uid, Long sid, Long cid) {
        User user = verifyUser(uid);
        if(sid==null||cid==null){
            throw new ServiceException("成绩ID或课程ID为空");
        }
        Score score = scoreMapper.getScoreBySid(sid);
        if(score==null){
            throw new ServiceException("成绩记录不存在");
        }

        Course course = courseMapper.findCourseByCid(cid);
        if(course==null){
            throw new ServiceException("课程记录不存在");
        }

        if(user.getType()==0);//什么也不做
        else if(user.getType()==1){
            if(course.getTeacherId()!=user.getUid()){
                throw new ServiceException("教师只能查询自己课程的成绩排名");
            }
        }
        else if(user.getType()==2){
            if(user.getUid()!=score.getStudentId()){
                throw new ServiceException("学生只能查询自己的成绩排名");
            }
        }
        else{
            throw new ServiceException("不存在这样的用户");
        }

        Integer row = scoreMapper.getTotalScorePosition(sid, cid);

        if(row==null){
            throw new ServiceException("成绩排名查询失败");
        }
        return row;
    }

    private User verifyUser(Long uid) {
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
        return user;
    }

    private Double recalculateTotalScore(Course course, Score oldScore, Score newScore) {
        Score score = new Score();
        score.setUsualScore(oldScore.getUsualScore());
        score.setAttendanceScore(oldScore.getAttendanceScore());
        score.setExperimentScore(oldScore.getExperimentScore());
        score.setAssignmentScore(oldScore.getAssignmentScore());
        score.setMidtermScore(oldScore.getMidtermScore());
        score.setFinalexamScore(oldScore.getFinalexamScore());

        if (newScore.getUsualScore() != null) {
            score.setUsualScore(newScore.getUsualScore());
        }
        if (newScore.getAttendanceScore() != null) {
            score.setAttendanceScore(newScore.getAttendanceScore());
        }
        if (newScore.getExperimentScore() != null) {
            score.setExperimentScore(newScore.getExperimentScore());
        }
        if (newScore.getAssignmentScore() != null) {
            score.setAssignmentScore(newScore.getAssignmentScore());
        }
        if (newScore.getMidtermScore() != null) {
            score.setMidtermScore(newScore.getMidtermScore());
        }
        if (newScore.getFinalexamScore() != null) {
            score.setFinalexamScore(newScore.getFinalexamScore());
        }
        return calculateTotalScore(course, score);
    }

    private Double calculateTotalScore(Course course, Score score) {
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
            return sumScore / sumWeight;
        } catch (ArithmeticException e) {
            return null;
        }
    }
}
