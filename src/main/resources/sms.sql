/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.5.59 : Database - sms
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sms` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `sms`;

/*Table structure for table `t_course` */

DROP TABLE IF EXISTS `t_course`;

CREATE TABLE `t_course` (
  `cid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `teacher_id` bigint(20) NOT NULL COMMENT '教师id',
  `term` int(11) NOT NULL COMMENT '学期',
  `sequence_no` char(32) NOT NULL COMMENT '课程序号',
  `course_no` char(32) NOT NULL COMMENT '课程代码',
  `name` varchar(64) NOT NULL COMMENT '课程名',
  `type` int(11) NOT NULL COMMENT '课程类型，0=未分类，1=通识教育课程，2=学科基础课程，3=专业发展课程，4=实践环节',
  `credit` double NOT NULL COMMENT '学分',
  `score_type` int(11) DEFAULT NULL COMMENT '分制，0=百分制，1=等级制',
  `usual_weight` double DEFAULT NULL COMMENT '平时成绩权重',
  `attendance_weight` double DEFAULT NULL COMMENT '考勤成绩权重',
  `assignment_weight` double DEFAULT NULL COMMENT '作业成绩权重',
  `experiment_weight` double DEFAULT NULL COMMENT '实验成绩权重',
  `midterm_weight` double DEFAULT NULL COMMENT '期中考试成绩权重',
  `finalexam_weight` double DEFAULT NULL COMMENT '期末考试成绩权重',
  `analysis_table` varchar(256) DEFAULT NULL COMMENT '成绩分析表',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_course` */

/*Table structure for table `t_score` */

DROP TABLE IF EXISTS `t_score`;

CREATE TABLE `t_score` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '成绩id',
  `student_id` bigint(20) NOT NULL COMMENT '学生id',
  `course_id` bigint(20) NOT NULL COMMENT '课程id',
  `usual_score` double DEFAULT NULL COMMENT '平时成绩',
  `attendance_score` double DEFAULT NULL COMMENT '考勤成绩',
  `assignment_score` double DEFAULT NULL COMMENT '作业成绩',
  `experiment_score` double DEFAULT NULL COMMENT '实验成绩',
  `midterm_score` double DEFAULT NULL COMMENT '期中考试成绩',
  `finalexam_score` double DEFAULT NULL COMMENT '期末考试成绩',
  `total_score` double DEFAULT NULL COMMENT '总评成绩',
  `reexam_score` double DEFAULT NULL COMMENT '补考成绩',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_score` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` char(32) NOT NULL COMMENT '密码',
  `type` int(11) NOT NULL DEFAULT '2' COMMENT '用户类型，0=管理员，1=教师，2=学生',
  `salt` char(36) NOT NULL COMMENT '盐值',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否标记为删除，1=删除，0=没有删除',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
