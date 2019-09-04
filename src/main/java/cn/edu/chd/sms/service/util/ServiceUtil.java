package cn.edu.chd.sms.service.util;

import cn.edu.chd.sms.entity.Course;
import cn.edu.chd.sms.entity.Score;

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
}
