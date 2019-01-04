/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50641
 Source Host           : localhost:3306
 Source Schema         : news

 Target Server Type    : MySQL
 Target Server Version : 50641
 File Encoding         : 65001

 Date: 04/01/2019 17:58:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_p_a_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_p_a_menu`;
CREATE TABLE `t_p_a_menu`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `enable` bit(1) NULL DEFAULT NULL,
  `local_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `locked` bit(1) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `icon_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `p_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_91c9f6uw94lvlmjpgrhpv8hj3`(`icon_id`) USING BTREE,
  INDEX `FK_l24erj08nhw71fnr1g0q55bb6`(`p_id`) USING BTREE,
  CONSTRAINT `FK_91c9f6uw94lvlmjpgrhpv8hj3` FOREIGN KEY (`icon_id`) REFERENCES `t_p_a_icon` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_l24erj08nhw71fnr1g0q55bb6` FOREIGN KEY (`p_id`) REFERENCES `t_p_a_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_p_a_menu
-- ----------------------------
INSERT INTO `t_p_a_menu` VALUES ('0339b9cf-cb90-409d-8291-edec7c8a1033', '2019-01-04 17:01:53', b'0', '2019-01-04 17:01:53', 'icon-manage', b'1', 'icon-manage', b'0', '图标管理', '/platform/access/icon/list.do', NULL, NULL);
INSERT INTO `t_p_a_menu` VALUES ('241790e3-b26e-451d-aa8f-0f5e26e9b454', '2019-01-04 16:59:38', b'0', '2019-01-04 16:59:38', 'monitor-manage', b'1', 'monitor-manage', b'0', '监控管理', '#', NULL, NULL);
INSERT INTO `t_p_a_menu` VALUES ('2885f353-239a-4ab7-969d-96037a4ea3b7', '2019-01-04 17:00:22', b'0', '2019-01-04 17:00:22', 'menu-managge', b'1', 'menu-managge', b'0', '菜单管理', '/platform/access/menu/list.do', NULL, NULL);
INSERT INTO `t_p_a_menu` VALUES ('500e3c6f-1fab-4d68-b247-7452c259869c', '2019-01-04 17:03:32', b'0', '2019-01-04 17:03:32', 'exceptionlog', b'1', 'exceptionlog-manage', b'0', '异常日志', '/platform/access/exceptionlog/list.do', NULL, NULL);
INSERT INTO `t_p_a_menu` VALUES ('900e3699-b1d5-442b-a137-c81708795e37', '2019-01-04 16:53:23', b'0', '2019-01-04 16:53:23', 'deploy-manage', b'1', 'deploy-manage', b'0', '发布管理', '#', NULL, NULL);
INSERT INTO `t_p_a_menu` VALUES ('a76e6489-b14d-43ae-a81f-0313e502245a', '2019-01-04 17:02:44', b'0', '2019-01-04 17:02:44', 'optlog-manage', b'1', 'optlog-manage', b'0', '操作日志', '/platform/access/optlog/list.do', NULL, NULL);
INSERT INTO `t_p_a_menu` VALUES ('c90978e8-cb0c-4265-9420-dbbfab83d1b8', '2019-01-04 16:58:48', b'0', '2019-01-04 16:58:48', 'user-manage', b'1', 'user-manage', b'0', '用户管理', '/platform/access/user/list.do', NULL, NULL);
INSERT INTO `t_p_a_menu` VALUES ('e49a462f-e78a-4ef9-b04d-84d746ba4dc6', '2019-01-04 16:45:01', b'0', '2019-01-04 16:45:01', 'platform-manage', b'1', 'platform-manage', b'0', '平台管理', '#', NULL, NULL);
INSERT INTO `t_p_a_menu` VALUES ('ebc71bba-ebdd-458c-a846-4749b0e85e8e', '2019-01-04 16:54:13', b'0', '2019-01-04 16:54:13', 'access-manage', b'1', 'access-manage', b'0', '权限管理', '#', NULL, NULL);
INSERT INTO `t_p_a_menu` VALUES ('f71cd277-dda7-40a4-80a7-a2af19c80584', '2019-01-04 16:55:16', b'0', '2019-01-04 16:55:16', 'role-manage', b'1', 'role-manage', b'0', '角色管理', '/platform/access/role/list.do', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
