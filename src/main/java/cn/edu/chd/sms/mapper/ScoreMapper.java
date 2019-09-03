package cn.edu.chd.sms.mapper;

import cn.edu.chd.sms.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 成绩mapper
 */
@Mapper
public interface ScoreMapper {
    Integer addScore(Score score);//添加成绩
    Integer removeScore(Long sid);//删除成绩
    Score getScoreBySid(Long sid);//根据主键查询成绩
    List<Score> getScore(Score score);//根据非主键属性查询课程
    Integer updateScore(Score score);//更新成绩
}
