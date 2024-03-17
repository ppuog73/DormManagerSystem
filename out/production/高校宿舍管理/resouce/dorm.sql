/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : dorm

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2022-02-18 15:31:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_dormbuild
-- ----------------------------
DROP TABLE IF EXISTS `tb_dormbuild`;
CREATE TABLE `tb_dormbuild` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `disabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_dormbuild
-- ----------------------------
INSERT INTO `tb_dormbuild` VALUES ('1', '1号楼', '1号楼备注1', '1');
INSERT INTO `tb_dormbuild` VALUES ('2', '3号楼', '3号楼名字不变', '0');
INSERT INTO `tb_dormbuild` VALUES ('3', '2号楼', '2号楼备注1', '0');
INSERT INTO `tb_dormbuild` VALUES ('4', '测试', '', '1');
INSERT INTO `tb_dormbuild` VALUES ('5', '4号楼', '这栋楼都是四人间', null);
INSERT INTO `tb_dormbuild` VALUES ('6', '6号宿舍楼', '都是单人间', '0');

-- ----------------------------
-- Table structure for tb_manage_dormbuild
-- ----------------------------
DROP TABLE IF EXISTS `tb_manage_dormbuild`;
CREATE TABLE `tb_manage_dormbuild` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `dormBuild_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `dormBuild_id` (`dormBuild_id`),
  CONSTRAINT `tb_manage_dormbuild_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `tb_manage_dormbuild_ibfk_2` FOREIGN KEY (`dormBuild_id`) REFERENCES `tb_dormbuild` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_manage_dormbuild
-- ----------------------------
INSERT INTO `tb_manage_dormbuild` VALUES ('4', '12', '1');
INSERT INTO `tb_manage_dormbuild` VALUES ('5', '12', '2');
INSERT INTO `tb_manage_dormbuild` VALUES ('6', '20', '1');
INSERT INTO `tb_manage_dormbuild` VALUES ('7', '21', '1');
INSERT INTO `tb_manage_dormbuild` VALUES ('8', '21', '2');
INSERT INTO `tb_manage_dormbuild` VALUES ('18', '22', '1');
INSERT INTO `tb_manage_dormbuild` VALUES ('19', '22', '2');
INSERT INTO `tb_manage_dormbuild` VALUES ('25', '47', '1');
INSERT INTO `tb_manage_dormbuild` VALUES ('26', '49', '1');
INSERT INTO `tb_manage_dormbuild` VALUES ('31', '11', '1');
INSERT INTO `tb_manage_dormbuild` VALUES ('48', '50', '1');
INSERT INTO `tb_manage_dormbuild` VALUES ('49', '50', '4');
INSERT INTO `tb_manage_dormbuild` VALUES ('50', '22', '1');
INSERT INTO `tb_manage_dormbuild` VALUES ('51', '65', '1');
INSERT INTO `tb_manage_dormbuild` VALUES ('52', '65', '2');
INSERT INTO `tb_manage_dormbuild` VALUES ('54', '10', '1');

-- ----------------------------
-- Table structure for tb_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_record`;
CREATE TABLE `tb_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL COMMENT '学生id',
  `date` date DEFAULT NULL COMMENT '缺勤时间',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `disabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `tb_record_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_record
-- ----------------------------
INSERT INTO `tb_record` VALUES ('1', '32', '2021-02-06', '132123', '0');
INSERT INTO `tb_record` VALUES ('2', '35', '2021-01-06', '222', '1');
INSERT INTO `tb_record` VALUES ('3', '33', '2021-01-06', '22222', '0');
INSERT INTO `tb_record` VALUES ('4', '36', '2021-01-06', '353535', '0');
INSERT INTO `tb_record` VALUES ('5', '39', '2022-01-01', '123123', '0');
INSERT INTO `tb_record` VALUES ('6', '47', '2022-02-18', '123123', '0');
INSERT INTO `tb_record` VALUES ('7', '49', '2022-02-18', '132123', '0');
INSERT INTO `tb_record` VALUES ('8', '65', '2022-02-18', '1231', '0');
INSERT INTO `tb_record` VALUES ('9', '66', '2022-02-18', '132132', '0');
INSERT INTO `tb_record` VALUES ('10', '38', '2022-02-18', '123132', '0');
INSERT INTO `tb_record` VALUES ('11', '32', '2022-02-15', '迟到了', '0');
INSERT INTO `tb_record` VALUES ('12', '34', '2022-02-18', '今天缺勤', '0');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `passWord` varchar(20) NOT NULL,
  `stu_code` varchar(20) DEFAULT NULL COMMENT '学号',
  `dorm_Code` varchar(20) DEFAULT NULL COMMENT '宿舍编号',
  `sex` varchar(10) DEFAULT NULL,
  `tel` varchar(15) DEFAULT NULL,
  `dormBuildId` int(11) DEFAULT NULL COMMENT '宿舍楼id',
  `role_id` int(11) DEFAULT NULL COMMENT '0 表示超级管理员 1宿舍管理员 2学生',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `disabled` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `stu_code` (`stu_code`),
  KEY `dormBuildId` (`dormBuildId`),
  KEY `create_user_id` (`create_user_id`),
  CONSTRAINT `tb_user_ibfk_1` FOREIGN KEY (`dormBuildId`) REFERENCES `tb_dormbuild` (`id`),
  CONSTRAINT `tb_user_ibfk_2` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', '管理员1', '123456', '0001', null, null, '13539909876', null, '0', null, '0');
INSERT INTO `tb_user` VALUES ('10', '管理员2', '123456', '0002', null, '男', '13539909242', null, '1', '1', '0');
INSERT INTO `tb_user` VALUES ('11', '管理员3', '123456', '0003', null, '男', '13532342348', null, '1', '1', '0');
INSERT INTO `tb_user` VALUES ('12', '管理员4', '123456', '0004', null, '男', '13543257654', null, '1', '1', '0');
INSERT INTO `tb_user` VALUES ('20', '管理员5', '123456', '0005', null, '女', '13539909242', null, '1', '1', '0');
INSERT INTO `tb_user` VALUES ('21', '管理员7', '123456', '0006', null, '女', '13543257654', null, '1', '1', '0');
INSERT INTO `tb_user` VALUES ('22', '管理员9', '123456', '0007', null, '男', '13539909241', null, '1', '1', '0');
INSERT INTO `tb_user` VALUES ('32', '陈迪凯', '123456', '0008', '3-201', '男', '13532342341', '1', '2', '11', '0');
INSERT INTO `tb_user` VALUES ('33', '姜涛', '123456', '0009', '1-201', '男', '13532342342', '1', '2', '11', '0');
INSERT INTO `tb_user` VALUES ('34', '张勇', '123456', '0010', '3-101', '男', '13532342342', '2', '2', '20', '0');
INSERT INTO `tb_user` VALUES ('35', '妲己', '123456', '0011', '2-201', '女', '13532342342', '3', '2', '1', '0');
INSERT INTO `tb_user` VALUES ('36', '亚瑟', '123456', '0012', '1-101', '男', '13532342342', '1', '2', '1', '0');
INSERT INTO `tb_user` VALUES ('37', '小乔', '123456', '0013', '2-201', '女', '13532342346', '3', '2', '1', '0');
INSERT INTO `tb_user` VALUES ('38', '貂蝉', '123456', '0014', '5-201', '女', '13532342347', '4', '2', '1', '0');
INSERT INTO `tb_user` VALUES ('39', '吕布', '123456', '0015', '1-101', '男', '13532342342', '1', '2', '1', '0');
INSERT INTO `tb_user` VALUES ('47', '测试2', '123456', '0023', '1-101', '男', '13455567784', '1', '1', '1', '0');
INSERT INTO `tb_user` VALUES ('49', '测试1', '123456', '0025', '1-101', '男', '13455567784', '1', '1', '1', '1');
INSERT INTO `tb_user` VALUES ('50', '张三三', '123456', '0026', '1-101', '男', '13572132476', '1', '1', '1', '1');
INSERT INTO `tb_user` VALUES ('65', '阿萨德三', '111', '0028', '4-110', '男', '18771271203', '4', '1', '1', '0');
INSERT INTO `tb_user` VALUES ('66', '王八蛋', '111', '0032', '1-205', '男', '13572132476', '1', '2', '1', '0');
