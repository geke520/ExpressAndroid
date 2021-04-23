/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50703
Source Host           : 127.0.0.1:3306
Source Database       : express_scan_db

Target Server Type    : MYSQL
Target Server Version : 50703
File Encoding         : 65001

Date: 2021-01-10 22:40:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_name` varchar(20) NOT NULL,
  `admin_password` varchar(50) DEFAULT NULL,
  `admin_issystem` int(11) DEFAULT '0',
  PRIMARY KEY (`admin_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', '123123', '1');

-- ----------------------------
-- Table structure for express
-- ----------------------------
DROP TABLE IF EXISTS `express`;
CREATE TABLE `express` (
  `express_id` varchar(64) NOT NULL,
  `people_id` int(11) DEFAULT NULL,
  `express_price` varchar(20) DEFAULT NULL,
  `express_start_datetime` varchar(20) DEFAULT NULL,
  `express_status` int(5) DEFAULT '0' COMMENT '0待揽件，1运输中，2派送中，3已完成',
  PRIMARY KEY (`express_id`),
  KEY `people_id` (`people_id`),
  CONSTRAINT `express_ibfk_1` FOREIGN KEY (`people_id`) REFERENCES `people` (`people_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of express
-- ----------------------------
INSERT INTO `express` VALUES ('1', '1', '5', '2021-01-13 00:00:00', '0');

-- ----------------------------
-- Table structure for people
-- ----------------------------
DROP TABLE IF EXISTS `people`;
CREATE TABLE `people` (
  `people_id` int(11) NOT NULL AUTO_INCREMENT,
  `people_name` varchar(255) DEFAULT NULL,
  `people_phone` varchar(255) DEFAULT NULL,
  `people_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`people_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of people
-- ----------------------------
INSERT INTO `people` VALUES ('1', '3433', '5633', '78');

-- ----------------------------
-- Table structure for user_data
-- ----------------------------
DROP TABLE IF EXISTS `user_data`;
CREATE TABLE `user_data` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识',
  `user_phone` varchar(11) DEFAULT NULL COMMENT '用户手机号',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `user_head` varchar(2000) DEFAULT NULL,
  `user_sex` tinyint(4) DEFAULT '1' COMMENT '0男1女',
  `user_mail` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_data
-- ----------------------------
INSERT INTO `user_data` VALUES ('5', '13333333333', '123123', null, '1', null);

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password` (
  `user_id` int(11) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `user_password_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_data` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES ('5', '4297F44B13955235245B2497399D7A93');

-- ----------------------------
-- Table structure for user_people
-- ----------------------------
DROP TABLE IF EXISTS `user_people`;
CREATE TABLE `user_people` (
  `user_people_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `people_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_people_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_people
-- ----------------------------
