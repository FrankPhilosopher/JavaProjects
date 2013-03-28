/*
Navicat MySQL Data Transfer

Source Server         : locahost_root
Source Server Version : 60011
Source Host           : localhost:3306
Source Database       : patientims

Target Server Type    : MYSQL
Target Server Version : 60011
File Encoding         : 65001

Date: 2011-10-30 10:33:53
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_department`
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO t_department VALUES ('1', 'Brain');
INSERT INTO t_department VALUES ('2', 'Nose');
INSERT INTO t_department VALUES ('3', 'Bone');
INSERT INTO t_department VALUES ('4', 'Eye');
INSERT INTO t_department VALUES ('5', 'Throat');

-- ----------------------------
-- Table structure for `t_diagnose`
-- ----------------------------
DROP TABLE IF EXISTS `t_diagnose`;
CREATE TABLE `t_diagnose` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `illness` varchar(100) DEFAULT NULL,
  `treatment` varchar(500) DEFAULT NULL,
  `diagnosedate` datetime DEFAULT NULL,
  `patient_id` bigint(20) DEFAULT NULL,
  `doctor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_diagnose_doctor` (`doctor_id`),
  KEY `FK_diagnose_patient` (`patient_id`),
  CONSTRAINT `FK_diagnose_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `t_doctor` (`id`),
  CONSTRAINT `FK_diagnose_patient` FOREIGN KEY (`patient_id`) REFERENCES `t_patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_diagnose
-- ----------------------------

-- ----------------------------
-- Table structure for `t_doctor`
-- ----------------------------
DROP TABLE IF EXISTS `t_doctor`;
CREATE TABLE `t_doctor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `logtime` datetime DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_doctor_department` (`department_id`),
  CONSTRAINT `FK_doctor_department` FOREIGN KEY (`department_id`) REFERENCES `t_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_doctor
-- ----------------------------
INSERT INTO t_doctor VALUES ('1', 'yinger', 'female', 'yinger', '090807', '20', '13752564825', 'Principal', '2011-10-30 09:43:46', '1');

-- ----------------------------
-- Table structure for `t_expense`
-- ----------------------------
DROP TABLE IF EXISTS `t_expense`;
CREATE TABLE `t_expense` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dec` varchar(50) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `unitprice` float(20,0) DEFAULT NULL,
  `number` int(20) DEFAULT NULL,
  `occurexpense` float(20,0) DEFAULT NULL,
  `occurtime` datetime DEFAULT NULL,
  `patient_id` bigint(20) DEFAULT NULL,
  `doctor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_expense_doctor` (`doctor_id`),
  KEY `FK_expense_patient` (`patient_id`),
  CONSTRAINT `FK_expense_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `t_doctor` (`id`),
  CONSTRAINT `FK_expense_patient` FOREIGN KEY (`patient_id`) REFERENCES `t_patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_expense
-- ----------------------------

-- ----------------------------
-- Table structure for `t_patient`
-- ----------------------------
DROP TABLE IF EXISTS `t_patient`;
CREATE TABLE `t_patient` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `logtime` datetime DEFAULT NULL,
  `sickbed_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_patient_sickbed` (`sickbed_id`),
  CONSTRAINT `FK_patient_sickbed` FOREIGN KEY (`sickbed_id`) REFERENCES `t_sickbed` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_patient
-- ----------------------------
INSERT INTO t_patient VALUES ('6', 'patient1', 'male', '37', '1533535354', 'TianJin', '2011-10-10 00:00:00', '61');
INSERT INTO t_patient VALUES ('7', 'patient2', 'male', '37', '1533535354', 'TianJin', '2011-10-10 00:00:00', '62');
INSERT INTO t_patient VALUES ('8', 'patient3', 'male', '37', '1533535354', 'TianJin', '2011-10-10 00:00:00', '63');
INSERT INTO t_patient VALUES ('9', 'patient4', 'male', '37', '1533535354', 'TianJin', '2011-10-10 00:00:00', '64');
INSERT INTO t_patient VALUES ('10', 'patient5', 'male', '37', '1533535354', 'TianJin', '2011-10-10 00:00:00', '65');
INSERT INTO t_patient VALUES ('21', 'patient1', 'female', '23', '1235132535', 'fasdf', '2011-10-09 00:00:00', '66');
INSERT INTO t_patient VALUES ('22', 'patient2', 'female', '23', '1235132535', 'fasdf', '2011-10-09 00:00:00', '67');
INSERT INTO t_patient VALUES ('24', 'patient4', 'female', '23', '1235132535', 'fasdf', '2011-10-09 00:00:00', '69');
INSERT INTO t_patient VALUES ('25', 'patient5', 'female', '23', '1235132535', 'fasdf', '2011-10-09 00:00:00', '70');
INSERT INTO t_patient VALUES ('26', 'yao', 'Male', '21', '105702350235', 'hgalsdhglhas', '2011-10-29 21:48:42', '81');
INSERT INTO t_patient VALUES ('27', 'hujiawei', 'Male', '23', '501273507', 'falsdhglasdh', '2011-10-30 09:36:20', '110');

-- ----------------------------
-- Table structure for `t_sickbed`
-- ----------------------------
DROP TABLE IF EXISTS `t_sickbed`;
CREATE TABLE `t_sickbed` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sickbedno` bigint(20) DEFAULT NULL,
  `sickroom_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sickbed_sickroom` (`sickroom_id`),
  CONSTRAINT `FK_sickbed_sickroom` FOREIGN KEY (`sickroom_id`) REFERENCES `t_sickroom` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sickbed
-- ----------------------------
INSERT INTO t_sickbed VALUES ('61', '1', '1');
INSERT INTO t_sickbed VALUES ('62', '2', '1');
INSERT INTO t_sickbed VALUES ('63', '3', '1');
INSERT INTO t_sickbed VALUES ('64', '4', '1');
INSERT INTO t_sickbed VALUES ('65', '5', '1');
INSERT INTO t_sickbed VALUES ('66', '6', '1');
INSERT INTO t_sickbed VALUES ('67', '1', '2');
INSERT INTO t_sickbed VALUES ('68', '2', '2');
INSERT INTO t_sickbed VALUES ('69', '3', '2');
INSERT INTO t_sickbed VALUES ('70', '4', '2');
INSERT INTO t_sickbed VALUES ('71', '5', '2');
INSERT INTO t_sickbed VALUES ('72', '6', '2');
INSERT INTO t_sickbed VALUES ('73', '1', '3');
INSERT INTO t_sickbed VALUES ('74', '2', '3');
INSERT INTO t_sickbed VALUES ('75', '3', '3');
INSERT INTO t_sickbed VALUES ('76', '4', '3');
INSERT INTO t_sickbed VALUES ('77', '5', '3');
INSERT INTO t_sickbed VALUES ('78', '6', '3');
INSERT INTO t_sickbed VALUES ('79', '1', '4');
INSERT INTO t_sickbed VALUES ('80', '2', '4');
INSERT INTO t_sickbed VALUES ('81', '3', '4');
INSERT INTO t_sickbed VALUES ('82', '4', '4');
INSERT INTO t_sickbed VALUES ('83', '5', '4');
INSERT INTO t_sickbed VALUES ('84', '6', '4');
INSERT INTO t_sickbed VALUES ('85', '1', '5');
INSERT INTO t_sickbed VALUES ('86', '2', '5');
INSERT INTO t_sickbed VALUES ('87', '3', '5');
INSERT INTO t_sickbed VALUES ('88', '4', '5');
INSERT INTO t_sickbed VALUES ('89', '5', '5');
INSERT INTO t_sickbed VALUES ('90', '6', '5');
INSERT INTO t_sickbed VALUES ('91', '1', '6');
INSERT INTO t_sickbed VALUES ('92', '2', '6');
INSERT INTO t_sickbed VALUES ('93', '3', '6');
INSERT INTO t_sickbed VALUES ('94', '4', '6');
INSERT INTO t_sickbed VALUES ('95', '5', '6');
INSERT INTO t_sickbed VALUES ('96', '6', '6');
INSERT INTO t_sickbed VALUES ('97', '1', '7');
INSERT INTO t_sickbed VALUES ('98', '2', '7');
INSERT INTO t_sickbed VALUES ('99', '3', '7');
INSERT INTO t_sickbed VALUES ('100', '4', '7');
INSERT INTO t_sickbed VALUES ('101', '5', '7');
INSERT INTO t_sickbed VALUES ('102', '6', '7');
INSERT INTO t_sickbed VALUES ('103', '1', '8');
INSERT INTO t_sickbed VALUES ('104', '2', '8');
INSERT INTO t_sickbed VALUES ('105', '3', '8');
INSERT INTO t_sickbed VALUES ('106', '4', '8');
INSERT INTO t_sickbed VALUES ('107', '5', '8');
INSERT INTO t_sickbed VALUES ('108', '6', '8');
INSERT INTO t_sickbed VALUES ('109', '1', '9');
INSERT INTO t_sickbed VALUES ('110', '2', '9');
INSERT INTO t_sickbed VALUES ('111', '3', '9');
INSERT INTO t_sickbed VALUES ('112', '4', '9');
INSERT INTO t_sickbed VALUES ('113', '5', '9');
INSERT INTO t_sickbed VALUES ('114', '6', '9');
INSERT INTO t_sickbed VALUES ('115', '1', '10');
INSERT INTO t_sickbed VALUES ('116', '2', '10');
INSERT INTO t_sickbed VALUES ('117', '3', '10');
INSERT INTO t_sickbed VALUES ('118', '4', '10');
INSERT INTO t_sickbed VALUES ('119', '5', '10');
INSERT INTO t_sickbed VALUES ('120', '6', '10');

-- ----------------------------
-- Table structure for `t_sickroom`
-- ----------------------------
DROP TABLE IF EXISTS `t_sickroom`;
CREATE TABLE `t_sickroom` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sickroomno` bigint(20) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sickroom_department` (`department_id`),
  CONSTRAINT `FK_sickroom_department` FOREIGN KEY (`department_id`) REFERENCES `t_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sickroom
-- ----------------------------
INSERT INTO t_sickroom VALUES ('1', '101', '1');
INSERT INTO t_sickroom VALUES ('2', '102', '1');
INSERT INTO t_sickroom VALUES ('3', '201', '2');
INSERT INTO t_sickroom VALUES ('4', '202', '2');
INSERT INTO t_sickroom VALUES ('5', '301', '3');
INSERT INTO t_sickroom VALUES ('6', '302', '3');
INSERT INTO t_sickroom VALUES ('7', '401', '4');
INSERT INTO t_sickroom VALUES ('8', '402', '4');
INSERT INTO t_sickroom VALUES ('9', '501', '5');
INSERT INTO t_sickroom VALUES ('10', '502', '5');
