package cn.edu.chd.sms.service.util;

import cn.edu.chd.sms.entity.Course;
import cn.edu.chd.sms.entity.Score;
import cn.edu.chd.sms.entity.User;
import cn.edu.chd.sms.mapper.CourseMapper;
import cn.edu.chd.sms.mapper.ScoreMapper;
import cn.edu.chd.sms.mapper.UserMapper;
import cn.edu.chd.sms.service.ex.ServiceException;

public class ServiceUtil {
    public static Double recalculateTotalScore(Course course, Score oldScore, Score newScore) {
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

    public static Double calculateTotalScore(Course course, Score score) {
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

    public static User verifyUser(UserMapper userMapper, Long uid) {
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

    public static Course verifyCourse(CourseMapper courseMapper, Long cid) {
        if (cid == null) {
            throw new ServiceException("课程id为空");
        }
        Course course = courseMapper.findCourseByCid(cid);
        if (course == null) {
            throw new ServiceException("课程不存在");
        }
        return course;
    }

    public static Score verifyScore(ScoreMapper scoreMapper,Long sid){
        if(sid==null){
            throw new ServiceException("成绩id为空");
        }
        Score score = scoreMapper.getScoreBySid(sid);
        if(score==null){
            throw new ServiceException("成绩记录不存在");
        }
        return score;
    }
}
