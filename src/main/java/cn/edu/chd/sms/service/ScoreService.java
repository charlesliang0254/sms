package cn.edu.chd.sms.service;

import cn.edu.chd.sms.entity.Score;

import java.util.List;
import java.util.Map;

public interface ScoreService {
    Integer addScore(Score scores, Long uid);
    Integer removeScore(Long uid, Long sid);
    Score getOneScore(Long uid, Long sid);
    @Deprecated
    List<Score> getAllScore(Score score);
    Integer updateScore(Long uid, Score score);
    Integer getTotalScorePosition(Long uid, Long sid, Long cid);
    //根据学生id查找所有课程成绩 (学生)
    List<Score> getAllScoreByStudentId(Long uid, Score score);
    //查找选修某门课程的所有学生成绩（教师）
    List<Score> getAllScoreByCid(Score score, Long uid);
    Map<String,Object> getScoreAnalysis(Long uid,Long cid);
    Integer saveScoreAnalysisFile(Long uid, Long cid, String parentPath, Map<String, Object> map);
    Map<String,Object> getScoreAnalysisFile(Long uid,Long cid,String parentPath);
}
