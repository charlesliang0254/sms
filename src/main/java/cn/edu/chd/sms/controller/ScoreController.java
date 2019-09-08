package cn.edu.chd.sms.controller;

import cn.edu.chd.sms.entity.Score;
import cn.edu.chd.sms.service.ScoreService;
import cn.edu.chd.sms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 成绩控制类
 */
@RestController
public class ScoreController {
    @Autowired
    private ScoreService scoreService;//成绩服务层

    //添加成绩
    @PostMapping("/score")
    public JsonResult doAddScore(Score score,HttpSession session){
        Long uid = (Long) session.getAttribute("uid");
        scoreService.addScore(score, uid);
        return new JsonResult("添加成绩成功");
    }

    //删除（撤销）成绩
    @DeleteMapping("/score/{sid}")
    public JsonResult doDeleteScore(HttpSession session,@PathVariable("sid") Long sid) {
        Long uid = (Long) session.getAttribute("uid");
        scoreService.removeScore(uid,sid);
        return new JsonResult();
    }

    //主键查询成绩
    @GetMapping("/score/{sid}")
    public JsonResult doGetScore(HttpSession session,@PathVariable("sid")Long sid){
        Long uid = (Long) session.getAttribute("uid");
        Score s = scoreService.getOneScore(uid, sid);
        return new JsonResult("查找成功",s);
    }

    //教师成绩查看
    @GetMapping("/course/{cid}/score")
    public JsonResult doGetScoreByCid(HttpSession session,@PathVariable("cid")Long cid){
        Long uid = (Long)session.getAttribute("uid");
        Score score = new Score();
        score.setCourseId(cid);
        List<Score> s = scoreService.getAllScoreByCid(score,uid);
        return new JsonResult("查询成功！",s);
    }

    //查询个人全部成绩
    @GetMapping("/score")
    public JsonResult doGetScoreList(HttpSession session,Score score){
        Long uid = (Long) session.getAttribute("uid");
        List<Score> scoreList = scoreService.getAllScoreByStudentId(uid,score);
        return new JsonResult("查询成功！",scoreList);
    }

    //查询某人的全部成绩
    @GetMapping("/student/{studentId}/score")
    public JsonResult doGetScoreListByUid(HttpSession session, @PathVariable("studentId") Long studentId, Score score) {
        Long uid = (Long) session.getAttribute("uid");
        score.setStudentId(studentId);
        List<Score> scoreList = scoreService.getAllScoreByStudentId(uid,score);
        return new JsonResult("成绩查询成功", scoreList);
    }

    //更新成绩
    @PutMapping("/score/{sid}")
    public JsonResult doUpdate(HttpSession session, @PathVariable("sid") Long sid, Score score) {
        System.out.println("sid = " + sid);
        System.out.println("score.getSid() = "+score.getSid());
        Long uid = (Long) session.getAttribute("uid");
        score.setSid(sid);
        scoreService.updateScore(uid,score);
        return new JsonResult("修改成功");
    }

    //获得某门课的排名
    @GetMapping("/course/{cid}/score/{sid}/position")
    public JsonResult doGetPositionByCid(HttpSession session,@PathVariable("cid") Long cid,@PathVariable("sid")Long sid){
        Long uid = (Long) session.getAttribute("uid");
        Integer row = scoreService.getTotalScorePosition(uid, sid, cid);
        return new JsonResult("ok",row);
    }
    //成绩提交
    @PutMapping("/score/{sid}/commit")
    public JsonResult doCommitScore(HttpSession session,@PathVariable("sid")Long sid){
        Long uid = (Long) session.getAttribute("uid");
        Score score = new Score();
        score.setSid(sid);
        score.setIsSubmitted(1);
        scoreService.updateScore(uid,score);
        return new JsonResult("成绩提交成功");
    }

    //生成成绩分析数据
    @GetMapping("/course/{cid}/score_analysis_data")
    public JsonResult doGetScoreAnalysisData(HttpSession session, @PathVariable("cid") Long cid) {
        Long uid = (Long) session.getAttribute("uid");
        Map<String,Object> map = scoreService.getScoreAnalysis(uid,cid);
        return new JsonResult("返回成绩分析数据成功",map);
    }

    //成绩分析文件保存
    @PostMapping("/course/{cid}/score_analysis")
    public JsonResult doCreateScoreAnalysisFile(HttpSession session, @PathVariable("cid") Long cid,@RequestParam Map<String,Object> data) {
        Long uid = (Long) session.getAttribute("uid");
        String parentPath = getParentPath(session);
        scoreService.saveScoreAnalysisFile(uid,cid,parentPath,data);
        return new JsonResult("成绩分析文件保存成功");
    }

    //成绩分析表的显示
    @GetMapping("course/{cid}/score_analysis")
    public JsonResult doGetScoreAnalysis(HttpSession session, @PathVariable("cid") Long cid){
        Long uid = (Long) session.getAttribute("uid");
        String parentPath = getParentPath(session);
        Map<String,Object> map = scoreService.getScoreAnalysisFile(uid,cid,parentPath);
        return new JsonResult("成绩分析文件加载完成",map);
    }

    private String getParentPath(HttpSession session) {
        String parentPath=null;
        try(FileReader in = new FileReader("src/main/resources/custom.properties")){
            Properties properties = new Properties();
            properties.load(in);
            parentPath = properties.getProperty("score.analysis.path");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(parentPath==null||parentPath.isEmpty()){
            parentPath=session.getServletContext().getRealPath("AnalysisFiles");
            File file = new File(parentPath);
            if(!file.exists()){
                file.mkdirs();
            }
        }
        return parentPath;
    }

}
