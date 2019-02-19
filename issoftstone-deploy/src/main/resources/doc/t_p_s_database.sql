/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : localhost:3306
 Source Schema         : news

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : 65001

 Date: 19/02/2019 17:44:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_p_s_database
-- ----------------------------
DROP TABLE IF EXISTS `t_p_s_database`;
CREATE TABLE `t_p_s_database`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` bit(1) NULL DEFAULT NULL COMMENT '是否删除：0 否 1 是',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '版本号,防重',
  `character_encoding` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据库字符集',
  `data_base_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据库名称',
  `data_base_type` int(4) NULL DEFAULT NULL COMMENT '数据库类型',
  `driver_class_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据库驱动',
  `enable` bit(1) NULL DEFAULT NULL COMMENT '是否启用 0 停用 1 启用',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据库IP',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据库密码',
  `port` int(5) NULL DEFAULT NULL COMMENT '数据库端口',
  `use_unicode` bit(1) NULL DEFAULT NULL COMMENT '是否使用unicode true 是 1 false',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '数据库用户名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_p_s_database
-- ----------------------------
INSERT INTO `t_p_s_database` VALUES ('35007600-938d-4a59-900e-db49682ab91e', '2019-02-19 10:02:46', b'0', NULL, 0, 'characterEncoding=utf-8', 'news3', 1, 'com.mysql.jdbc.Driver', b'1', 'localhost', 'root', 3306, b'1', 'root');
INSERT INTO `t_p_s_database` VALUES ('a74845c0-dd75-4b03-b969-cc258241c6b8', '2019-02-19 10:01:30', b'0', NULL, 0, 'characterEncoding=utf-8', 'news2', 1, 'com.mysql.jdbc.Driver', b'1', 'localhost', 'root', 3306, b'1', 'root');
INSERT INTO `t_p_s_database` VALUES ('ccd67302-c212-4f86-b388-8c9e73f62cb5', '2019-02-19 10:03:24', b'0', NULL, 0, 'characterEncoding=utf-8', 'news4', 1, 'com.mysql.jdbc.Driver', b'1', 'localhost', 'root', 3306, b'1', 'root');
INSERT INTO `t_p_s_database` VALUES ('ea1c21b0-8b94-4eb0-a9f1-bdc08c124b21', '2019-02-19 09:53:11', b'0', NULL, 0, 'characterEncoding=utf-8', 'news6', 1, 'com.mysql.jdbc.Driver', b'1', 'localhost', 'root', 3306, b'1', 'root');
INSERT INTO `t_p_s_database` VALUES ('ea1c21b0-8b94-4eb0-a9f1-bdc08c124b22', '2019-02-19 09:53:11', b'0', NULL, 0, 'characterEncoding=utf-8', 'news5', 1, 'com.mysql.jdbc.Driver', b'1', 'localhost', 'root', 3306, b'1', 'root');
INSERT INTO `t_p_s_database` VALUES ('ea1c21b0-8b94-4eb0-a9f1-bdc08c124b23', '2019-02-19 09:53:11', b'0', NULL, 0, 'characterEncoding=utf-8', 'news7', 1, 'com.mysql.jdbc.Driver', b'1', 'localhost', 'root', 3306, b'1', 'root');
INSERT INTO `t_p_s_database` VALUES ('ea1c21b0-8b94-4eb0-a9f1-bdc08c124b2f', '2019-02-19 09:53:11', b'0', NULL, 0, 'characterEncoding=utf-8', 'news1', 1, 'com.mysql.jdbc.Driver', b'1', 'localhost', 'root', 3306, b'1', 'root');

SET FOREIGN_KEY_CHECKS = 1;
