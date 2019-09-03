package cn.edu.chd.sms.service;

import cn.edu.chd.sms.entity.Score;

import java.util.List;

public interface ScoreService {
    Integer addScore(List<Score> scores, Long uid);
    Integer removeScore(Long sid);
    Score getOneScore(Long sid);
    List<Score> getAllScore(Score score);
    void updateScore(Score score);
}
