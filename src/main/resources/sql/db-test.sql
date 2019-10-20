/*
 Navicat Premium Data Transfer

 Source Server         : personal server
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           : 
 Source Schema         : db-test

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 07/10/2019 12:22:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_index
-- ----------------------------
DROP TABLE IF EXISTS `order_index`;
CREATE TABLE `order_index` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11697 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for order_item_index
-- ----------------------------
DROP TABLE IF EXISTS `order_item_index`;
CREATE TABLE `order_item_index` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23246 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
