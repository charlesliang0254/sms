package cn.edu.chd.sms.service.impl;

import cn.edu.chd.sms.entity.Course;
import cn.edu.chd.sms.entity.Score;
import cn.edu.chd.sms.entity.User;
import cn.edu.chd.sms.mapper.CourseMapper;
import cn.edu.chd.sms.mapper.ScoreMapper;
import cn.edu.chd.sms.mapper.UserMapper;
import cn.edu.chd.sms.service.ScoreService;
import cn.edu.chd.sms.service.ex.ServiceException;
import cn.edu.chd.sms.util.XMLParser;
import cn.edu.chd.sms.vo.CourseScore;
import cn.edu.chd.sms.vo.StudentScore;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static cn.edu.chd.sms.service.util.ServiceUtil.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class ScoreServiceImpl implements ScoreService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreServiceImpl.class);
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
        User user = verifyUser(userMapper,uid);
        Integer type = user.getType();
        if (!type.equals(1)&&!type.equals(0)) {
            throw new ServiceException("没有添加成绩的权限");
        }

        //查询成绩对应课程
        Course course = verifyCourse(courseMapper,score.getCourseId());

        //验证学生身份
        verifyUser(userMapper,score.getStudentId());

        //教师只能添加自己学生的成绩
        if (!course.getTeacherId().equals(uid)) {
            throw new ServiceException("教师只能添加自己学生的成绩");
        }

        //计算总评成绩
        score.setTotalScore(calculateTotalScore(course, score));

        //将成绩对象持久化
        Integer row = scoreMapper.addScore(score);
        if (row != 1) {
            throw new ServiceException("数据库插入操作失败");
        }
        return row;
    }

    //删除成绩
    @Override
    public Integer removeScore(Long uid, Long sid) {
        User user = verifyUser(userMapper,uid);
        if (sid == null) {
            throw new ServiceException("待删除的成绩id为空");
        }
        Score score = scoreMapper.getScoreBySid(sid);
        if (score == null) {
            throw new ServiceException("成绩记录不存在");
        }

        if (user.getType() == 0) {
            //管理员具有最高权限
        } else if (user.getType() == 1) {
            if (score.getIsSubmitted() == 1) {
                throw new ServiceException("成绩已经被提交，无法删除，请联系管理员");
            }
        } else {
            throw new ServiceException("没有删除权限");
        }

        Integer row = scoreMapper.removeScore(sid);
        if (row != 1) {
            throw new ServiceException("数据库删除操作失败");
        }
        return row;
    }

    //根据成绩ID查询成绩
    @Override
    public Score getOneScore(Long uid, Long sid) {
        User user = verifyUser(userMapper,uid);
        Score s = scoreMapper.getScoreBySid(sid);
        if (s == null) {
            throw new ServiceException("该成绩不存在！");
        }
        if (user.getType() == 0) {

        } else if (user.getType() == 1) {
            if (courseMapper.findCourseByCid(s.getCourseId()) == null) {
                throw new ServiceException("不允许查询其他课程的成绩");
            }
        } else if (user.getType() == 2) {
            if (s.getStudentId() != uid) {
                throw new ServiceException("不允许查询其他人的成绩");
            }
            if (s.getIsSubmitted() != 1) {
                throw new ServiceException("成绩尚未被提交");
            }
        } else {
            throw new ServiceException("没有访问权限");
        }
        return s;
    }

    //getAllScore的旧版本
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

    //成绩修改
    @Override
    public Integer updateScore(Long uid, Score score) {
        //身份的验证
        User user = verifyUser(userMapper,uid);

        Score oldScore = scoreMapper.getScoreBySid(score.getSid());
        if (oldScore == null) {
            throw new ServiceException("要修改的成绩记录不存在");
        }

        Course course = courseMapper.findCourseByCid(oldScore.getCourseId());
        if (course == null) {
            throw new ServiceException("考试对应的课程信息不存在");
        }

        if (user.getType() == 0) {
            ;//管理员没有限制
        } else if (user.getType() == 1) {
            if (oldScore.getIsSubmitted() == 1) {
                throw new ServiceException("成绩记录已经被提交，无法修改");
            }
        } else {
            throw new ServiceException("用户没有修改的权限");
        }

        if (score.getSid() == null) {
            throw new ServiceException("要修改成绩记录的id未知");
        }

        Double totalScore = recalculateTotalScore(course, oldScore, score);
        score.setTotalScore(totalScore);

        //System.out.println("score = " + score);
        Integer row = scoreMapper.updateScore(score);
        if (row != 1) {
            throw new ServiceException("执行数据库更新操作失败");
        }

        return row;
    }

    //获得成绩排名
    @Override
    public Integer getTotalScorePosition(Long uid, Long sid) {
        User user = verifyUser(userMapper,uid);
        Score score = verifyScore(scoreMapper,sid);
        if (score.getIsSubmitted() != 1) {
            throw new ServiceException("成绩没有被提交");
        }

        if(score.getCourseId()==null){
            throw new ServiceException("课程ID为空");
        }
        Course course = courseMapper.findCourseByCid(score.getCourseId());
        if (course == null) {
            throw new ServiceException("课程记录不存在");
        }

        if (user.getType() == 0) ;//什么也不做
        else if (user.getType() == 1) {
            if (course.getTeacherId() != user.getUid()) {
                throw new ServiceException("教师只能查询自己课程的成绩排名");
            }
        } else if (user.getType() == 2) {
            if (user.getUid() != score.getStudentId()) {
                throw new ServiceException("学生只能查询自己的成绩排名");
            }
        } else {
            throw new ServiceException("不存在这样的用户");
        }

        Integer row = scoreMapper.getTotalScorePosition(sid, score.getCourseId());

        if (row == null) {
            throw new ServiceException("成绩排名查询失败");
        }
        return row;
    }

    @Override
    public List<CourseScore> getAllScoreByStudentId(Long uid, CourseScore courseScore) {
        //用户身份的校验
        User u = verifyUser(userMapper,uid);

        if (u.getType() == 0) {
            ;//可以查询任何人的成绩
        } else if (u.getType() == 2) {
            courseScore.setIsSubmitted(1);//只能查询提交过的成绩
            courseScore.setStudentId(uid);//只能查询自己的成绩
        } else {
            throw new ServiceException("不允许查看学生的全部成绩");
        }

        //List<Score> s = scoreMapper.getScore(courseScore);
        List<CourseScore> s = scoreMapper.getCourseScore(courseScore);

        if (s == null) {
            throw new ServiceException("查询成绩失败");
        }

        if (s.isEmpty()) {
            throw new ServiceException("查询结果为空");
        }
        return s;
    }

    @Override
    public List<StudentScore> getAllScoreByCid(StudentScore score, Long uid) {
        User u = verifyUser(userMapper,uid);

        if (score.getCourseId() == null) {
            throw new ServiceException("课程ID为空");
        }
        Course course = courseMapper.findCourseByCid(score.getCourseId());
        if (course == null) {
            throw new ServiceException("课程不存在");
        }

        if (u.getType() == 0) {
            ;//管理元查询，什么也不做
        } else if (u.getType() == 1) {
            if (!course.getTeacherId().equals(uid)) {
                throw new ServiceException("不允许查询其他教师课程的成绩");
            }
        } else {
            throw new ServiceException("没有权限查询成绩列表");
        }

        //List<Score> s = scoreMapper.getScore(score);
        List<StudentScore> s = scoreMapper.getStudentScoreList(score);
        if (s == null) {
            throw new ServiceException("成绩查找失败");
        }
        if (s.isEmpty()) {
            throw new ServiceException("查询结果为空");
        }
        return s;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Map<String, Object> getScoreAnalysis(Long uid, Long cid) {
        User user = verifyUser(userMapper,uid);
        if (cid == null) {
            throw new ServiceException("没有给出课程号");
        }
        Course course = courseMapper.findCourseByCid(cid);
        if (course == null) {
            throw new ServiceException("课程不存在");
        }
        if (user.getType() == 0) {
            //管理员
        } else if (user.getType() == 1) {
            //教师
            if (course.getTeacherId() != uid) {
                throw new ServiceException("不允许查看其他教师的成绩分析表");
            }
        } else {
            throw new ServiceException("没有访问权限");
        }

        Score cond = new Score();
        cond.setCourseId(cid);
        cond.setIsSubmitted(1);
        List<Score> scoreList = scoreMapper.getScore(cond);

        double[] segments = new double[4];
        try {
            Properties scoreProperties = new Properties();
            scoreProperties.load(new FileReader("src/main/resources/custom.properties"));
            segments[0] = Double.parseDouble(scoreProperties.getProperty("score.S"));
            segments[1] = Double.parseDouble(scoreProperties.getProperty("score.A"));
            segments[2] = Double.parseDouble(scoreProperties.getProperty("score.B"));
            segments[3] = Double.parseDouble(scoreProperties.getProperty("score.C"));
        } catch (IOException e) {
            segments[0] = 90;
            segments[1] = 80;
            segments[2] = 70;
            segments[3] = 60;
        }

        int[] counts = {0, 0, 0, 0, 0};
        double max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        double sum = 0;
        for (Score score : scoreList) {
            double total = score.getTotalScore();
            if (total > max) {
                max = total;
            }
            if (total < min) {
                min = total;
            }
            sum += total;
            int i = 0;
            while (i < 4 && total < segments[i]) {
                i++;
            }
            counts[i]++;
        }

        double average = scoreList.size() == 0 ? 0 : (sum / scoreList.size());

        sum = 0;
        for (Score score : scoreList) {
            double total = score.getTotalScore();
            sum += Math.pow(total - average, 2);
        }
        double variance = scoreList.size() == 0 ? 0 : (sum / scoreList.size());

        Map<String, Object> map = new HashMap<>();
        map.put("segments", counts);
        map.put("max", max);
        map.put("min", min);
        map.put("average", average);
        map.put("stddev", Math.sqrt(variance));
        return map;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Integer saveScoreAnalysisFile(Long uid, Long cid, String parentPath, Map<String, Object> map) {
        User user = verifyUser(userMapper,uid);
        if (cid == null) {
            throw new ServiceException("没有给出课程号");
        }
        Course course = courseMapper.findCourseByCid(cid);
        if (course == null) {
            throw new ServiceException("课程不存在");
        }
        if (user.getType() == 0) {
            //管理员
        } else if (user.getType() == 1) {
            //教师
            if (course.getTeacherId() != uid) {
                throw new ServiceException("不允许查看其他教师的成绩分析表");
            }
        } else {
            throw new ServiceException("没有访问权限");
        }

        String filename = System.currentTimeMillis() + ".xml";
        String file = parentPath + File.separator + filename;
        LOGGER.info("file = "+file);
        try {
            XMLParser.generateXMLFile(file, map);
        } catch (Exception e) {
            throw new ServiceException("文件存储失败");
        }

        Course modifiedCourse = new Course();
        modifiedCourse.setCid(course.getCid());
        modifiedCourse.setAnalysisTable(filename);
        Integer row = courseMapper.updateCourse(modifiedCourse);
        if (row != 1) {
            throw new ServiceException("数据库更新操作失败");
        }
        return row;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Map<String, Object> getScoreAnalysisFile(Long uid, Long cid, String parentPath) {
        User user = verifyUser(userMapper,uid);
        if (cid == null) {
            throw new ServiceException("没有给出课程号");
        }
        Course course = courseMapper.findCourseByCid(cid);
        if (course == null) {
            throw new ServiceException("课程不存在");
        }
        if (user.getType() == 0) {
            //管理员
        } else if (user.getType() == 1) {
            //教师
            if (!course.getTeacherId().equals(uid)) {
                throw new ServiceException("不允许查看其他教师的成绩分析表");
            }
        } else {
            throw new ServiceException("没有访问权限");
        }

        LOGGER.info("course = "+course);
        String filename = course.getAnalysisTable();
        if (StringUtils.isEmpty(filename)) {
            throw new ServiceException("没有保存的成绩分析表");
        }
        File file = new File(parentPath, filename);
        if (!file.exists()) {
            throw new ServiceException("原始文件丢失");
        }

        Map<String, Object> map = null;
        try {
            map = XMLParser.parseXMLFile(file.getAbsolutePath());
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new ServiceException("解析xml文件失败");
        }
        LOGGER.info("返回的结果:"+map);
        return map;
    }
}
