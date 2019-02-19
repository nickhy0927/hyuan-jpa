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

 Date: 19/02/2019 17:44:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_p_a_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_p_a_menu`;
CREATE TABLE `t_p_a_menu`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` bit(1) NULL DEFAULT NULL COMMENT '是否删除：0 否 1 是',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '版本号,防重',
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `enable` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `local_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `locked` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `orders` int(11) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `icon_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主键ID',
  `p_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '主键ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_91c9f6uw94lvlmjpgrhpv8hj3`(`icon_id`) USING BTREE,
  INDEX `FK_l24erj08nhw71fnr1g0q55bb6`(`p_id`) USING BTREE,
  CONSTRAINT `FK_91c9f6uw94lvlmjpgrhpv8hj3` FOREIGN KEY (`icon_id`) REFERENCES `t_p_a_icon` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_p_a_menu
-- ----------------------------
INSERT INTO `t_p_a_menu` VALUES ('003d39ce-5f3e-462e-81bb-bc5f7a23b607', '2019-02-15 16:44:33', b'0', '2019-02-15 16:45:04', 1, 'iconEditUpdate', '1', '#', '0', '图标修改-保存图标请求', 2, '/platform/access/icon/iconEditUpdate.json', NULL, 'a5473f9b-6bc5-4daa-b5ce-c05e5f9c9fcf');
INSERT INTO `t_p_a_menu` VALUES ('03a56961-5ce1-41ed-a3da-f42140dcb3df', '2019-02-15 17:45:45', b'0', NULL, 0, 'roleEditUpdate', '1', '#', '0', '角色修改-保存数据请求', 2, '/platform/access/role/roleEditUpdate.json', NULL, '6af7ef28-0214-4c38-8645-725abd62387b');
INSERT INTO `t_p_a_menu` VALUES ('056904fa-c58d-4252-8e09-3906b345755c', '2019-02-15 16:21:13', b'0', NULL, 0, 'iconList', '1', '#', '0', '图标列表', 4, '#', '5fc92edc-f191-446c-89a6-26033a6c88ce', '522199a8-5325-4ddc-be7a-8b23e3a19a6b');
INSERT INTO `t_p_a_menu` VALUES ('08364282-52a4-49a7-9c96-bc4c92587716', '2019-02-15 16:22:00', b'0', '2019-02-15 16:45:36', 3, 'iconList', '1', '#', '0', '图标列表-图标分页请求', 1, '/platform/access/icon/iconList.json', NULL, '056904fa-c58d-4252-8e09-3906b345755c');
INSERT INTO `t_p_a_menu` VALUES ('0963f209-cd0a-4197-93ae-7e058ceb4bdf', '2019-02-15 23:46:25', b'0', NULL, 0, 'autoTaskCraeteSave', '1', '#', '0', '调度任务新增-保存数据请求', 2, '/platform/access/autoTask/autoTaskCreateSave.json', NULL, '168aa69f-a999-49b5-8c7a-bb30c012bc45');
INSERT INTO `t_p_a_menu` VALUES ('0ce83191-c8e2-4385-ad41-2a52c5b7da3f', '2019-02-15 15:09:32', b'0', '2019-02-15 15:20:48', 1, 'deploy-manage', '1', 'deploy.manage', '0', '发布管理', 3, '#', '913ac920-7521-4246-a35e-cb0ac0edf841', NULL);
INSERT INTO `t_p_a_menu` VALUES ('0d9803c8-1a78-4d1b-9af4-4609bdaf074f', '2019-02-17 13:22:49', b'0', NULL, 0, 'classification-manage', '1', 'classification.manage', '0', '分类管理', 1, '/blog/classification/classificationList.do', '4886afbb-aae5-4757-bfb9-1136812b7d6e', '8ef3c5ac-e0d5-4bd2-b3b3-d4d4852aeeea');
INSERT INTO `t_p_a_menu` VALUES ('10e88f8a-8ca5-4886-9098-ac265800d0e2', '2019-02-16 00:12:17', b'0', NULL, 0, 'jobLogView', '1', '#', '0', '调度任务日志查看-日志详情', 1, '/platform/system/jobLog/jobLogView.json', NULL, 'f68cb6fa-bd4f-4f80-aed5-9321c22630b4');
INSERT INTO `t_p_a_menu` VALUES ('126cc624-cc97-4e20-88f4-5347881fd4f2', '2019-02-15 16:48:56', b'0', '2019-02-15 16:49:05', 1, 'menuList', '1', 'com.iss.platform.access.menu.controller.MenuController.menuList', '0', '菜单管理', 3, '/platform/access/menu/menuList.do', '4886afbb-aae5-4757-bfb9-1136812b7d6e', '5e2d6ab2-1db6-4291-98bf-ceb28c3aa435');
INSERT INTO `t_p_a_menu` VALUES ('15c967c6-d857-4c0e-8d28-cfe56cb0ce10', '2019-02-15 22:14:42', b'0', NULL, 0, 'userList', '1', 'com.iss.platform.access.user.controller.UserController.userList', '0', '用户管理', 5, '/platform/access/user/userList.do', '4886afbb-aae5-4757-bfb9-1136812b7d6e', '5e2d6ab2-1db6-4291-98bf-ceb28c3aa435');
INSERT INTO `t_p_a_menu` VALUES ('168aa69f-a999-49b5-8c7a-bb30c012bc45', '2019-02-15 23:45:20', b'0', NULL, 0, 'autoTaskCreate', '1', '#', '0', '调度任务新增', 1, '/platform/system/autotask/autoTaskCreate.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', 'e99d1f9b-e6a0-4dd8-ab45-73d72cd01cae');
INSERT INTO `t_p_a_menu` VALUES ('1fd4795f-22fc-4d88-9f99-52a2788c3d75', '2019-02-16 00:01:33', b'0', NULL, 0, 'jobLogList', '1', '#', '0', '调度任务日志', 2, '/platform/system/jobLog/jobLogList.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', '46d756e9-2b99-4354-873a-3db6c9214777');
INSERT INTO `t_p_a_menu` VALUES ('239960c5-4c13-45b6-8e68-60fd6befe15e', '2019-02-15 17:30:32', b'0', NULL, 0, 'roleCreate', '1', '#', '0', '角色新增', 2, '/platform/access/role/roleCreate.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', '23fea1b7-d45b-42ca-a06c-f23d2a63480d');
INSERT INTO `t_p_a_menu` VALUES ('23fea1b7-d45b-42ca-a06c-f23d2a63480d', '2019-02-15 17:25:06', b'0', NULL, 0, 'roleList', '1', 'com.iss.platform.access.role.controller.RoleController.roleList', '0', '角色管理', 4, '/platform/access/role/roleList.do', '4886afbb-aae5-4757-bfb9-1136812b7d6e', '5e2d6ab2-1db6-4291-98bf-ceb28c3aa435');
INSERT INTO `t_p_a_menu` VALUES ('25308fdf-8e92-42ac-ab93-577f2e516946', '2019-02-15 23:41:37', b'0', NULL, 0, 'autoTaskList', '1', '#', '0', '调度任务列表', 1, '#', '5fc92edc-f191-446c-89a6-26033a6c88ce', 'e99d1f9b-e6a0-4dd8-ab45-73d72cd01cae');
INSERT INTO `t_p_a_menu` VALUES ('27070ec7-c5be-4240-a5cd-825779e8c11f', '2019-02-15 16:43:14', b'0', '2019-02-15 16:44:53', 1, 'iconEditJson', '1', '#', '0', '图标修改-查询详情请求', 1, '/platform/access/icon/iconEditJson.json', NULL, 'a5473f9b-6bc5-4daa-b5ce-c05e5f9c9fcf');
INSERT INTO `t_p_a_menu` VALUES ('2b34f07c-38ea-4478-b3b4-dd84f4f13d3c', '2019-02-16 00:14:41', b'0', '2019-02-16 15:36:43', 5, 'jobLogDelete', '1', '#', '0', '调度任务日志删除', 3, '/platform/system/jobLog/jobLogDelete.json', '5fc92edc-f191-446c-89a6-26033a6c88ce', '1fd4795f-22fc-4d88-9f99-52a2788c3d75');
INSERT INTO `t_p_a_menu` VALUES ('2b581c51-0725-44db-9a66-d966bbc4aedf', '2019-02-15 17:26:11', b'0', NULL, 0, 'roleList', '1', '#', '0', '角色列表', 1, '#', '5fc92edc-f191-446c-89a6-26033a6c88ce', '23fea1b7-d45b-42ca-a06c-f23d2a63480d');
INSERT INTO `t_p_a_menu` VALUES ('2d005e30-bb13-4e20-916a-14da04e2a82a', '2019-02-15 22:37:33', b'0', '2019-02-15 22:40:24', 1, 'userEditUpdate', '1', '#', '0', '用户修改-保存数据请求', 2, '/platform/access/user/userEditUpdate.json', NULL, '31ea65d7-8aa2-44d4-a527-69964c274046');
INSERT INTO `t_p_a_menu` VALUES ('2dd544b3-b755-46ef-bd46-8e783f5a72f6', '2019-02-15 17:06:53', b'0', NULL, 0, 'menuList', '1', '#', '0', '菜单列表-列表分页请求', 1, '/platform/access/menu/menuList.json', NULL, 'e2ddb7b4-d775-4f0c-b297-ffa685527fc2');
INSERT INTO `t_p_a_menu` VALUES ('30442d6a-9ba7-4e5d-957f-76896f26760f', '2019-02-15 15:55:03', b'0', '2019-02-15 16:07:00', 6, 'dictList', '1', '#', '0', '数据字典列表', 1, '#', '5fc92edc-f191-446c-89a6-26033a6c88ce', 'c3a2c0c2-5a93-42cf-a0f5-71f564f0b62e');
INSERT INTO `t_p_a_menu` VALUES ('31ea65d7-8aa2-44d4-a527-69964c274046', '2019-02-15 22:20:31', b'0', '2019-02-15 22:37:48', 1, 'userEdit', '1', '#', '0', '用户修改', 3, '/platform/access/user/userEdit.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', '15c967c6-d857-4c0e-8d28-cfe56cb0ce10');
INSERT INTO `t_p_a_menu` VALUES ('39025058-8fb4-4e6f-845f-3cec666aaf6b', '2019-02-16 00:03:07', b'0', '2019-02-16 00:04:02', 1, 'jobLogList', '1', '#', '0', '调度任务日志列表-列表分页请求', 1, '/platform/system/jobLog/jobLogList.json', NULL, '83606f32-9af4-4600-a2a3-03e3bf9c5b7b');
INSERT INTO `t_p_a_menu` VALUES ('39da4fbb-9026-4808-9685-4471e41532d5', '2019-02-15 17:44:57', b'0', '2019-02-15 17:45:08', 1, 'roleEditJson', '1', '#', '0', '角色修改-获取角色详情', 2, '/platform/access/role/roleEditJson.json', NULL, '6af7ef28-0214-4c38-8645-725abd62387b');
INSERT INTO `t_p_a_menu` VALUES ('3a96cc99-240a-40b9-bd1d-752483901afc', '2019-02-15 16:07:52', b'0', '2019-02-15 17:17:34', 1, 'dictCreateSave', '1', '#', '0', '数据字典新增-保存数据请求', 1, '/platform/access/dict/dictCreateSave.json', NULL, '69b4fb32-c59c-4b51-b994-7cf9e744ee9a');
INSERT INTO `t_p_a_menu` VALUES ('3aa12ab6-edd6-4612-b756-0dc57eab550f', '2019-02-15 22:42:51', b'0', '2019-02-15 23:26:27', 2, 'userList', '1', '#', '0', '用户列表-获取列表请求', 1, '/platform/access/user/userList.json', NULL, 'dacc0034-711c-43fd-9de6-2769590c265b');
INSERT INTO `t_p_a_menu` VALUES ('3dafdfc6-adb1-4da1-bc7f-173c52779358', '2019-02-15 17:11:17', b'0', '2019-02-15 17:11:34', 1, 'menuEditUpdate', '1', '#', '0', '菜单修改-保存数据请求', 2, '/platform/access/menu/menuEditUpdate.json', NULL, '8aaadf85-c90f-436d-892b-f7a6f71ad68e');
INSERT INTO `t_p_a_menu` VALUES ('46d756e9-2b99-4354-873a-3db6c9214777', '2019-02-15 23:35:08', b'0', '2019-02-15 23:36:49', 1, 'system-manage', '1', 'system.manage', '0', '系统管理', 2, '#', '4557ada1-1da0-4af1-924e-c2d52aa0d187', '52513434-2d52-4c7f-b152-1b63fbbca288');
INSERT INTO `t_p_a_menu` VALUES ('46e61c69-b99f-4711-a1dc-d253d949c3c8', '2019-02-17 16:16:04', b'0', NULL, 0, 'tagsList', '1', 'tagsList', '0', '标签管理', 2, '/blog/tags/tagsList.do', '4886afbb-aae5-4757-bfb9-1136812b7d6e', '8ef3c5ac-e0d5-4bd2-b3b3-d4d4852aeeea');
INSERT INTO `t_p_a_menu` VALUES ('46ee7979-9fe4-4645-a617-ab87e860f2cd', '2019-02-15 15:06:07', b'0', '2019-02-15 15:09:27', 1, 'index', '0', '#', '0', '首页', 2, '/index.do', 'f4e08b0f-3e0a-49f8-ad31-df67687377b3', NULL);
INSERT INTO `t_p_a_menu` VALUES ('4ccda588-5610-4413-9be5-3091cf962ace', '2019-02-15 22:38:43', b'0', NULL, 0, 'userDelete', '1', '#', '0', '用户删除', 4, '/platform/access/user/userDelete.json', '5fc92edc-f191-446c-89a6-26033a6c88ce', '15c967c6-d857-4c0e-8d28-cfe56cb0ce10');
INSERT INTO `t_p_a_menu` VALUES ('5063f55c-fc3f-4b15-a3fa-26b8b6d13da4', '2019-02-15 17:31:18', b'0', NULL, 0, 'roleCreateSave', '1', '#', '0', '角色新增-保存数据请求', 1, '/platform/access/role/roleCreateSave.json', NULL, '239960c5-4c13-45b6-8e68-60fd6befe15e');
INSERT INTO `t_p_a_menu` VALUES ('5170cf7b-0310-4879-b2ac-332f45f2a588', '2019-02-15 16:13:50', b'0', '2019-02-15 16:46:28', 3, 'dictEditUpdate', '1', '#', '0', '数据字典修改-保存数据请求', 1, '/platform/access/dict/dictEditUpdate.json', NULL, 'aa82f279-5071-4732-82fd-f3d7fc1c00a4');
INSERT INTO `t_p_a_menu` VALUES ('522199a8-5325-4ddc-be7a-8b23e3a19a6b', '2019-02-15 16:17:38', b'0', '2019-02-15 16:19:40', 1, 'iconList', '1', 'com.iss.platform.access.icon.controller.IconControlller.iconList', '0', '图标管理', 2, '/platform/access/icon/iconList.do', '4886afbb-aae5-4757-bfb9-1136812b7d6e', '5e2d6ab2-1db6-4291-98bf-ceb28c3aa435');
INSERT INTO `t_p_a_menu` VALUES ('52513434-2d52-4c7f-b152-1b63fbbca288', '2019-02-15 15:07:32', b'0', '2019-02-15 15:50:06', 3, 'platform-manage', '1', 'platform.manage', '0', '平台管理', 1, '#', '913ac920-7521-4246-a35e-cb0ac0edf841', NULL);
INSERT INTO `t_p_a_menu` VALUES ('5cf7bdab-6003-42ab-bc46-bf3ab403213c', '2019-02-15 16:41:53', b'0', NULL, 0, 'iconDelete', '1', '#', '0', '图标删除', 2, '/platform/access/icon/iconDelete.json', '5fc92edc-f191-446c-89a6-26033a6c88ce', '522199a8-5325-4ddc-be7a-8b23e3a19a6b');
INSERT INTO `t_p_a_menu` VALUES ('5e2d6ab2-1db6-4291-98bf-ceb28c3aa435', '2019-02-15 15:49:51', b'0', '2019-02-15 15:51:09', 1, 'access-manage', '1', 'access.manage', '0', '权限管理', 1, '#', '4557ada1-1da0-4af1-924e-c2d52aa0d187', '52513434-2d52-4c7f-b152-1b63fbbca288');
INSERT INTO `t_p_a_menu` VALUES ('65405e34-5397-43f3-b1e3-170d46d9c63b', '2019-02-15 22:39:42', b'0', NULL, 0, 'userStatusUpdate', '1', '#', '0', '用户列表-更新用户状态', 2, '/platform/access/user/userStatusUpdate.json', NULL, 'dacc0034-711c-43fd-9de6-2769590c265b');
INSERT INTO `t_p_a_menu` VALUES ('663865af-3d19-46ba-b807-73729a33af61', '2019-02-15 17:32:32', b'0', NULL, 0, 'roleCreateJson', '1', '#', '0', '角色新增-获取角色信息', 2, '/platform/access/role/roleCreate.json', NULL, '239960c5-4c13-45b6-8e68-60fd6befe15e');
INSERT INTO `t_p_a_menu` VALUES ('69b4fb32-c59c-4b51-b994-7cf9e744ee9a', '2019-02-15 15:56:53', b'0', '2019-02-15 17:13:42', 3, 'dictCreate', '1', '#', '0', '数据字典新增', 2, '/platform/access/dict/dictCreate.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', 'c3a2c0c2-5a93-42cf-a0f5-71f564f0b62e');
INSERT INTO `t_p_a_menu` VALUES ('6af7ef28-0214-4c38-8645-725abd62387b', '2019-02-15 17:44:05', b'0', NULL, 0, 'roleEdit', '1', '#', '0', '角色修改', 3, '/platform/access/role/roleEdit.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', '23fea1b7-d45b-42ca-a06c-f23d2a63480d');
INSERT INTO `t_p_a_menu` VALUES ('7144e2c0-1164-48c5-b624-ede9a866fc91', '2019-02-17 13:10:02', b'0', NULL, 0, 'base-manage', '1', 'base.manage', '0', '新闻基础设置', 1, '#', '3cbe41b3-3a13-4814-892a-9626148ef2d4', '0ce83191-c8e2-4385-ad41-2a52c5b7da3f');
INSERT INTO `t_p_a_menu` VALUES ('76fb982e-7035-4f09-8cfe-52ec6af6a365', '2019-02-15 23:54:00', b'0', NULL, 0, 'autoTaskEdit', '1', '#', '0', '调度任务修改', 3, '/platform/system/autotask/autoTaskEdit.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', 'e99d1f9b-e6a0-4dd8-ab45-73d72cd01cae');
INSERT INTO `t_p_a_menu` VALUES ('786c3f49-9747-4422-9440-ca6d46c4fbf6', '2019-02-15 16:26:50', b'0', '2019-02-15 16:26:59', 1, 'iconCreate', '1', '#', '0', '图标新增', 1, '/platform/access/icon/create.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', '522199a8-5325-4ddc-be7a-8b23e3a19a6b');
INSERT INTO `t_p_a_menu` VALUES ('7fd8f487-ccec-4e01-aef1-6d2e9cedd9f2', '2019-02-15 23:10:32', b'0', '2019-02-15 23:26:45', 1, 'userRoleSave', '1', '#', '0', '添加用户权限', 5, '/platform/access/user/userRoleSave.json', NULL, 'b072a927-92bb-40fa-bf19-0579c9453aa2');
INSERT INTO `t_p_a_menu` VALUES ('83606f32-9af4-4600-a2a3-03e3bf9c5b7b', '2019-02-16 00:03:50', b'0', '2019-02-16 00:04:31', 1, 'jobLogList', '1', '#', '0', '调度任务日志列表', 1, '#', '5fc92edc-f191-446c-89a6-26033a6c88ce', '1fd4795f-22fc-4d88-9f99-52a2788c3d75');
INSERT INTO `t_p_a_menu` VALUES ('8aaadf85-c90f-436d-892b-f7a6f71ad68e', '2019-02-15 17:09:59', b'0', NULL, 0, 'menuEdit', '1', '#', '0', '菜单修改', 3, '/platform/access/menu/menuEdit.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', '126cc624-cc97-4e20-88f4-5347881fd4f2');
INSERT INTO `t_p_a_menu` VALUES ('8d953a97-71ce-4f3e-82cb-cf37a40013bc', '2019-02-15 17:04:48', b'0', NULL, 0, 'menuCreate', '1', '#', '0', '菜单新增', 1, '/platform/access/menu/menuCreate.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', '126cc624-cc97-4e20-88f4-5347881fd4f2');
INSERT INTO `t_p_a_menu` VALUES ('8ef3c5ac-e0d5-4bd2-b3b3-d4d4852aeeea', '2019-02-17 13:19:22', b'0', '2019-02-17 13:20:32', 1, 'blog-classification-manage', '1', 'blog.classification.manage', '0', '博客基础设置', 1, '#', '3cbe41b3-3a13-4814-892a-9626148ef2d4', 'f91f98b4-dc48-4676-9dbb-47c0156f4484');
INSERT INTO `t_p_a_menu` VALUES ('90d55887-6e78-4423-ab2e-8211958b8126', '2019-02-15 23:58:43', b'0', NULL, 0, 'autoTaskDelete', '1', '#', '0', '调度任务删除', 4, '/platform/access/autoTask/autoTaskDelete.json', NULL, 'e99d1f9b-e6a0-4dd8-ab45-73d72cd01cae');
INSERT INTO `t_p_a_menu` VALUES ('9a86c57f-ded4-48d2-a728-3bad0596605d', '2019-02-15 17:29:14', b'0', NULL, 0, 'roleList', '1', '#', '0', '角色列表-获取分页请求', 1, '/platform/access/role/roleList.json', NULL, '2b581c51-0725-44db-9a66-d966bbc4aedf');
INSERT INTO `t_p_a_menu` VALUES ('9a9cff79-2dd1-430b-84de-7ceb868664fb', '2019-02-15 17:12:20', b'0', '2019-02-15 17:13:01', 2, 'menuStatusUpdate', '1', '#', '0', '菜单列表-更新菜单状态', 3, '/platform/access/menu/menuStatusUpdate.json', NULL, 'e2ddb7b4-d775-4f0c-b297-ffa685527fc2');
INSERT INTO `t_p_a_menu` VALUES ('9c389cd0-bf4a-478b-9366-ca6457244dbe', '2019-02-15 22:24:15', b'0', '2019-02-15 22:40:07', 2, 'userEditJson', '1', '#', '0', '用户修改-获取用户详情', 1, '/platform/access/user/userEditJson.json', NULL, '31ea65d7-8aa2-44d4-a527-69964c274046');
INSERT INTO `t_p_a_menu` VALUES ('9d98a263-ac12-4e09-9b2e-a3c93d32d457', '2019-02-15 17:08:55', b'0', NULL, 0, 'menuDelete', '1', '#', '0', '菜单删除', 2, '/platform/access/menu/menuDelete.json', '5fc92edc-f191-446c-89a6-26033a6c88ce', '126cc624-cc97-4e20-88f4-5347881fd4f2');
INSERT INTO `t_p_a_menu` VALUES ('9e1decae-88c5-4c4a-8340-b020f382c766', '2019-02-15 16:05:24', b'0', '2019-02-15 17:18:28', 4, 'dictList', '1', '#', '0', '数据字典列表-获取分页请求', 1, '/platform/access/dict/dictList.json', NULL, '30442d6a-9ba7-4e5d-957f-76896f26760f');
INSERT INTO `t_p_a_menu` VALUES ('a21841e4-e5c6-4e13-a5a2-aceb45bff0ca', '2019-02-15 17:06:02', b'0', NULL, 0, 'menuCreateJson', '1', '#', '0', '菜单新增-获取菜单信息', 1, '/platform/access/menu/menuCreateJson.json', NULL, '8d953a97-71ce-4f3e-82cb-cf37a40013bc');
INSERT INTO `t_p_a_menu` VALUES ('a5473f9b-6bc5-4daa-b5ce-c05e5f9c9fcf', '2019-02-15 16:42:32', b'0', NULL, 0, 'iconEdit', '1', '#', '0', '图标修改', 3, '/platform/access/icon/iconEdit.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', '522199a8-5325-4ddc-be7a-8b23e3a19a6b');
INSERT INTO `t_p_a_menu` VALUES ('a80c33e8-e34a-4abd-8d1a-0ea71714dff4', '2019-02-15 23:54:56', b'0', '2019-02-15 23:55:21', 1, 'autoTaskEditJson', '1', '#', '0', '调度任务修改-获取数据详情', 1, '/platform/access/autoTask/autoTaskEditJson.json', NULL, '76fb982e-7035-4f09-8cfe-52ec6af6a365');
INSERT INTO `t_p_a_menu` VALUES ('aa82f279-5071-4732-82fd-f3d7fc1c00a4', '2019-02-15 16:12:48', b'0', NULL, 0, 'dictEdit', '1', '#', '0', '数据字典修改', 4, '/platform/access/dict/dictEdit.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', 'c3a2c0c2-5a93-42cf-a0f5-71f564f0b62e');
INSERT INTO `t_p_a_menu` VALUES ('b072a927-92bb-40fa-bf19-0579c9453aa2', '2019-02-15 23:24:08', b'0', '2019-02-15 23:25:01', 2, 'userRoleList', '1', '#', '0', '用户列表-获取用户权限', 5, '/platform/access/user/userRoleList.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', 'dacc0034-711c-43fd-9de6-2769590c265b');
INSERT INTO `t_p_a_menu` VALUES ('bf3219c5-038a-4203-9cea-68fd655e1e93', '2019-02-15 22:19:06', b'0', '2019-02-15 22:20:43', 2, 'userCreateSave', '1', '#', '0', '用户新增-保存用户请求', 1, '/platform/access/user/autoTaskCreateSave.json', NULL, 'f6dc873f-354e-46cd-8ad7-53b249cea2ca');
INSERT INTO `t_p_a_menu` VALUES ('c00a1e06-bd2f-4af1-8897-3fea66e5a3ad', '2019-02-15 23:57:36', b'0', NULL, 0, 'excuteTask', '1', '#', '0', '调度任务列表-执行调度任务', 2, '/platform/access/autoTask/excuteTask.json', NULL, '25308fdf-8e92-42ac-ab93-577f2e516946');
INSERT INTO `t_p_a_menu` VALUES ('c3a2c0c2-5a93-42cf-a0f5-71f564f0b62e', '2019-02-15 15:53:02', b'0', '2019-02-15 15:53:11', 1, 'dictList', '1', 'com.iss.platform.access.dict.controller.DictController.dictList', '0', '数据字典', 1, '/platform/access/dict/dictList.do', '4886afbb-aae5-4757-bfb9-1136812b7d6e', '5e2d6ab2-1db6-4291-98bf-ceb28c3aa435');
INSERT INTO `t_p_a_menu` VALUES ('c4525817-5570-43f8-9829-b4ff41ab1dc9', '2019-02-15 23:56:16', b'0', NULL, 0, 'autoTaskEditUpdate', '1', '#', '0', '调度任务修改-保存数据请求', 2, '/platform/access/autoTask/autoTaskEditUpdate.json', NULL, '76fb982e-7035-4f09-8cfe-52ec6af6a365');
INSERT INTO `t_p_a_menu` VALUES ('c609d76b-12a4-4d54-a44d-e3bf3bd17c53', '2019-02-19 09:21:23', b'0', '2019-02-19 09:22:00', 1, 'dataBaseList', '1', 'dataBaseList', '0', '数据库管理', 3, '/platform/system/dataBase/dataBaseList.do', '4886afbb-aae5-4757-bfb9-1136812b7d6e', '46d756e9-2b99-4354-873a-3db6c9214777');
INSERT INTO `t_p_a_menu` VALUES ('c66337c7-2278-49f2-96a3-f6e80f660ae5', '2019-02-15 16:38:57', b'0', '2019-02-15 16:45:22', 2, 'iconCreateSave', '1', '#', '0', '图标新增-图标保存请求', 1, '/platform/access/icon/iconCreateSave.json', NULL, '786c3f49-9747-4422-9440-ca6d46c4fbf6');
INSERT INTO `t_p_a_menu` VALUES ('c708184d-92d6-4bcd-8034-3d6ac2ada0df', '2019-02-15 23:40:09', b'0', '2019-02-15 23:42:09', 2, 'autoTaskList', '1', '#', '0', '调度任务列表-任务分页请求', 1, '/platform/access/autoTask/autoTaskList.json', NULL, '25308fdf-8e92-42ac-ab93-577f2e516946');
INSERT INTO `t_p_a_menu` VALUES ('c7f95e33-2ce8-43b3-a427-c7c2c6f41213', '2019-02-17 13:11:07', b'0', NULL, 0, 'section-manage', '1', 'section.manage', '0', '版块管理', 1, '/content/news/section/sectionList.do', '4886afbb-aae5-4757-bfb9-1136812b7d6e', '7144e2c0-1164-48c5-b624-ede9a866fc91');
INSERT INTO `t_p_a_menu` VALUES ('c92e7b72-6d18-46e2-a97e-c8ba3d468315', '2019-02-15 16:15:06', b'0', '2019-02-15 17:19:07', 1, 'dictStatusUpdate', '1', '#', '0', '数据字典列表-更新数据状态', 2, '/platform/access/dict/dictStatusUpdate.json', NULL, '30442d6a-9ba7-4e5d-957f-76896f26760f');
INSERT INTO `t_p_a_menu` VALUES ('d567443b-bd0a-4cb7-8d37-fbcf195f906d', '2019-02-15 16:11:01', b'0', '2019-02-15 17:14:01', 1, 'dictDelete', '1', '#', '0', '数据字典删除', 3, '/platform/access/dict/dictDelete.json', '5fc92edc-f191-446c-89a6-26033a6c88ce', 'c3a2c0c2-5a93-42cf-a0f5-71f564f0b62e');
INSERT INTO `t_p_a_menu` VALUES ('dacc0034-711c-43fd-9de6-2769590c265b', '2019-02-15 22:18:13', b'0', NULL, 0, 'userList', '1', '#', '0', '用户列表', 2, '#', '5fc92edc-f191-446c-89a6-26033a6c88ce', '15c967c6-d857-4c0e-8d28-cfe56cb0ce10');
INSERT INTO `t_p_a_menu` VALUES ('e2ddb7b4-d775-4f0c-b297-ffa685527fc2', '2019-02-15 16:59:36', b'0', NULL, 0, 'menuList', '1', '#', '0', '菜单列表', 4, '#', '5fc92edc-f191-446c-89a6-26033a6c88ce', '126cc624-cc97-4e20-88f4-5347881fd4f2');
INSERT INTO `t_p_a_menu` VALUES ('e99d1f9b-e6a0-4dd8-ab45-73d72cd01cae', '2019-02-15 23:38:46', b'0', '2019-02-15 23:40:50', 1, 'autoTaskList', '1', '#', '0', '调度任务', 1, '/platform/system/autotask/autoTaskList.do', '4886afbb-aae5-4757-bfb9-1136812b7d6e', '46d756e9-2b99-4354-873a-3db6c9214777');
INSERT INTO `t_p_a_menu` VALUES ('f68cb6fa-bd4f-4f80-aed5-9321c22630b4', '2019-02-16 00:06:04', b'0', '2019-02-16 00:13:00', 3, 'jobLogView', '1', '#', '0', '调度任务日志查看', 2, '/platform/system/jobLog/jobLogView.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', '1fd4795f-22fc-4d88-9f99-52a2788c3d75');
INSERT INTO `t_p_a_menu` VALUES ('f6dc873f-354e-46cd-8ad7-53b249cea2ca', '2019-02-15 22:17:33', b'0', '2019-02-15 22:19:39', 1, 'userCreate', '1', '#', '0', '用户新增', 1, '/platform/access/user/userCreate.do', '5fc92edc-f191-446c-89a6-26033a6c88ce', '15c967c6-d857-4c0e-8d28-cfe56cb0ce10');
INSERT INTO `t_p_a_menu` VALUES ('f91f98b4-dc48-4676-9dbb-47c0156f4484', '2019-02-17 13:07:12', b'0', NULL, 0, 'blog-manage', '1', 'blog.manage', '0', '博客管理', 4, '#', '913ac920-7521-4246-a35e-cb0ac0edf841', NULL);
INSERT INTO `t_p_a_menu` VALUES ('f9d83f1f-7256-4b07-b104-debf775dcc55', '2019-02-15 17:46:45', b'0', NULL, 0, 'roleMenuSave', '1', '#', '0', '角色l列表-保存角色菜单', 2, '/platform/access/role/roleMenuSave.json', NULL, '2b581c51-0725-44db-9a66-d966bbc4aedf');
INSERT INTO `t_p_a_menu` VALUES ('fb616cbb-7dce-4093-8d54-c5181136b0fe', '2019-02-15 17:43:26', b'0', NULL, 0, 'roleDetete', '1', '#', '0', '角色删除', 3, '/platform/access/role/roleDetete.json', NULL, '23fea1b7-d45b-42ca-a06c-f23d2a63480d');
INSERT INTO `t_p_a_menu` VALUES ('fbdbb8bd-2015-403c-81af-08ab313629dc', '2019-02-15 17:10:28', b'0', '2019-02-15 17:10:39', 1, 'menuEditJson', '1', '#', '0', '菜单修改-获取详情', 1, '/platform/access/menu/menuEditJson.json', NULL, '8aaadf85-c90f-436d-892b-f7a6f71ad68e');

SET FOREIGN_KEY_CHECKS = 1;
