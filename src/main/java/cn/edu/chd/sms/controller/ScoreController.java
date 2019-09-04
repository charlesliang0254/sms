package cn.edu.chd.sms.controller;

import cn.edu.chd.sms.entity.Score;
import cn.edu.chd.sms.service.ScoreService;
import cn.edu.chd.sms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
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
}
