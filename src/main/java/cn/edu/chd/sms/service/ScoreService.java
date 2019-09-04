package cn.edu.chd.sms.service;

import cn.edu.chd.sms.entity.Score;

import java.util.List;

public interface ScoreService {
    Integer addScore(Score scores, Long uid);
    Integer removeScore(Long uid, Long sid);
    Score getOneScore(Long uid, Long sid);
    List<Score> getAllScore(Score score);
    Integer updateScore(Long uid, Score score);
    Integer getTotalScorePosition(Long uid, Long sid, Long cid);
    //根据学生id查找所有课程成绩 (学生)
    List<Score> getAllScoreByStudentId(Long uid, Score score);
    //查找选修某门课程的所有学生成绩（教师）
    List<Score> getAllScoreByCid(Score score, Long uid);
}
