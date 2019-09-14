package cn.edu.chd.sms.mapper;

import cn.edu.chd.sms.entity.Score;
import cn.edu.chd.sms.vo.CourseScore;
import cn.edu.chd.sms.vo.StudentScore;
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
    List<Score> getScore(Score score);//根据非主键属性查询成绩
    List<CourseScore> getCourseScore(CourseScore score);//根据非主键属性查询成绩
    List<StudentScore> getStudentScoreList(StudentScore studentScore);//根据非主属性查询成绩和对应的学生用户名
    Integer updateScore(Score score);//更新成绩
    Integer getTotalScorePosition(Long sid,Long cid);//获取成绩排名
}
