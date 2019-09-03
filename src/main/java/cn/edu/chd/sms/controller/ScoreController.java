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
    public JsonResult doDeleteScore(@PathVariable("sid") Long sid) {
        scoreService.removeScore(sid);
        return new JsonResult();
    }

    //主键查询成绩
    @GetMapping("/score/{sid}")
    public JsonResult doGetScore(@PathVariable("sid")Long sid){
        Score s = scoreService.getOneScore(sid);
        return new JsonResult("查找成功",s);
    }

    //查找选修某门课程的学生成绩
    @GetMapping("/course/{cid}/score")
    public JsonResult doGetScoreByCid(HttpSession session,@PathVariable("cid")Long cid){
        //TODO 查找选修某门课程的学生成绩
        return new JsonResult();
    }

    //非主键查询成绩
    @GetMapping("/score")
    public JsonResult doGetScoreList(Score score){
        List<Score> s = scoreService.getAllScore(score);
        return new JsonResult("查询成功！",s);
    }

    //更新成绩
    @PutMapping("/score/{sid}")
    public JsonResult doUpdate(HttpSession session,@PathVariable("sid") Long sid,Score score) {
  /*      User user = new User();
        user.setUid((Long) session.getAttribute("uid"));
        user.setType((Integer) session.getAttribute("type"));*/
        scoreService.updateScore(score);
        return new JsonResult("修改成功");
    }

    //获得某门课的排名
    @GetMapping("/course/{cid}/student/{uid}/position")
    public JsonResult doGetPositionByCid(HttpSession session,@PathVariable("cid") Long cid,@PathVariable("uid")Long uid){
        //TODO
        return new JsonResult();
    }
}
