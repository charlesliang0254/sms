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
  `is_submitted` int(11) DEFAULT NULL COMMENT '是否提交，0=未提交，1=提交',
  PRIMARY KEY (`cid`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `t_course_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `t_user` (`uid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `t_course` */

insert  into `t_course`(`cid`,`teacher_id`,`term`,`sequence_no`,`course_no`,`name`,`type`,`credit`,`score_type`,`usual_weight`,`attendance_weight`,`assignment_weight`,`experiment_weight`,`midterm_weight`,`finalexam_weight`,`analysis_table`,`is_submitted`) values (6,27,1,'C1.1','C1','高等数学',1,5,0,0.3,0,0,0,0,0.7,'1568865618944.xml',NULL),(7,27,1,'C1.2','C1','高等数学',1,5,0,0.3,0,0,0,0,0.7,NULL,NULL),(8,27,1,'C1.3','C1','高等数学',1,5,0,0.3,0,0,0,0,0.7,NULL,NULL),(9,28,2,'C2.1','C2','数据结构',2,3,0,0.1,0.05,0.15,0.1,0,0.6,NULL,NULL),(10,28,2,'C2.2','C2','数据结构',2,3,0,0.1,0.05,0.15,0.1,0,0.6,NULL,NULL),(11,28,2,'C3.1','C3','数据结构课程设计',4,2,0,0,0,0,0,0,1,NULL,NULL),(12,28,2,'C3.2','C3','数据结构课程设计',4,2,0,0,0,0,0,NULL,1,NULL,NULL),(13,27,3,'C4.1','C4','高等代数',3,3,0,0.3,NULL,NULL,NULL,NULL,0.7,NULL,NULL);

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
  `is_submitted` int(11) DEFAULT '0' COMMENT '是否提交，0=未提交，1=提交',
  PRIMARY KEY (`sid`),
  KEY `student_id` (`student_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `t_score_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `t_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_score_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `t_course` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

/*Data for the table `t_score` */

insert  into `t_score`(`sid`,`student_id`,`course_id`,`usual_score`,`attendance_score`,`assignment_score`,`experiment_score`,`midterm_score`,`finalexam_score`,`total_score`,`reexam_score`,`is_submitted`) values (33,37,6,100,NULL,NULL,NULL,NULL,100,100,NULL,1),(35,39,6,70,NULL,NULL,NULL,NULL,60,63,NULL,1),(37,40,6,100,NULL,NULL,NULL,NULL,42,59.4,NULL,1),(38,41,7,96,NULL,NULL,NULL,NULL,81,85.5,NULL,0),(39,42,7,74,NULL,NULL,NULL,NULL,55,60.7,NULL,0),(40,43,7,100,NULL,NULL,NULL,NULL,32,52.4,NULL,0),(41,44,8,66,NULL,NULL,NULL,NULL,55,58.3,NULL,0),(42,45,8,95,NULL,NULL,NULL,NULL,100,98.5,NULL,0),(43,46,8,88,NULL,NULL,NULL,NULL,91,90.1,NULL,0),(47,38,6,60,NULL,NULL,NULL,NULL,80,74,NULL,1),(48,37,6,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,0);

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
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`uid`,`username`,`password`,`type`,`salt`,`is_delete`) values (26,'001','7C6D3322F1B1D99E5A01917D0CE2D6B5',0,'599dcdb2-a2d7-4bf0-b2f5-af8c0234d563',0),(27,'101','B155BA7DCC6030C178D56C369D6B76BC',1,'16b606b4-9352-41a7-bc63-555ca0a5dec3',0),(28,'102','421FBB830C5B61ABDB13800143B7D45D',1,'4f5851a7-d929-4fbe-96ee-7327017d43e3',0),(29,'103','0103E14EE76F192FF8FE55CA0E0D654E',1,'23effacf-14a8-40f5-98a3-79b6c29565d7',0),(30,'104','3AA2F46F1CAD36FB734949C3F4EEAACB',1,'7bca9ef4-1f51-4f54-b703-ecd50c2c2a28',0),(31,'105','67AD75BA6DC0412BB30F1319900FB176',1,'b9fa3e47-7340-44eb-adc6-07b260537865',0),(32,'106','E56A5FFC2F3270201BAF8826AD32D952',1,'2a8716f9-98aa-459f-963b-49eec8408a8c',0),(33,'107','07A1A2756F08249DA88A0A1FC9504C4E',1,'08056ea7-3248-4779-8ce0-c9f815c85ef7',0),(34,'108','96300967D708039EC6178B180176BFDC',1,'631aa8c3-445a-4a82-b57b-35287f3d88c7',0),(35,'109','F712EC418238C89E798D4D796E7A2316',1,'05cb0c41-5cde-42e0-9098-315e3aec2e97',0),(36,'110','078F3AA31104DCF9771DE9AF52513B13',1,'93f252c2-01fc-490d-8c5b-1a1757b4d24f',0),(37,'201','CC29FDBB891AC6EFA2E5E3C3544B87D7',2,'1451a663-bac4-4398-8b97-1687fab3a3f4',0),(38,'202','CE46DD082F2E44557DDA3A3492B93F13',2,'4a6982d2-aff0-48ee-b26d-611ae79fa37d',0),(39,'203','3DBD8386EDE4666F443555B0D46C0370',2,'953be18e-1824-4e69-98a0-7d6254576a0e',0),(40,'204','4D9DDF1585FFD8CA06C93FA66D06F5BF',2,'8fa0d79c-6571-4dcf-840f-df4ac7b72086',0),(41,'205','FD9DE4D116C134DC0066C7E407B7C526',2,'ff6d7e1f-c925-4915-9d37-b5e45f9cdb3e',0),(42,'206','64A795B3B9E0AC7258CEAE75349D45D5',2,'56dbceb9-6811-4b90-b87a-94aa51ebbad6',0),(43,'207','C9E1B0FACDB838CF6E094B86ACB7A4F1',2,'d12a34fa-1274-433e-9f90-68c1f8dbac7c',0),(44,'208','C42856097A25AF4D8BF1F8CFFE5D0D13',2,'1ad536c2-43ff-46c9-86f8-d3b4f8d55c5a',0),(45,'209','3EAC842F9DEA2EE1D41AFD2D39C17CC6',2,'e70698e9-ff26-4718-a541-d0b5f91e802c',0),(46,'210','28A3B8547E32200FED55AC5E815F5B04',2,'ed8db0e4-ee6f-4a4d-afa7-56f178fd3de6',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
