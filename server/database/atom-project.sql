/*
 Navicat Premium Data Transfer

 Source Server         : 本机开发
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : atom-project

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 17/12/2021 10:50:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_action`;
CREATE TABLE `sys_action` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `url` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '动作响应地址',
  `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `type` int(2) NOT NULL COMMENT '资源类型，1查询，2新增，3修改，4删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `topic_id` int(3) NOT NULL COMMENT '动作归属的主题ID',
  `topic_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '动作归属的主题名称',
  `grant_type` int(2) NOT NULL DEFAULT '0' COMMENT '授权类型 0:手动 1:默认',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_menu_res` (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100068 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统api动作资源信息，对应菜单按钮';

-- ----------------------------
-- Records of sys_action
-- ----------------------------
BEGIN;
INSERT INTO `sys_action` VALUES (100000, '/system/action/list', '查询-查询动作资源列表', 1, '2021-06-03 15:14:09', 107, '系统资源管理', 1);
INSERT INTO `sys_action` VALUES (100001, '/system/action/topic/list', '查询-查询动作资源主题列表', 1, '2021-06-03 15:14:09', 107, '系统资源管理', 1);
INSERT INTO `sys_action` VALUES (100002, '/system/dept/update', '新增-新增或者编辑组织数据', 2, '2021-06-03 15:14:09', 104, '系统组织管理', 0);
INSERT INTO `sys_action` VALUES (100003, '/system/dept/update', '编辑-新增或者编辑组织数据', 3, '2021-06-03 15:14:09', 104, '系统组织管理', 0);
INSERT INTO `sys_action` VALUES (100004, '/system/dept/delete/{deptId}', '删除-删除当前组织', 4, '2021-06-03 15:14:09', 104, '系统组织管理', 0);
INSERT INTO `sys_action` VALUES (100005, '/system/dept/tree', '查询-按照树结构查询组织机构', 1, '2021-06-03 15:14:09', 104, '系统组织管理', 1);
INSERT INTO `sys_action` VALUES (100006, '/system/file/list', '查询-查询附件列表', 1, '2021-06-03 15:14:09', 100, '系统附件管理', 1);
INSERT INTO `sys_action` VALUES (100007, '/system/file/operate/{operate}', '编辑-单一文件操作，删除|delete，移动|move，复制|copy，重命名|rename', 3, '2021-06-03 15:14:09', 100, '系统附件管理', 0);
INSERT INTO `sys_action` VALUES (100008, '/system/file/upload', '新增-上传文件', 2, '2021-06-03 15:14:09', 100, '系统附件管理', 0);
INSERT INTO `sys_action` VALUES (100009, '/system/file/folder/tree', '查询-查询文件夹目录', 1, '2021-06-03 15:14:09', 100, '系统附件管理', 1);
INSERT INTO `sys_action` VALUES (100010, '/system/file/add/folder', '新增-新增文件夹', 2, '2021-06-03 15:14:09', 100, '系统附件管理', 0);
INSERT INTO `sys_action` VALUES (100011, '/system/file/batch/operate/{operate}', '编辑-批量文件操作，删除|delete，移动|move，复制|copy，重命名|rename', 3, '2021-06-03 15:14:09', 100, '系统附件管理', 0);
INSERT INTO `sys_action` VALUES (100012, '/system/log/list', '查询-获取系统日志数据表', 1, '2021-06-03 15:14:09', 103, '系统日志管理', 1);
INSERT INTO `sys_action` VALUES (100013, '/system/menu/update', '新增-新增或者编辑组织数据', 2, '2021-06-03 15:14:09', 105, '系统菜单管理', 0);
INSERT INTO `sys_action` VALUES (100014, '/system/menu/update', '编辑-新增或者编辑组织数据', 3, '2021-06-03 15:14:09', 105, '系统菜单管理', 0);
INSERT INTO `sys_action` VALUES (100015, '/system/menu/delete/{menuId}', '删除-删除当前菜单', 4, '2021-06-03 15:14:09', 105, '系统菜单管理', 0);
INSERT INTO `sys_action` VALUES (100016, '/system/menu/tree', '查询-查询菜单树结构', 1, '2021-06-03 15:14:09', 105, '系统菜单管理', 1);
INSERT INTO `sys_action` VALUES (100017, '/system/menu/exchange/{move}', '编辑-菜单顺序上移或者下移', 3, '2021-06-03 15:14:09', 105, '系统菜单管理', 0);
INSERT INTO `sys_action` VALUES (100018, '/system/role/{roleId}/permission', '编辑-给角色赋权菜单和资源按钮', 3, '2021-06-03 15:14:09', 106, '系统角色管理', 0);
INSERT INTO `sys_action` VALUES (100019, '/system/role/{roleId}/update/permission', '编辑-给角色赋权菜单和资源按钮', 3, '2021-06-03 15:14:09', 106, '系统角色管理', 0);
INSERT INTO `sys_action` VALUES (100020, '/system/role/update', '新增-新增或者编辑角色信息', 2, '2021-06-03 15:14:09', 106, '系统角色管理', 0);
INSERT INTO `sys_action` VALUES (100021, '/system/role/update', '编辑-新增或者编辑角色信息', 3, '2021-06-03 15:14:09', 106, '系统角色管理', 0);
INSERT INTO `sys_action` VALUES (100022, '/system/role/delete/{roleId}', '删除-删除角色', 4, '2021-06-03 15:14:09', 106, '系统角色管理', 0);
INSERT INTO `sys_action` VALUES (100023, '/system/role/list', '查询-获取系统角色数据表', 1, '2021-06-03 15:14:09', 106, '系统角色管理', 1);
INSERT INTO `sys_action` VALUES (100024, '/system/type/update', '新增-新增或者编辑数据字典类型', 2, '2021-06-03 15:14:09', 102, '系统数据字典管理', 0);
INSERT INTO `sys_action` VALUES (100025, '/system/type/update', '编辑-新增或者编辑数据字典类型', 3, '2021-06-03 15:14:09', 102, '系统数据字典管理', 0);
INSERT INTO `sys_action` VALUES (100026, '/system/type/delete/{typeId}', '删除-删除系统数据字典', 4, '2021-06-03 15:14:09', 102, '系统数据字典管理', 0);
INSERT INTO `sys_action` VALUES (100027, '/system/type/list', '查询-查询数据字典的列表', 1, '2021-06-03 15:14:09', 102, '系统数据字典管理', 1);
INSERT INTO `sys_action` VALUES (100028, '/system/type/code/tree', '查询-查询数据字典树', 1, '2021-06-03 15:14:09', 102, '系统数据字典管理', 1);
INSERT INTO `sys_action` VALUES (100029, '/system/type/code/list', '查询-查询数据字典列表', 1, '2021-06-03 15:14:09', 102, '系统数据字典管理', 1);
INSERT INTO `sys_action` VALUES (100030, '/system/type/{meanId}/code/update', '新增-新增或者编辑数据字典', 2, '2021-06-03 15:14:09', 102, '系统数据字典管理', 0);
INSERT INTO `sys_action` VALUES (100031, '/system/type/{meanId}/code/update', '编辑-新增或者编辑数据字典', 3, '2021-06-03 15:14:09', 102, '系统数据字典管理', 0);
INSERT INTO `sys_action` VALUES (100032, '/system/type/code/delete/{codeId}', '删除-删除数据字典', 4, '2021-06-03 15:14:09', 102, '系统数据字典管理', 0);
INSERT INTO `sys_action` VALUES (100033, '/system/type/exchange', '编辑-字典顺序上移或者下移', 3, '2021-06-03 15:14:09', 102, '系统数据字典管理', 0);
INSERT INTO `sys_action` VALUES (100034, '/system/type/exchange/{move}', '编辑-字典顺序上移或者下移', 3, '2021-06-03 15:14:09', 102, '系统数据字典管理', 0);
INSERT INTO `sys_action` VALUES (100035, '/system/user/update', '新增-新增或者编辑用户信息', 2, '2021-06-03 15:14:09', 108, '系统用户管理', 0);
INSERT INTO `sys_action` VALUES (100036, '/system/user/update', '编辑-新增或者编辑用户信息', 3, '2021-06-03 15:14:09', 108, '系统用户管理', 0);
INSERT INTO `sys_action` VALUES (100037, '/system/user/list', '查询-获取系统用户数据表', 1, '2021-06-03 15:14:09', 108, '系统用户管理', 0);
INSERT INTO `sys_action` VALUES (100038, '/system/user/me', '查询-取用户信息SessionUser', 1, '2021-06-03 15:14:09', 108, '系统用户管理', 1);
INSERT INTO `sys_action` VALUES (100039, '/system/user/update/{userId}/password', '编辑-更新用户密码', 3, '2021-06-03 15:14:09', 108, '系统用户管理', 1);
INSERT INTO `sys_action` VALUES (100040, '/system/user/reset/{userId}/password', '编辑-重置用户密码', 3, '2021-06-03 15:14:09', 108, '系统用户管理', 0);
INSERT INTO `sys_action` VALUES (100041, '/system/user/toggle/valid/{userId}', '编辑-用户禁用/启用', 3, '2021-06-03 15:14:09', 108, '系统用户管理', 0);
INSERT INTO `sys_action` VALUES (100042, '/system/user/{userId}/role', '编辑-查询角色列表', 3, '2021-06-03 15:14:09', 108, '系统用户管理', 0);
INSERT INTO `sys_action` VALUES (100043, '/system/user/update/{userId}/head', '编辑-更新用户头像', 3, '2021-06-03 15:14:09', 108, '系统用户管理', 1);
INSERT INTO `sys_action` VALUES (100044, '/system/logout', '编辑-系统用户登出', 3, '2021-06-03 15:14:09', 101, '系统管理', 1);
INSERT INTO `sys_action` VALUES (100045, '/system/send/{account}/verify/code', '新增-发送短信验证码', 2, '2021-06-03 15:14:09', 101, '系统管理', 1);
INSERT INTO `sys_action` VALUES (100046, '/system/sign/up', '新增-系统注册', 2, '2021-06-03 15:14:09', 101, '系统管理', 1);
INSERT INTO `sys_action` VALUES (100047, '/system/third/state', '查询-第三方登录随机串', 1, '2021-06-03 15:14:09', 101, '系统管理', 1);
INSERT INTO `sys_action` VALUES (100048, '/system/third/sign/in', '编辑-第三方登录回调方法', 3, '2021-06-03 15:14:09', 101, '系统管理', 1);
INSERT INTO `sys_action` VALUES (100049, '/system/captcha', '查询-生成验证码', 1, '2021-06-03 15:14:09', 101, '系统管理', 1);
INSERT INTO `sys_action` VALUES (100050, '/system/judge/captcha', '查询-校验图片验证码', 1, '2021-06-03 15:14:09', 101, '系统管理', 1);
INSERT INTO `sys_action` VALUES (100051, '/system/forget/password', '编辑-忘记密码', 3, '2021-06-03 15:14:09', 101, '系统管理', 1);
INSERT INTO `sys_action` VALUES (100052, '/system/send/{phone}/verify/code', '新增-发送短信验证码', 2, '2021-07-21 16:54:26', 101, '系统管理', 1);
INSERT INTO `sys_action` VALUES (100053, '/system/user/{userId}/update/role', '编辑-用户角色赋权', 3, '2021-07-30 12:29:02', 108, '系统用户管理', 0);
INSERT INTO `sys_action` VALUES (100054, '/system/send/{phone}/verifyCode', '新增-发送短信验证码', 2, '2021-07-31 10:17:03', 101, '系统管理', 1);
INSERT INTO `sys_action` VALUES (100055, '/system/user/update/appConfig', '编辑-更新用户App配置', 3, '2021-08-18 21:45:37', 108, '系统用户管理', 1);
INSERT INTO `sys_action` VALUES (100056, '/system/news/list', '查询-获取系统提醒消息待办列表', 1, '2021-08-24 10:55:42', 109, '系统通知消息待办管理', 0);
INSERT INTO `sys_action` VALUES (100057, '/system/news/read', '编辑-设置多条消息为已读', 3, '2021-08-24 19:26:51', 109, '系统通知消息待办管理', 0);
INSERT INTO `sys_action` VALUES (100058, '/system/news/read/{newsId}', '编辑-设置一条消息为已读', 3, '2021-08-24 19:26:51', 109, '系统通知消息待办管理', 0);
INSERT INTO `sys_action` VALUES (100059, '/system/news/delete/{newsId}', '删除-删除消息', 4, '2021-08-24 21:38:55', 109, '系统通知消息待办管理', 0);
INSERT INTO `sys_action` VALUES (100060, '/system/news/unread/{newsId}', '编辑-设置一条消息为未读', 3, '2021-08-24 21:38:55', 109, '系统通知消息待办管理', 0);
INSERT INTO `sys_action` VALUES (100061, '/system/file/upload/{parentId}', '新增-上传文件到文件夹', 2, '2021-09-03 21:27:29', 100, '系统附件管理', 0);
INSERT INTO `sys_action` VALUES (100062, '/system/file/delete/{fileId}', '删除-删除文件', 4, '2021-09-07 13:45:14', 100, '系统附件管理', 0);
INSERT INTO `sys_action` VALUES (100063, '/system/file/download/{fileId}', '查询-下载文件', 1, '2021-09-07 18:26:27', 100, '系统附件管理', 0);
INSERT INTO `sys_action` VALUES (100064, '/system/form/toggle/valid/{formId}', '编辑-系统自定义表单禁用/启用', 3, '2021-11-22 15:29:14', 110, '系统自定义表单管理', 0);
INSERT INTO `sys_action` VALUES (100065, '/system/form/update', '新增-新增或者编辑用户信息', 2, '2021-11-22 15:29:14', 110, '系统自定义表单管理', 0);
INSERT INTO `sys_action` VALUES (100066, '/system/form/update', '编辑-新增或者编辑用户信息', 3, '2021-11-22 15:29:14', 110, '系统自定义表单管理', 0);
INSERT INTO `sys_action` VALUES (100067, '/system/form/list', '查询-获取系统自定义表单数据表', 1, '2021-11-22 15:29:14', 110, '系统自定义表单管理', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_action_topic
-- ----------------------------
DROP TABLE IF EXISTS `sys_action_topic`;
CREATE TABLE `sys_action_topic` (
  `id` int(3) NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `name` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '响应主题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统动作响应资源对应的主题，一个菜单可以有N个主题';

-- ----------------------------
-- Records of sys_action_topic
-- ----------------------------
BEGIN;
INSERT INTO `sys_action_topic` VALUES (100, '系统附件管理');
INSERT INTO `sys_action_topic` VALUES (101, '系统管理');
INSERT INTO `sys_action_topic` VALUES (102, '系统数据字典管理');
INSERT INTO `sys_action_topic` VALUES (103, '系统日志管理');
INSERT INTO `sys_action_topic` VALUES (104, '系统组织管理');
INSERT INTO `sys_action_topic` VALUES (105, '系统菜单管理');
INSERT INTO `sys_action_topic` VALUES (106, '系统角色管理');
INSERT INTO `sys_action_topic` VALUES (107, '系统资源管理');
INSERT INTO `sys_action_topic` VALUES (108, '系统用户管理');
INSERT INTO `sys_action_topic` VALUES (109, '系统通知消息待办管理');
INSERT INTO `sys_action_topic` VALUES (110, '系统自定义表单管理');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dept_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `dept_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门描述',
  `leader_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门负责人名称',
  `leader_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门负责人电话',
  `dept_parent` int(6) DEFAULT NULL COMMENT '父级部门',
  `if_valid` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效1有效，0失效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1025 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统组织机构';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (1000, '管理平台', '管理平台', 'Atom', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (1001, '门户平台', '门户平台', 'Atom', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (1003, '市应急局', '市应急局', NULL, '18630855886', 1000, 1);
INSERT INTO `sys_dept` VALUES (1004, '区应急局', '区应急局', NULL, NULL, 1000, 1);
INSERT INTO `sys_dept` VALUES (1005, '市委市政府', '市委市政府', NULL, NULL, 1000, 1);
INSERT INTO `sys_dept` VALUES (1006, '办公室', '办公室', NULL, NULL, 1003, 1);
INSERT INTO `sys_dept` VALUES (1007, '指挥中心', '指挥中心', NULL, NULL, 1003, 1);
INSERT INTO `sys_dept` VALUES (1008, '事务中心', '事务中心', NULL, NULL, 1003, 1);
INSERT INTO `sys_dept` VALUES (1009, '处置中心', '处置中心', NULL, NULL, 1003, 1);
INSERT INTO `sys_dept` VALUES (1010, '局网信办', '局网信办', NULL, NULL, 1003, 1);
INSERT INTO `sys_dept` VALUES (1011, '和平区应急局', '和平区应急局', NULL, NULL, 1004, 1);
INSERT INTO `sys_dept` VALUES (1012, '南开区应急局', '南开区应急局', NULL, NULL, 1004, 1);
INSERT INTO `sys_dept` VALUES (1013, '河东区应急局', '河东区应急局', NULL, NULL, 1004, 1);
INSERT INTO `sys_dept` VALUES (1014, '河西区应急局', '河西区应急局', NULL, NULL, 1004, 1);
INSERT INTO `sys_dept` VALUES (1015, '河北区应急局', '河北区应急局', NULL, NULL, 1004, 1);
INSERT INTO `sys_dept` VALUES (1016, '红桥区应急局', '红桥区应急局', NULL, NULL, 1004, 1);
INSERT INTO `sys_dept` VALUES (1017, '市委网信息办', '市委网络与信息办公室', NULL, NULL, 1005, 1);
INSERT INTO `sys_dept` VALUES (1018, '城市管理委员会', '城市管理委员会', NULL, NULL, 1005, 1);
INSERT INTO `sys_dept` VALUES (1019, '交通运输委员会', '交通运输委员会', NULL, NULL, 1005, 1);
INSERT INTO `sys_dept` VALUES (1020, '规划与资源局', '规划与资源局', NULL, NULL, 1005, 1);
INSERT INTO `sys_dept` VALUES (1021, '企业组织', '企业组织', NULL, NULL, 1001, 1);
INSERT INTO `sys_dept` VALUES (1022, '政府组织', '政府组织', NULL, NULL, 1001, 1);
INSERT INTO `sys_dept` VALUES (1023, '个人组织', '个人组织', NULL, NULL, 1001, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `file_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '节点名称',
  `file_type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件类型 ',
  `file_size` bigint(11) DEFAULT NULL COMMENT '节点大小，单位为KB',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '所属父节点id',
  `file_key` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件key',
  `file_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件路径',
  `creator_id` int(6) NOT NULL COMMENT '创建人id',
  `creator_name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `modify_time` datetime NOT NULL COMMENT '创建/修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `INDEX_OF_NAME` (`file_name`) USING BTREE,
  KEY `INDEX_OF_CREATORID` (`creator_id`) USING BTREE,
  KEY `INDEX_OF_FILETYPE` (`file_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='附件管理';

-- ----------------------------
-- Records of sys_file
-- ----------------------------
BEGIN;
INSERT INTO `sys_file` VALUES (1, 'advanceForm', 'folder', NULL, NULL, '/file/advanceForm', '/Users/zr/Downloads/file/advanceForm', 100000, '管理员', '2021-09-11 15:57:10');
INSERT INTO `sys_file` VALUES (2, '报销单1.pdf', '.pdf', 44446, 1, '/file/advanceForm/报销单120210911155709669.pdf', '/Users/zr/Downloads/file/advanceForm/报销单120210911155709669.pdf', 100000, '管理员', '2021-09-11 15:57:10');
INSERT INTO `sys_file` VALUES (3, '报销单2.pdf', '.pdf', 44446, 1, '/file/advanceForm/报销单220210911155709865.pdf', '/Users/zr/Downloads/file/advanceForm/报销单220210911155709865.pdf', 100000, '管理员', '2021-09-11 15:57:10');
INSERT INTO `sys_file` VALUES (4, '报销单3.pdf', '.pdf', 44446, 1, '/file/advanceForm/报销单320210911190351682.pdf', '/Users/zr/Downloads/file/advanceForm/报销单320210911190351682.pdf', 100000, '管理员', '2021-09-11 19:03:52');
INSERT INTO `sys_file` VALUES (5, '报销单1.pdf', '.pdf', 44446, 1, '/file/advanceForm/报销单120210911190805674.pdf', '/Users/zr/Downloads/file/advanceForm/报销单120210911190805674.pdf', 100000, '管理员', '2021-09-11 19:08:06');
COMMIT;

-- ----------------------------
-- Table structure for sys_form
-- ----------------------------
DROP TABLE IF EXISTS `sys_form`;
CREATE TABLE `sys_form` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表单标题',
  `panel` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'mac' COMMENT '画布类型 mac | pad | phone',
  `width` int(3) NOT NULL COMMENT '表单宽度',
  `form_config` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '表单配置',
  `widgets` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组件配置内容',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `creator_id` int(6) NOT NULL COMMENT '修改人ID',
  `creator_name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `if_valid` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效1有效，0失效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统自定义表单';

-- ----------------------------
-- Records of sys_form
-- ----------------------------
BEGIN;
INSERT INTO `sys_form` VALUES (1, '复杂表单信息采集', 'mac', 100, '{\"title\":\"复杂表单信息采集\",\"width\":100,\"dialogSize\":720,\"labelAlign\":\"right\",\"labelColType\":\"style\",\"labelColSize\":\"80\",\"size\":\"default\",\"colon\":true,\"labelCol\":{\"style\":{\"width\":\"80px\"}}}', '[{\"icon\":\"atom-form-title\",\"title\":\"标题\",\"type\":\"title\",\"group\":\"layout\",\"key\":\"title_06e58e3d\",\"options\":{\"type\":\"title\",\"label\":\"标题\",\"name\":\"title_06e58e3d\",\"key\":\"title_06e58e3d\",\"width\":100,\"title\":\"基础信息\",\"style\":{\"width\":\"100%\"}},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"title_06e58e3d\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"title\",\"type\":\"text\",\"label\":\"自定义标题\"}],\"widgetConfig\":{\"key\":\"title_06e58e3d\",\"width\":100,\"title\":\"基础信息\"}},{\"icon\":\"atom-layout-table\",\"title\":\"表格布局\",\"type\":\"table\",\"items\":[{\"key\":\"row_0\",\"columns\":[{\"key\":\"column_0_0\",\"widgets\":[{\"icon\":\"atom-form-input\",\"title\":\"单行文本\",\"type\":\"input\",\"group\":\"basic\",\"key\":\"input_6a08e489\",\"options\":{\"type\":\"input\",\"label\":\"姓名\",\"placeholder\":\"请输入姓名\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"input_6a08e489\",\"key\":\"input_6a08e489\",\"width\":100,\"prefix\":\"\",\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"input_6a08e489\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"text\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\"},{\"name\":\"prefix\",\"type\":\"iconPicker\",\"label\":\"前缀图标\"},{\"name\":\"suffix\",\"type\":\"iconPicker\",\"label\":\"后缀图标\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"input_6a08e489\",\"label\":\"姓名\",\"width\":100,\"prefix\":\"\",\"disabled\":false,\"labelVisible\":true}}]},{\"key\":\"column_0_1\",\"widgets\":[{\"icon\":\"atom-form-input\",\"title\":\"单行文本\",\"type\":\"input\",\"group\":\"basic\",\"key\":\"input_92f6cda1\",\"options\":{\"type\":\"input\",\"label\":\"单位\",\"placeholder\":\"请输入单位\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"input_92f6cda1\",\"key\":\"input_92f6cda1\",\"width\":100,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"input_92f6cda1\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"text\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\"},{\"name\":\"prefix\",\"type\":\"iconPicker\",\"label\":\"前缀图标\"},{\"name\":\"suffix\",\"type\":\"iconPicker\",\"label\":\"后缀图标\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"input_92f6cda1\",\"label\":\"单位\",\"width\":100,\"disabled\":false,\"labelVisible\":true}}]}]},{\"key\":\"row_1\",\"columns\":[{\"key\":\"column_1_0\",\"widgets\":[{\"icon\":\"atom-form-input\",\"title\":\"单行文本\",\"type\":\"input\",\"group\":\"basic\",\"key\":\"input_1012a5d1\",\"options\":{\"type\":\"input\",\"label\":\"邮编\",\"placeholder\":\"请输入邮编\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"input_1012a5d1\",\"key\":\"input_1012a5d1\",\"width\":100,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"input_1012a5d1\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"text\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\"},{\"name\":\"prefix\",\"type\":\"iconPicker\",\"label\":\"前缀图标\"},{\"name\":\"suffix\",\"type\":\"iconPicker\",\"label\":\"后缀图标\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"input_1012a5d1\",\"label\":\"邮编\",\"width\":100,\"disabled\":false,\"labelVisible\":true}}]},{\"key\":\"column_1_1\",\"widgets\":[{\"icon\":\"atom-form-input\",\"title\":\"单行文本\",\"type\":\"input\",\"group\":\"basic\",\"key\":\"input_ad32ea5f\",\"options\":{\"type\":\"input\",\"label\":\"家庭住址\",\"placeholder\":\"请输入家庭住址\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"input_ad32ea5f\",\"key\":\"input_ad32ea5f\",\"width\":100,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"input_ad32ea5f\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"text\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\"},{\"name\":\"prefix\",\"type\":\"iconPicker\",\"label\":\"前缀图标\"},{\"name\":\"suffix\",\"type\":\"iconPicker\",\"label\":\"后缀图标\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"input_ad32ea5f\",\"label\":\"家庭住址\",\"width\":100,\"disabled\":false,\"labelVisible\":true}}]}]}],\"group\":\"layout\",\"key\":\"table_287baefa\",\"options\":{\"type\":\"table\",\"label\":\"表格布局\",\"name\":\"table_287baefa\",\"style\":{\"width\":\"100%\",\"border\":\"0px hidden \"},\"key\":\"table_287baefa\",\"width\":100,\"row\":2,\"col\":2,\"borderWidth\":0,\"borderStyle\":\"hidden\",\"borderColor\":\"\"},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"table_287baefa\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"rowCol\",\"type\":\"inputGroup\",\"label\":\"表格行列\",\"group\":[{\"name\":\"row\",\"label\":\"行\",\"default\":2,\"type\":\"number\",\"min\":2,\"max\":6,\"style\":{\"width\":\"50%\"}},{\"name\":\"col\",\"label\":\"列\",\"default\":2,\"type\":\"number\",\"min\":2,\"max\":6,\"style\":{\"width\":\"50%\"}}]},{\"name\":\"borderWidth\",\"type\":\"number\",\"label\":\"边框宽度\",\"default\":1},{\"name\":\"borderStyle\",\"type\":\"select\",\"label\":\"边框样式\",\"default\":\"solid\",\"options\":[{\"value\":\"solid\",\"title\":\"实线-solid\"},{\"value\":\"dashed\",\"title\":\"虚线-dashed\"},{\"value\":\"dotted\",\"title\":\"圆点-dotted\"},{\"value\":\"double\",\"title\":\"双线-double\"},{\"value\":\"groove\",\"title\":\"沟槽-groove\"},{\"value\":\"ridge\",\"title\":\"山脊-ridge\"},{\"value\":\"hidden\",\"title\":\"隐藏-hidden\"}]},{\"name\":\"borderColor\",\"type\":\"colorPicker\",\"label\":\"边框颜色\",\"default\":\"\"}],\"widgetConfig\":{\"key\":\"table_287baefa\",\"width\":100,\"row\":2,\"col\":2,\"borderWidth\":0,\"borderStyle\":\"hidden\",\"borderColor\":\"\"}},{\"icon\":\"atom-form-title\",\"title\":\"标题\",\"type\":\"title\",\"group\":\"layout\",\"key\":\"title_d9c9b2c9\",\"options\":{\"type\":\"title\",\"label\":\"标题\",\"name\":\"title_d9c9b2c9\",\"style\":{\"width\":\"100%\"},\"key\":\"title_d9c9b2c9\",\"width\":100,\"title\":\"职业信息\"},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"title_d9c9b2c9\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"title\",\"type\":\"text\",\"label\":\"自定义标题\"}],\"widgetConfig\":{\"key\":\"title_d9c9b2c9\",\"width\":100,\"title\":\"职业信息\"}},{\"icon\":\"atom-layout-desc\",\"title\":\"提示文本\",\"type\":\"alert\",\"group\":\"layout\",\"key\":\"alert_08411b09\",\"options\":{\"type\":\"alert\",\"label\":\"提示文本\",\"name\":\"alert_08411b09\",\"style\":{\"width\":\"100%\"},\"key\":\"alert_08411b09\",\"width\":100,\"message\":\"以下信息请务必正确填写\",\"alertType\":\"warning\",\"banner\":true,\"closable\":false,\"showIcon\":true,\"placeholder\":\"请输入提示文本\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"}},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"alert_08411b09\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"message\",\"type\":\"textarea\",\"label\":\"内容\",\"default\":\"提示内容\"},{\"name\":\"alertType\",\"type\":\"radio\",\"label\":\"提示样式\",\"default\":\"warning\",\"mode\":\"button\",\"buttonStyle\":\"solid\",\"options\":[{\"value\":\"success\",\"title\":\"成功\"},{\"value\":\"info\",\"title\":\"消息\"},{\"value\":\"warning\",\"title\":\"提醒\"},{\"value\":\"error\",\"title\":\"失败\"}]},{\"name\":\"banner\",\"type\":\"switch\",\"label\":\"是否顶部公告\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"closable\",\"type\":\"switch\",\"label\":\"是否可关闭\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"showIcon\",\"type\":\"switch\",\"label\":\"是否显示图标\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true}],\"widgetConfig\":{\"key\":\"alert_08411b09\",\"width\":100,\"message\":\"以下信息请务必正确填写\",\"alertType\":\"warning\",\"banner\":true,\"closable\":false,\"showIcon\":true}},{\"icon\":\"atom-layout-grid\",\"title\":\"栅格布局\",\"type\":\"grid\",\"items\":[{\"key\":\"column_0\",\"order\":0,\"span\":12,\"widgets\":[{\"icon\":\"atom-form-number\",\"title\":\"计数器\",\"type\":\"number\",\"group\":\"basic\",\"key\":\"number_44be32ba\",\"options\":{\"type\":\"number\",\"label\":\"年龄\",\"placeholder\":\"请输入年龄\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"number_44be32ba\",\"key\":\"number_44be32ba\",\"width\":100,\"min\":20,\"max\":60,\"step\":1,\"formatter\":\"(value) => `${value}`.replace(/\\\\B(?=(\\\\d{3})+(?!\\\\d))/g, \',\')\",\"parser\":\"(value) => value.replace(/\\\\$\\\\s?|(,*)/g, \'\')\",\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"number_44be32ba\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"计数器\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"number\",\"label\":\"默认值\"},{\"name\":\"range\",\"type\":\"inputGroup\",\"label\":\"区间步长\",\"group\":[{\"name\":\"min\",\"type\":\"number\",\"label\":\"最小值\",\"default\":0,\"style\":{\"width\":\"33%\"}},{\"name\":\"max\",\"type\":\"number\",\"label\":\"最大值\",\"default\":100,\"style\":{\"width\":\"33%\"}},{\"name\":\"step\",\"type\":\"number\",\"label\":\"步长\",\"default\":1,\"style\":{\"width\":\"34%\"}}]},{\"name\":\"formatter\",\"type\":\"text\",\"label\":\"展示格式\",\"default\":\"(value) => `${value}`.replace(/\\\\B(?=(\\\\d{3})+(?!\\\\d))/g, \',\')\"},{\"name\":\"parser\",\"type\":\"text\",\"label\":\"展示格式\",\"default\":\"(value) => value.replace(/\\\\$\\\\s?|(,*)/g, \'\')\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"number_44be32ba\",\"label\":\"年龄\",\"width\":100,\"min\":20,\"max\":60,\"step\":1,\"formatter\":\"(value) => `${value}`.replace(/\\\\B(?=(\\\\d{3})+(?!\\\\d))/g, \',\')\",\"parser\":\"(value) => value.replace(/\\\\$\\\\s?|(,*)/g, \'\')\",\"disabled\":false,\"labelVisible\":true}}]},{\"key\":\"column_1\",\"order\":1,\"span\":12,\"widgets\":[{\"icon\":\"atom-form-number\",\"title\":\"计数器\",\"type\":\"number\",\"group\":\"basic\",\"key\":\"number_7150a9de\",\"options\":{\"type\":\"number\",\"label\":\"工作年限\",\"placeholder\":\"请输入工作年限\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"number_7150a9de\",\"key\":\"number_7150a9de\",\"width\":100,\"min\":1,\"max\":40,\"step\":1,\"formatter\":\"(value) => `${value}`.replace(/\\\\B(?=(\\\\d{3})+(?!\\\\d))/g, \',\')\",\"parser\":\"(value) => value.replace(/\\\\$\\\\s?|(,*)/g, \'\')\",\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"number_7150a9de\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"计数器\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"number\",\"label\":\"默认值\"},{\"name\":\"range\",\"type\":\"inputGroup\",\"label\":\"区间步长\",\"group\":[{\"name\":\"min\",\"type\":\"number\",\"label\":\"最小值\",\"default\":0,\"style\":{\"width\":\"33%\"}},{\"name\":\"max\",\"type\":\"number\",\"label\":\"最大值\",\"default\":100,\"style\":{\"width\":\"33%\"}},{\"name\":\"step\",\"type\":\"number\",\"label\":\"步长\",\"default\":1,\"style\":{\"width\":\"34%\"}}]},{\"name\":\"formatter\",\"type\":\"text\",\"label\":\"展示格式\",\"default\":\"(value) => `${value}`.replace(/\\\\B(?=(\\\\d{3})+(?!\\\\d))/g, \',\')\"},{\"name\":\"parser\",\"type\":\"text\",\"label\":\"展示格式\",\"default\":\"(value) => value.replace(/\\\\$\\\\s?|(,*)/g, \'\')\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"number_7150a9de\",\"label\":\"工作年限\",\"width\":100,\"min\":1,\"max\":40,\"step\":1,\"formatter\":\"(value) => `${value}`.replace(/\\\\B(?=(\\\\d{3})+(?!\\\\d))/g, \',\')\",\"parser\":\"(value) => value.replace(/\\\\$\\\\s?|(,*)/g, \'\')\",\"disabled\":false,\"labelVisible\":true}}]}],\"group\":\"layout\",\"key\":\"grid_d3c6c34e\",\"options\":{\"type\":\"grid\",\"label\":\"栅格布局\",\"name\":\"grid_d3c6c34e\",\"style\":{\"width\":\"100%\"},\"key\":\"grid_d3c6c34e\",\"width\":100,\"col\":2,\"gutter\":8,\"align\":\"top\",\"justify\":\"start\",\"wrap\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"grid_d3c6c34e\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"col\",\"type\":\"select\",\"label\":\"列数\",\"default\":2,\"options\":[{\"value\":2,\"title\":\"2列\"},{\"value\":3,\"title\":\"3列\"},{\"value\":4,\"title\":\"4列\"},{\"value\":6,\"title\":\"6列\"},{\"value\":8,\"title\":\"8列\"},{\"value\":12,\"title\":\"12列\"}]},{\"name\":\"gutter\",\"type\":\"number\",\"label\":\"栅格间隔\",\"default\":8},{\"name\":\"align\",\"type\":\"radio\",\"label\":\"水平布局\",\"default\":\"top\",\"mode\":\"button\",\"buttonStyle\":\"solid\",\"options\":[{\"value\":\"top\",\"title\":\"顶部对齐\"},{\"value\":\"middle\",\"title\":\"垂直居中\"},{\"value\":\"bottom\",\"title\":\"底部对齐\"}]},{\"name\":\"justify\",\"type\":\"select\",\"label\":\"垂直布局\",\"default\":\"start\",\"options\":[{\"value\":\"start\",\"title\":\"左对齐\"},{\"value\":\"end\",\"title\":\"右对齐\"},{\"value\":\"center\",\"title\":\"水平居中\"},{\"value\":\"space-around\",\"title\":\"两端间隔\"},{\"value\":\"space-between\",\"title\":\"两端对齐\"}]},{\"name\":\"wrap\",\"type\":\"switch\",\"label\":\"是否换行\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true}],\"widgetConfig\":{\"key\":\"grid_d3c6c34e\",\"width\":100,\"col\":2,\"gutter\":8,\"align\":\"top\",\"justify\":\"start\",\"wrap\":true}},{\"icon\":\"atom-form-switch\",\"title\":\"开关\",\"type\":\"switch\",\"group\":\"basic\",\"key\":\"switch_55eaaec0\",\"options\":{\"type\":\"switch\",\"label\":\"是否在职\",\"placeholder\":\"请选择是否在职\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"switch_55eaaec0\",\"key\":\"switch_55eaaec0\",\"width\":100,\"default\":1,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"switch_55eaaec0\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"开关\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"switch\",\"label\":\"默认值\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"switch_55eaaec0\",\"label\":\"是否在职\",\"width\":100,\"default\":1,\"disabled\":false,\"labelVisible\":true}},{\"icon\":\"atom-layout-step\",\"title\":\"分步布局\",\"type\":\"steps\",\"items\":[{\"key\":\"工作1\",\"title\":\"工作1\",\"widgets\":[{\"icon\":\"atom-form-input\",\"title\":\"单行文本\",\"type\":\"input\",\"group\":\"basic\",\"key\":\"input_fd40c8f1\",\"options\":{\"type\":\"input\",\"label\":\"单位\",\"placeholder\":\"请输入单位\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"input_fd40c8f1\",\"key\":\"input_fd40c8f1\",\"width\":100,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"input_fd40c8f1\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"text\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"input_fd40c8f1\",\"label\":\"单位\",\"width\":100,\"disabled\":false,\"labelVisible\":true}},{\"icon\":\"atom-form-textarea\",\"title\":\"多行文本\",\"type\":\"textarea\",\"group\":\"basic\",\"key\":\"textarea_2d8e23b4\",\"options\":{\"type\":\"textarea\",\"label\":\"工作简介\",\"placeholder\":\"请输入工作简介\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"textarea_2d8e23b4\",\"key\":\"textarea_2d8e23b4\",\"width\":100,\"rows\":4,\"autoSize\":false,\"showCount\":true,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"textarea_2d8e23b4\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"多行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"textarea\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\",\"step\":10},{\"name\":\"rows\",\"type\":\"number\",\"label\":\"行数\",\"default\":4},{\"name\":\"autoSize\",\"type\":\"switch\",\"label\":\"自适应内容高度\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"showCount\",\"type\":\"switch\",\"label\":\"是否展示字数\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"textarea_2d8e23b4\",\"label\":\"工作简介\",\"width\":100,\"rows\":4,\"autoSize\":false,\"showCount\":true,\"disabled\":false,\"labelVisible\":true}}]},{\"key\":\"工作2\",\"title\":\"工作2\",\"widgets\":[{\"icon\":\"atom-form-input\",\"title\":\"单行文本\",\"type\":\"input\",\"group\":\"basic\",\"key\":\"input_bb2bdcd7\",\"options\":{\"type\":\"input\",\"label\":\"单位\",\"placeholder\":\"请输入单位\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"input_bb2bdcd7\",\"key\":\"input_bb2bdcd7\",\"width\":100,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"input_bb2bdcd7\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"text\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"input_bb2bdcd7\",\"label\":\"单位\",\"width\":100,\"disabled\":false,\"labelVisible\":true}},{\"icon\":\"atom-form-textarea\",\"title\":\"多行文本\",\"type\":\"textarea\",\"group\":\"basic\",\"key\":\"textarea_7dfd23d6\",\"options\":{\"type\":\"textarea\",\"label\":\"工作简介\",\"placeholder\":\"请输入工作简介\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"textarea_7dfd23d6\",\"key\":\"textarea_7dfd23d6\",\"width\":100,\"rows\":4,\"autoSize\":false,\"showCount\":true,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"textarea_7dfd23d6\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"多行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"textarea\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\",\"step\":10},{\"name\":\"rows\",\"type\":\"number\",\"label\":\"行数\",\"default\":4},{\"name\":\"autoSize\",\"type\":\"switch\",\"label\":\"自适应内容高度\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"showCount\",\"type\":\"switch\",\"label\":\"是否展示字数\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"textarea_7dfd23d6\",\"label\":\"工作简介\",\"width\":100,\"rows\":4,\"autoSize\":false,\"showCount\":true,\"disabled\":false,\"labelVisible\":true}}]},{\"key\":\"工作3\",\"title\":\"工作3\",\"widgets\":[{\"icon\":\"atom-form-input\",\"title\":\"单行文本\",\"type\":\"input\",\"group\":\"basic\",\"key\":\"input_73cead6a\",\"options\":{\"type\":\"input\",\"label\":\"单位\",\"placeholder\":\"请输入单位\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"input_73cead6a\",\"key\":\"input_73cead6a\",\"width\":100,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"input_73cead6a\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"text\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"input_73cead6a\",\"label\":\"单位\",\"width\":100,\"disabled\":false,\"labelVisible\":true}},{\"icon\":\"atom-form-textarea\",\"title\":\"多行文本\",\"type\":\"textarea\",\"group\":\"basic\",\"key\":\"textarea_59ed14af\",\"options\":{\"type\":\"textarea\",\"label\":\"工作简介\",\"placeholder\":\"请输入工作简介\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"textarea_59ed14af\",\"key\":\"textarea_59ed14af\",\"width\":100,\"rows\":4,\"autoSize\":false,\"showCount\":true,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"textarea_59ed14af\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"多行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"textarea\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\",\"step\":10},{\"name\":\"rows\",\"type\":\"number\",\"label\":\"行数\",\"default\":4},{\"name\":\"autoSize\",\"type\":\"switch\",\"label\":\"自适应内容高度\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"showCount\",\"type\":\"switch\",\"label\":\"是否展示字数\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"textarea_59ed14af\",\"label\":\"工作简介\",\"width\":100,\"rows\":4,\"autoSize\":false,\"showCount\":true,\"disabled\":false,\"labelVisible\":true}}]}],\"group\":\"layout\",\"key\":\"steps_0aa7a4e6\",\"options\":{\"type\":\"steps\",\"label\":\"分步布局\",\"name\":\"steps_0aa7a4e6\",\"style\":{\"width\":\"100%\"},\"key\":\"steps_0aa7a4e6\",\"width\":100,\"stepType\":\"navigation\",\"direction\":\"horizontal\",\"labelPlacement\":\"horizontal\",\"progressDot\":false,\"items\":{\"labelShow\":false,\"default\":[\"工作3\"],\"options\":[{\"value\":\"工作1\",\"key\":\"Step1\",\"title\":\"工作1\",\"label\":\"工作1\"},{\"value\":\"工作2\",\"key\":\"Step2\",\"title\":\"工作2\",\"label\":\"工作2\"},{\"value\":\"工作3\",\"key\":\"Step3\",\"title\":\"工作3\",\"label\":\"工作3\"}]}},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"steps_0aa7a4e6\",\"disabled\":false},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100,\"disabled\":false},{\"name\":\"stepType\",\"type\":\"radio\",\"label\":\"步骤条类型\",\"default\":\"navigation\",\"mode\":\"button\",\"buttonStyle\":\"solid\",\"options\":[{\"value\":\"default\",\"title\":\"默认\"},{\"value\":\"navigation\",\"title\":\"导航条\"}],\"disabled\":false},{\"name\":\"direction\",\"type\":\"radio\",\"label\":\"步骤条方向\",\"default\":\"horizontal\",\"mode\":\"button\",\"buttonStyle\":\"solid\",\"options\":[{\"value\":\"vertical\",\"title\":\"垂直对齐\"},{\"value\":\"horizontal\",\"title\":\"水平对齐\"}],\"disabled\":true},{\"name\":\"labelPlacement\",\"type\":\"radio\",\"label\":\"标签位置\",\"default\":\"horizontal\",\"mode\":\"button\",\"buttonStyle\":\"solid\",\"options\":[{\"value\":\"vertical\",\"title\":\"垂直对齐\"},{\"value\":\"horizontal\",\"title\":\"水平对齐\"}],\"disabled\":true},{\"name\":\"progressDot\",\"type\":\"switch\",\"label\":\"设置为点状步骤条\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false,\"disabled\":true},{\"name\":\"items\",\"type\":\"optionTree\",\"label\":\"步骤标签\",\"showLabelOption\":false,\"default\":{\"labelShow\":false,\"multiple\":false,\"default\":[\"Step1\"],\"options\":[{\"value\":\"Step1\"},{\"value\":\"Step2\"},{\"value\":\"Step3\"}]},\"help\":\"必须保证值的全局惟一性\",\"disabled\":false}],\"widgetConfig\":{\"key\":\"steps_0aa7a4e6\",\"width\":100,\"stepType\":\"navigation\",\"direction\":\"horizontal\",\"labelPlacement\":\"horizontal\",\"progressDot\":false,\"items\":{\"labelShow\":false,\"default\":[\"工作3\"],\"options\":[{\"value\":\"工作1\",\"key\":\"Step1\",\"title\":\"工作1\",\"label\":\"工作1\"},{\"value\":\"工作2\",\"key\":\"Step2\",\"title\":\"工作2\",\"label\":\"工作2\"},{\"value\":\"工作3\",\"key\":\"Step3\",\"title\":\"工作3\",\"label\":\"工作3\"}]}}},{\"icon\":\"atom-layout-tab\",\"title\":\"标签布局\",\"type\":\"tabs\",\"items\":[{\"key\":\"职业1\",\"title\":\"职业1\",\"widgets\":[{\"icon\":\"atom-form-input\",\"title\":\"单行文本\",\"type\":\"input\",\"group\":\"basic\",\"key\":\"input_ae426823\",\"options\":{\"type\":\"input\",\"label\":\"单行文本\",\"placeholder\":\"请输入单行文本\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"input_ae426823\",\"key\":\"input_ae426823\",\"width\":100,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"input_ae426823\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"text\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"input_ae426823\",\"label\":\"单行文本\",\"width\":100,\"disabled\":false,\"labelVisible\":true}}]},{\"key\":\"职业2\",\"title\":\"职业2\",\"widgets\":[{\"icon\":\"atom-form-input\",\"title\":\"单行文本\",\"type\":\"input\",\"group\":\"basic\",\"key\":\"input_3509ba59\",\"options\":{\"type\":\"input\",\"label\":\"单行文本\",\"placeholder\":\"请输入单行文本\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"input_3509ba59\",\"key\":\"input_3509ba59\",\"width\":100,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"input_3509ba59\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"text\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"input_3509ba59\",\"label\":\"单行文本\",\"width\":100,\"disabled\":false,\"labelVisible\":true}}]},{\"key\":\"职业3\",\"title\":\"职业3\",\"widgets\":[{\"icon\":\"atom-form-input\",\"title\":\"单行文本\",\"type\":\"input\",\"group\":\"basic\",\"key\":\"input_bfe0ee49\",\"options\":{\"type\":\"input\",\"label\":\"单行文本\",\"placeholder\":\"请输入单行文本\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"input_bfe0ee49\",\"key\":\"input_bfe0ee49\",\"width\":100,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"input_bfe0ee49\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"text\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"input_bfe0ee49\",\"label\":\"单行文本\",\"width\":100,\"disabled\":false,\"labelVisible\":true}}]}],\"group\":\"layout\",\"key\":\"tabs_b7d32132\",\"options\":{\"type\":\"tabs\",\"label\":\"标签布局\",\"name\":\"tabs_b7d32132\",\"style\":{\"width\":\"100%\"},\"key\":\"tabs_b7d32132\",\"width\":100,\"tabType\":\"line\",\"tabPosition\":\"top\",\"items\":{\"labelShow\":false,\"default\":[\"职业3\"],\"options\":[{\"value\":\"职业1\",\"key\":\"Tab1\",\"title\":\"职业1\",\"label\":\"职业1\"},{\"value\":\"职业2\",\"key\":\"Tab2\",\"title\":\"职业2\",\"label\":\"职业2\"},{\"value\":\"职业3\",\"key\":\"Tab3\",\"title\":\"职业3\",\"label\":\"职业3\"}]}},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"tabs_b7d32132\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"tabType\",\"type\":\"radio\",\"label\":\"标签页风格\",\"default\":\"line\",\"mode\":\"button\",\"buttonStyle\":\"solid\",\"options\":[{\"value\":\"line\",\"title\":\"选项卡\"},{\"value\":\"card\",\"title\":\"卡片式\"}]},{\"name\":\"tabPosition\",\"type\":\"radio\",\"label\":\"标签位置\",\"default\":\"top\",\"mode\":\"button\",\"buttonStyle\":\"solid\",\"options\":[{\"value\":\"top\",\"title\":\"顶部\"},{\"value\":\"right\",\"title\":\"右侧\"},{\"value\":\"bottom\",\"title\":\"底部\"},{\"value\":\"left\",\"title\":\"左侧\"}]},{\"name\":\"items\",\"type\":\"optionTree\",\"label\":\"选项标签\",\"showLabelOption\":false,\"default\":{\"labelShow\":false,\"multiple\":false,\"default\":[\"Tab1\"],\"options\":[{\"value\":\"Tab1\"},{\"value\":\"Tab2\"},{\"value\":\"Tab3\"}]},\"help\":\"必须保证值的全局惟一性\"}],\"widgetConfig\":{\"key\":\"tabs_b7d32132\",\"width\":100,\"tabType\":\"line\",\"tabPosition\":\"top\",\"items\":{\"labelShow\":false,\"default\":[\"职业3\"],\"options\":[{\"value\":\"职业1\",\"key\":\"Tab1\",\"title\":\"职业1\",\"label\":\"职业1\"},{\"value\":\"职业2\",\"key\":\"Tab2\",\"title\":\"职业2\",\"label\":\"职业2\"},{\"value\":\"职业3\",\"key\":\"Tab3\",\"title\":\"职业3\",\"label\":\"职业3\"}]}}}]', '2021-11-24 14:41:32', 100000, '管理员', 1);
INSERT INTO `sys_form` VALUES (2, '个人信息', 'mac', 60, '{\"width\":60,\"dialogSize\":720,\"labelAlign\":\"right\",\"labelCol\":{\"style\":{\"width\":\"80px\"}},\"labelColType\":\"style\",\"labelColSize\":\"80\",\"size\":\"default\",\"colon\":true,\"title\":\"个人信息\"}', '[{\"icon\":\"atom-form-input\",\"title\":\"单行文本\",\"type\":\"input\",\"group\":\"basic\",\"key\":\"input_4763648d\",\"options\":{\"type\":\"input\",\"label\":\"名称\",\"placeholder\":\"请输入名称\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"input_4763648d\",\"key\":\"input_4763648d\",\"width\":100,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"input_4763648d\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"text\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"input_4763648d\",\"label\":\"名称\",\"width\":100,\"disabled\":false,\"labelVisible\":true}},{\"icon\":\"atom-form-textarea\",\"title\":\"多行文本\",\"type\":\"textarea\",\"group\":\"basic\",\"key\":\"textarea_3491e640\",\"options\":{\"type\":\"textarea\",\"label\":\"个人履历\",\"placeholder\":\"请输入个人履历\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"textarea_3491e640\",\"key\":\"textarea_3491e640\",\"width\":100,\"rows\":4,\"autoSize\":false,\"showCount\":true,\"disabled\":false,\"labelVisible\":true},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"textarea_3491e640\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"多行文本\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"default\",\"type\":\"textarea\",\"label\":\"默认值\"},{\"name\":\"maxlength\",\"type\":\"number\",\"label\":\"最大长度\",\"step\":10},{\"name\":\"rows\",\"type\":\"number\",\"label\":\"行数\",\"default\":4},{\"name\":\"autoSize\",\"type\":\"switch\",\"label\":\"自适应内容高度\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"showCount\",\"type\":\"switch\",\"label\":\"是否展示字数\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"textarea_3491e640\",\"label\":\"个人履历\",\"width\":100,\"rows\":4,\"autoSize\":false,\"showCount\":true,\"disabled\":false,\"labelVisible\":true}},{\"icon\":\"atom-form-radio\",\"title\":\"单选框组\",\"type\":\"radio\",\"group\":\"basic\",\"key\":\"radio_8d12e7fd\",\"options\":{\"type\":\"radio\",\"label\":\"性别\",\"placeholder\":\"请选择性别\",\"size\":\"default\",\"hidden\":false,\"replaceFields\":{\"key\":\"key\",\"title\":\"title\",\"children\":\"children\",\"label\":\"label\",\"value\":\"value\",\"status\":\"status\",\"color\":\"color\"},\"style\":{\"width\":\"100%\"},\"name\":\"radio_8d12e7fd\",\"key\":\"radio_8d12e7fd\",\"width\":100,\"mode\":\"-\",\"buttonStyle\":\"-\",\"options\":[{\"value\":\"m\",\"title\":\"男\",\"key\":\"value1\",\"label\":\"男\"},{\"value\":\"f\",\"title\":\"女\",\"key\":\"value2\",\"label\":\"女\"}],\"disabled\":false,\"labelVisible\":true,\"default\":\"m\"},\"fields\":[{\"name\":\"key\",\"type\":\"text\",\"label\":\"字段标识[需当前表单惟一]\",\"required\":true,\"default\":\"radio_8d12e7fd\"},{\"name\":\"label\",\"type\":\"text\",\"label\":\"标题\",\"required\":true,\"default\":\"单选框组\"},{\"name\":\"width\",\"type\":\"number\",\"label\":\"宽度\",\"min\":0,\"max\":100,\"default\":100},{\"name\":\"mode\",\"type\":\"radio\",\"label\":\"选择模式\",\"default\":\"-\",\"mode\":\"button\",\"buttonStyle\":\"solid\",\"options\":[{\"value\":\"-\",\"title\":\"默认\"},{\"value\":\"button\",\"title\":\"按钮\"}]},{\"name\":\"buttonStyle\",\"type\":\"radio\",\"label\":\"按钮模式\",\"default\":\"-\",\"mode\":\"button\",\"buttonStyle\":\"solid\",\"options\":[{\"value\":\"outline\",\"title\":\"边框\"},{\"value\":\"solid\",\"title\":\"填底\"}]},{\"name\":\"options\",\"type\":\"optionTree\",\"label\":\"选项\",\"default\":{\"labelShow\":true,\"default\":[\"value1\"],\"options\":[{\"value\":\"value1\",\"title\":\"选项1\"},{\"value\":\"value2\",\"title\":\"选项2\"},{\"value\":\"value3\",\"title\":\"选项3\"}]},\"help\":\"必须保证值的全局惟一性\"},{\"name\":\"disabled\",\"type\":\"switch\",\"label\":\"是否禁用\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":false},{\"name\":\"labelVisible\",\"type\":\"switch\",\"label\":\"显示标签\",\"checkedValue\":true,\"unCheckedValue\":false,\"default\":true},{\"name\":\"rules\",\"type\":\"textarea\",\"label\":\"校验规则\"},{\"name\":\"placeholder\",\"type\":\"text\",\"label\":\"占位提示\"}],\"widgetConfig\":{\"key\":\"radio_8d12e7fd\",\"label\":\"性别\",\"width\":100,\"mode\":\"-\",\"buttonStyle\":\"-\",\"options\":{\"labelShow\":true,\"default\":[\"m\"],\"options\":[{\"value\":\"m\",\"title\":\"男\",\"key\":\"value1\",\"label\":\"男\"},{\"value\":\"f\",\"title\":\"女\",\"key\":\"value2\",\"label\":\"女\"}]},\"disabled\":false,\"labelVisible\":true}}]', '2021-11-24 20:55:35', 100000, '管理员', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作账号名称',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作用户姓名',
  `type` int(2) NOT NULL DEFAULT '1' COMMENT '日志类型 1：认证登录日志 2：服务调用日志 3：数据同步日志',
  `action_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作类型 同接口ActionType',
  `create_time` bigint(13) NOT NULL COMMENT '日志产生时间',
  `request_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接口URL',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '接口参数',
  `platform_type` int(1) NOT NULL DEFAULT '1' COMMENT '平台类型 同PlatformType',
  `result_status` int(1) NOT NULL DEFAULT '1' COMMENT '请求结果 1:成功 0:失败',
  `execution_time` bigint(20) NOT NULL COMMENT '执行耗时ms',
  `exception` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行异常信息',
  `result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '响应内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `menu_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单名称',
  `english_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '英文名称',
  `menu_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单描述',
  `menu_icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单图标',
  `route_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路由的名称',
  `parent_id` int(6) DEFAULT NULL COMMENT '父级菜单，顶级菜单为NULL',
  `menu_order` int(2) NOT NULL DEFAULT '1' COMMENT '父级中的顺序',
  `modify_time` datetime NOT NULL COMMENT '创建/修改时间',
  `hidden` int(2) NOT NULL DEFAULT '0' COMMENT '是否隐藏，1是，0否，默认0',
  `if_valid` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效1有效，0失效',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_route` (`route_name`) COMMENT '路由一定不能重复'
) ENGINE=InnoDB AUTO_INCREMENT=100023 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统菜单信息';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (100000, '首页', 'Dashboard', '工作台首页', 'DashboardOutlined', 'home', NULL, 1, '2021-07-16 16:24:24', 0, 1);
INSERT INTO `sys_menu` VALUES (100001, '表格展示', 'Table', '表格展示', 'TableOutlined', 'table', NULL, 2, '2021-11-26 18:53:22', 0, 1);
INSERT INTO `sys_menu` VALUES (100002, '基础表格', 'Basic Table', '基础表格', NULL, 'basicTable', 100001, 1, '2021-11-26 18:53:32', 0, 1);
INSERT INTO `sys_menu` VALUES (100003, '高级表格', 'Advance Table', '高级表格', NULL, 'advanceTable', 100001, 2, '2021-11-26 18:53:41', 0, 1);
INSERT INTO `sys_menu` VALUES (100004, '列表展示', NULL, '列表展示', 'PicRightOutlined', 'list', NULL, 3, '2021-11-26 18:54:30', 0, 1);
INSERT INTO `sys_menu` VALUES (100005, '基础列表', NULL, '基础列表', NULL, 'basicList', 100004, 1, '2021-11-26 18:55:25', 0, 1);
INSERT INTO `sys_menu` VALUES (100006, '高级列表', NULL, '高级列表', NULL, 'advanceList', 100004, 2, '2021-11-26 18:55:50', 0, 1);
INSERT INTO `sys_menu` VALUES (100007, '卡片列表', 'Card Table', '卡片列表', NULL, 'cardList', 100004, 3, '2021-12-06 09:05:10', 0, 1);
INSERT INTO `sys_menu` VALUES (100008, '表单呈现', 'Form', '表单呈现', 'FormOutlined', 'form', NULL, 4, '2021-11-26 18:54:45', 0, 1);
INSERT INTO `sys_menu` VALUES (100009, '基础表单', 'Basic Form', '基础表单', NULL, 'basicForm', 100008, 1, '2021-07-16 16:28:21', 0, 1);
INSERT INTO `sys_menu` VALUES (100010, '分步表单', 'Step Form', '分步表单', NULL, 'stepForm', 100008, 4, '2021-07-16 16:28:51', 0, 1);
INSERT INTO `sys_menu` VALUES (100011, '高级表单', 'Advance Form', '高级表单', NULL, 'advanceForm', 100008, 2, '2021-07-16 16:29:06', 0, 1);
INSERT INTO `sys_menu` VALUES (100012, '动态表单', 'Dynamic Form', '动态表单', NULL, 'dynamicForm', 100008, 3, '2021-07-16 16:29:22', 0, 1);
INSERT INTO `sys_menu` VALUES (100013, '系统管理', 'System', '系统管理', 'SettingOutlined', 'system', NULL, 5, '2021-11-26 18:54:56', 0, 1);
INSERT INTO `sys_menu` VALUES (100014, '组织管理', 'Department', '组织管理', NULL, 'sysdept', 100013, 1, '2021-08-10 12:05:29', 0, 1);
INSERT INTO `sys_menu` VALUES (100015, '用户管理', 'User', '用户管理', NULL, 'sysuser', 100013, 2, '2021-08-10 12:06:18', 0, 1);
INSERT INTO `sys_menu` VALUES (100016, '角色管理', 'Role', '角色管理', NULL, 'sysrole', 100013, 3, '2021-08-10 12:06:47', 0, 1);
INSERT INTO `sys_menu` VALUES (100017, '菜单管理', 'Menu', '菜单管理', NULL, 'sysmenu', 100013, 4, '2021-08-10 12:07:11', 0, 1);
INSERT INTO `sys_menu` VALUES (100018, '数据字典', 'Directory', '数据字典', NULL, 'systype', 100013, 5, '2021-08-10 12:07:35', 0, 1);
INSERT INTO `sys_menu` VALUES (100019, '表单管理', 'Form', '表单管理', NULL, 'sysform', 100013, 6, '2021-11-19 17:49:14', 0, 1);
INSERT INTO `sys_menu` VALUES (100020, '消息管理', 'News', '消息管理', NULL, 'sysnews', 100013, 7, '2021-11-19 17:48:59', 0, 1);
INSERT INTO `sys_menu` VALUES (100021, '文件管理', 'File', '文件管理', NULL, 'sysfile', 100013, 8, '2021-11-19 17:49:04', 0, 1);
INSERT INTO `sys_menu` VALUES (100022, '日志管理', 'Log', '日志管理', NULL, 'syslog', 100013, 9, '2021-11-19 17:49:09', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_menu_topic
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_topic`;
CREATE TABLE `sys_menu_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列ID',
  `menu_id` int(6) NOT NULL COMMENT '菜单ID',
  `topic_id` int(3) NOT NULL COMMENT '响应主题ID',
  PRIMARY KEY (`id`),
  KEY `fk_menu_topic` (`menu_id`),
  KEY `fk_topic_menu` (`topic_id`),
  CONSTRAINT `fk_menu_topic` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_topic_menu` FOREIGN KEY (`topic_id`) REFERENCES `sys_action_topic` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统菜单动作资源主题';

-- ----------------------------
-- Records of sys_menu_topic
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu_topic` VALUES (1, 100011, 104);
INSERT INTO `sys_menu_topic` VALUES (2, 100012, 108);
INSERT INTO `sys_menu_topic` VALUES (3, 100012, 104);
INSERT INTO `sys_menu_topic` VALUES (4, 100012, 106);
INSERT INTO `sys_menu_topic` VALUES (5, 100013, 106);
INSERT INTO `sys_menu_topic` VALUES (6, 100013, 105);
INSERT INTO `sys_menu_topic` VALUES (7, 100013, 107);
INSERT INTO `sys_menu_topic` VALUES (8, 100014, 105);
INSERT INTO `sys_menu_topic` VALUES (9, 100014, 107);
INSERT INTO `sys_menu_topic` VALUES (10, 100015, 102);
COMMIT;

-- ----------------------------
-- Table structure for sys_news
-- ----------------------------
DROP TABLE IF EXISTS `sys_news`;
CREATE TABLE `sys_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` int(2) NOT NULL DEFAULT '1' COMMENT '信息类型1待办，2通知，3消息',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `desc` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容简介，由内容抽取前200字符',
  `from_user` int(6) DEFAULT NULL COMMENT '源用户，系统提示用户为空',
  `to_user` int(6) NOT NULL COMMENT '目标用户id',
  `route` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0未读 1已读',
  `create_time` datetime NOT NULL COMMENT '消息创建时间',
  `if_valid` int(1) DEFAULT '1' COMMENT '是否有效1有效，0失效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统通知消息待办信息';

-- ----------------------------
-- Records of sys_news
-- ----------------------------
BEGIN;
INSERT INTO `sys_news` VALUES (1, 2, '收到面试邀约', '', NULL, 100000, '/', 0, '2020-09-03 10:59:01', 1);
INSERT INTO `sys_news` VALUES (2, 2, '来自未来的邮件', '', NULL, 100000, '/', 0, '2021-04-08 10:59:35', 0);
INSERT INTO `sys_news` VALUES (3, 2, '明天检查消防设施', '', NULL, 100000, '/', 0, '2021-06-17 11:00:21', 1);
INSERT INTO `sys_news` VALUES (4, 2, '出货单已发', '', NULL, 100000, '/', 1, '2021-07-20 11:00:50', 1);
INSERT INTO `sys_news` VALUES (5, 2, '努力才会有收获', '', NULL, 100000, '/', 1, '2021-08-23 11:01:06', 1);
INSERT INTO `sys_news` VALUES (6, 3, '来自未来的评论', '', NULL, 100000, '/', 1, '2021-06-03 11:01:30', 1);
INSERT INTO `sys_news` VALUES (7, 3, 'Do Your Job, Good Job', '', NULL, 100000, '/', 1, '2021-07-15 11:02:05', 1);
INSERT INTO `sys_news` VALUES (8, 3, 'Atom评论了你', '', NULL, 100000, '/', 0, '2021-08-24 11:02:23', 1);
INSERT INTO `sys_news` VALUES (9, 1, '预案智能化', '', NULL, 100000, '/', 0, '2021-08-01 11:02:52', 1);
INSERT INTO `sys_news` VALUES (10, 1, '上报每周工作进展', '', NULL, 100000, '/', 1, '2021-08-17 11:03:13', 1);
INSERT INTO `sys_news` VALUES (11, 1, 'LTE装备调试', '', NULL, 100000, '/', 0, '2021-08-24 11:03:42', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_news_content
-- ----------------------------
DROP TABLE IF EXISTS `sys_news_content`;
CREATE TABLE `sys_news_content` (
  `id` int(11) NOT NULL COMMENT 'ID',
  `news_id` int(11) NOT NULL COMMENT '消息ID',
  `content` longtext COLLATE utf8mb4_general_ci COMMENT '消息内容',
  `files` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '消息附件，逗号分隔',
  `extra_info` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '扩展信息，传递参数',
  PRIMARY KEY (`id`),
  KEY `fk_news` (`news_id`),
  CONSTRAINT `fk_news` FOREIGN KEY (`news_id`) REFERENCES `sys_news` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统通知消息内容';

-- ----------------------------
-- Records of sys_news_content
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_desc` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色说明',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  `if_default` int(2) NOT NULL DEFAULT '0' COMMENT '是否默认角色1是，0否，最多只允许一个默认权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (100, '超级管理员', '超级管理员', '2021-07-16 16:46:36', 0);
INSERT INTO `sys_role` VALUES (101, '管理员', '管理员', '2021-08-09 14:53:11', 0);
INSERT INTO `sys_role` VALUES (102, '区局管理员', '区局管理员', '2021-08-09 14:57:00', 0);
INSERT INTO `sys_role` VALUES (103, '普通用户', '普通用户', '2021-08-09 14:58:06', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_action`;
CREATE TABLE `sys_role_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列ID',
  `role_id` int(6) NOT NULL COMMENT '角色ID',
  `action_id` int(6) NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`),
  KEY `fk_roleact_role` (`role_id`),
  KEY `fk_roleact_act` (`action_id`),
  CONSTRAINT `fk_roleres_res` FOREIGN KEY (`action_id`) REFERENCES `sys_action` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_roleres_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1975 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色资源关系表';

-- ----------------------------
-- Records of sys_role_action
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_action` VALUES (1610, 101, 100006);
INSERT INTO `sys_role_action` VALUES (1611, 101, 100009);
INSERT INTO `sys_role_action` VALUES (1612, 101, 100044);
INSERT INTO `sys_role_action` VALUES (1613, 101, 100045);
INSERT INTO `sys_role_action` VALUES (1614, 101, 100046);
INSERT INTO `sys_role_action` VALUES (1615, 101, 100047);
INSERT INTO `sys_role_action` VALUES (1616, 101, 100048);
INSERT INTO `sys_role_action` VALUES (1617, 101, 100049);
INSERT INTO `sys_role_action` VALUES (1618, 101, 100050);
INSERT INTO `sys_role_action` VALUES (1619, 101, 100051);
INSERT INTO `sys_role_action` VALUES (1620, 101, 100052);
INSERT INTO `sys_role_action` VALUES (1621, 101, 100054);
INSERT INTO `sys_role_action` VALUES (1622, 101, 100027);
INSERT INTO `sys_role_action` VALUES (1623, 101, 100028);
INSERT INTO `sys_role_action` VALUES (1624, 101, 100029);
INSERT INTO `sys_role_action` VALUES (1625, 101, 100012);
INSERT INTO `sys_role_action` VALUES (1626, 101, 100002);
INSERT INTO `sys_role_action` VALUES (1627, 101, 100003);
INSERT INTO `sys_role_action` VALUES (1628, 101, 100004);
INSERT INTO `sys_role_action` VALUES (1629, 101, 100005);
INSERT INTO `sys_role_action` VALUES (1630, 101, 100016);
INSERT INTO `sys_role_action` VALUES (1631, 101, 100023);
INSERT INTO `sys_role_action` VALUES (1632, 101, 100000);
INSERT INTO `sys_role_action` VALUES (1633, 101, 100001);
INSERT INTO `sys_role_action` VALUES (1634, 101, 100035);
INSERT INTO `sys_role_action` VALUES (1635, 101, 100036);
INSERT INTO `sys_role_action` VALUES (1636, 101, 100037);
INSERT INTO `sys_role_action` VALUES (1637, 101, 100038);
INSERT INTO `sys_role_action` VALUES (1638, 101, 100039);
INSERT INTO `sys_role_action` VALUES (1639, 101, 100040);
INSERT INTO `sys_role_action` VALUES (1640, 101, 100041);
INSERT INTO `sys_role_action` VALUES (1641, 101, 100042);
INSERT INTO `sys_role_action` VALUES (1642, 101, 100043);
INSERT INTO `sys_role_action` VALUES (1643, 101, 100053);
INSERT INTO `sys_role_action` VALUES (1644, 101, 100055);
INSERT INTO `sys_role_action` VALUES (1920, 100, 100006);
INSERT INTO `sys_role_action` VALUES (1921, 100, 100007);
INSERT INTO `sys_role_action` VALUES (1922, 100, 100008);
INSERT INTO `sys_role_action` VALUES (1923, 100, 100009);
INSERT INTO `sys_role_action` VALUES (1924, 100, 100010);
INSERT INTO `sys_role_action` VALUES (1925, 100, 100011);
INSERT INTO `sys_role_action` VALUES (1926, 100, 100044);
INSERT INTO `sys_role_action` VALUES (1927, 100, 100045);
INSERT INTO `sys_role_action` VALUES (1928, 100, 100046);
INSERT INTO `sys_role_action` VALUES (1929, 100, 100047);
INSERT INTO `sys_role_action` VALUES (1930, 100, 100048);
INSERT INTO `sys_role_action` VALUES (1931, 100, 100049);
INSERT INTO `sys_role_action` VALUES (1932, 100, 100050);
INSERT INTO `sys_role_action` VALUES (1933, 100, 100051);
INSERT INTO `sys_role_action` VALUES (1934, 100, 100052);
INSERT INTO `sys_role_action` VALUES (1935, 100, 100054);
INSERT INTO `sys_role_action` VALUES (1936, 100, 100024);
INSERT INTO `sys_role_action` VALUES (1937, 100, 100025);
INSERT INTO `sys_role_action` VALUES (1938, 100, 100026);
INSERT INTO `sys_role_action` VALUES (1939, 100, 100027);
INSERT INTO `sys_role_action` VALUES (1940, 100, 100028);
INSERT INTO `sys_role_action` VALUES (1941, 100, 100029);
INSERT INTO `sys_role_action` VALUES (1942, 100, 100030);
INSERT INTO `sys_role_action` VALUES (1943, 100, 100031);
INSERT INTO `sys_role_action` VALUES (1944, 100, 100032);
INSERT INTO `sys_role_action` VALUES (1945, 100, 100033);
INSERT INTO `sys_role_action` VALUES (1946, 100, 100034);
INSERT INTO `sys_role_action` VALUES (1947, 100, 100012);
INSERT INTO `sys_role_action` VALUES (1948, 100, 100002);
INSERT INTO `sys_role_action` VALUES (1949, 100, 100003);
INSERT INTO `sys_role_action` VALUES (1950, 100, 100004);
INSERT INTO `sys_role_action` VALUES (1951, 100, 100005);
INSERT INTO `sys_role_action` VALUES (1952, 100, 100013);
INSERT INTO `sys_role_action` VALUES (1953, 100, 100014);
INSERT INTO `sys_role_action` VALUES (1954, 100, 100015);
INSERT INTO `sys_role_action` VALUES (1955, 100, 100016);
INSERT INTO `sys_role_action` VALUES (1956, 100, 100017);
INSERT INTO `sys_role_action` VALUES (1957, 100, 100018);
INSERT INTO `sys_role_action` VALUES (1958, 100, 100019);
INSERT INTO `sys_role_action` VALUES (1959, 100, 100020);
INSERT INTO `sys_role_action` VALUES (1960, 100, 100021);
INSERT INTO `sys_role_action` VALUES (1961, 100, 100022);
INSERT INTO `sys_role_action` VALUES (1962, 100, 100023);
INSERT INTO `sys_role_action` VALUES (1963, 100, 100000);
INSERT INTO `sys_role_action` VALUES (1964, 100, 100001);
INSERT INTO `sys_role_action` VALUES (1965, 100, 100035);
INSERT INTO `sys_role_action` VALUES (1966, 100, 100036);
INSERT INTO `sys_role_action` VALUES (1967, 100, 100037);
INSERT INTO `sys_role_action` VALUES (1968, 100, 100038);
INSERT INTO `sys_role_action` VALUES (1969, 100, 100039);
INSERT INTO `sys_role_action` VALUES (1970, 100, 100040);
INSERT INTO `sys_role_action` VALUES (1971, 100, 100041);
INSERT INTO `sys_role_action` VALUES (1972, 100, 100042);
INSERT INTO `sys_role_action` VALUES (1973, 100, 100043);
INSERT INTO `sys_role_action` VALUES (1974, 100, 100055);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列ID',
  `role_id` int(6) NOT NULL COMMENT '角色ID',
  `menu_id` int(6) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  KEY `fk_rolemenu_role` (`role_id`),
  KEY `fk_rolemenu_menu` (`menu_id`),
  CONSTRAINT `fk_rolemenu_menu` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rolemenu_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=615 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色菜单关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (487, 101, 100000);
INSERT INTO `sys_role_menu` VALUES (488, 101, 100001);
INSERT INTO `sys_role_menu` VALUES (489, 101, 100005);
INSERT INTO `sys_role_menu` VALUES (490, 101, 100010);
INSERT INTO `sys_role_menu` VALUES (491, 101, 100002);
INSERT INTO `sys_role_menu` VALUES (492, 101, 100003);
INSERT INTO `sys_role_menu` VALUES (493, 101, 100004);
INSERT INTO `sys_role_menu` VALUES (494, 101, 100006);
INSERT INTO `sys_role_menu` VALUES (495, 101, 100008);
INSERT INTO `sys_role_menu` VALUES (496, 101, 100009);
INSERT INTO `sys_role_menu` VALUES (497, 101, 100007);
INSERT INTO `sys_role_menu` VALUES (498, 101, 100011);
INSERT INTO `sys_role_menu` VALUES (499, 101, 100012);
INSERT INTO `sys_role_menu` VALUES (500, 101, 100013);
INSERT INTO `sys_role_menu` VALUES (501, 101, 100014);
INSERT INTO `sys_role_menu` VALUES (502, 101, 100015);
INSERT INTO `sys_role_menu` VALUES (503, 101, 100017);
INSERT INTO `sys_role_menu` VALUES (504, 101, 100018);
INSERT INTO `sys_role_menu` VALUES (505, 101, 100019);
INSERT INTO `sys_role_menu` VALUES (506, 101, 100016);
INSERT INTO `sys_role_menu` VALUES (592, 100, 100000);
INSERT INTO `sys_role_menu` VALUES (593, 100, 100001);
INSERT INTO `sys_role_menu` VALUES (594, 100, 100004);
INSERT INTO `sys_role_menu` VALUES (595, 100, 100008);
INSERT INTO `sys_role_menu` VALUES (596, 100, 100013);
INSERT INTO `sys_role_menu` VALUES (597, 100, 100002);
INSERT INTO `sys_role_menu` VALUES (598, 100, 100003);
INSERT INTO `sys_role_menu` VALUES (599, 100, 100005);
INSERT INTO `sys_role_menu` VALUES (600, 100, 100006);
INSERT INTO `sys_role_menu` VALUES (601, 100, 100007);
INSERT INTO `sys_role_menu` VALUES (602, 100, 100009);
INSERT INTO `sys_role_menu` VALUES (603, 100, 100011);
INSERT INTO `sys_role_menu` VALUES (604, 100, 100012);
INSERT INTO `sys_role_menu` VALUES (605, 100, 100010);
INSERT INTO `sys_role_menu` VALUES (606, 100, 100014);
INSERT INTO `sys_role_menu` VALUES (607, 100, 100015);
INSERT INTO `sys_role_menu` VALUES (608, 100, 100016);
INSERT INTO `sys_role_menu` VALUES (609, 100, 100017);
INSERT INTO `sys_role_menu` VALUES (610, 100, 100018);
INSERT INTO `sys_role_menu` VALUES (611, 100, 100019);
INSERT INTO `sys_role_menu` VALUES (612, 100, 100020);
INSERT INTO `sys_role_menu` VALUES (613, 100, 100021);
INSERT INTO `sys_role_menu` VALUES (614, 100, 100022);
COMMIT;

-- ----------------------------
-- Table structure for sys_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_type`;
CREATE TABLE `sys_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号ID',
  `mean_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `mean_desc` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典描述',
  `multi_level` int(1) NOT NULL DEFAULT '0' COMMENT '是否多层级1是0否',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统数据字典表';

-- ----------------------------
-- Records of sys_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_type` VALUES (100, '附件类型', '附件类型', 0);
INSERT INTO `sys_type` VALUES (101, '组织机构', '组织机构', 1);
INSERT INTO `sys_type` VALUES (102, '行政区划', '中国行政区划', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_type_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_type_code`;
CREATE TABLE `sys_type_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列ID',
  `type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型名称',
  `type_desc` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型描述',
  `parent_id` int(6) DEFAULT NULL COMMENT '类型父级编码，可以为空',
  `type_order` int(11) DEFAULT NULL COMMENT '排序',
  `mean_id` int(11) NOT NULL COMMENT '数据字典ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_mean_type` (`mean_id`) USING BTREE,
  CONSTRAINT `fk_mean_type` FOREIGN KEY (`mean_id`) REFERENCES `sys_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统码表';

-- ----------------------------
-- Records of sys_type_code
-- ----------------------------
BEGIN;
INSERT INTO `sys_type_code` VALUES (1, '文件夹', '文件夹', NULL, 1, 100);
INSERT INTO `sys_type_code` VALUES (2, '图片', '图片', NULL, 2, 100);
INSERT INTO `sys_type_code` VALUES (3, '视频', '视频', NULL, 3, 100);
INSERT INTO `sys_type_code` VALUES (4, '音乐', '音乐', NULL, 4, 100);
INSERT INTO `sys_type_code` VALUES (5, '文档', '文档', NULL, 5, 100);
INSERT INTO `sys_type_code` VALUES (6, '其他', '其他', NULL, 6, 100);
INSERT INTO `sys_type_code` VALUES (9, '市应急局', '市应急局', NULL, 1, 101);
INSERT INTO `sys_type_code` VALUES (10, '区应急局', '区应急局', NULL, 2, 101);
INSERT INTO `sys_type_code` VALUES (11, '市委市政府', '市委市政府', NULL, 3, 101);
INSERT INTO `sys_type_code` VALUES (12, '办公室', '办公室', 9, 1, 101);
INSERT INTO `sys_type_code` VALUES (13, '指挥中心', '指挥中心', 9, 2, 101);
INSERT INTO `sys_type_code` VALUES (14, '事务中心', '事务中心', 9, 3, 101);
INSERT INTO `sys_type_code` VALUES (15, '处置中心', '处置中心', 9, 4, 101);
INSERT INTO `sys_type_code` VALUES (16, '局网信办', '局网信办', 9, 5, 101);
INSERT INTO `sys_type_code` VALUES (17, '安研中心', '安全生产技术研究中心', 9, 6, 101);
INSERT INTO `sys_type_code` VALUES (18, '和平区应急局', '和平区应急局', 10, 1, 101);
INSERT INTO `sys_type_code` VALUES (19, '南开区应急局', '南开区应急局', 10, 2, 101);
INSERT INTO `sys_type_code` VALUES (20, '河东区应急局', '河东区应急局', 10, 3, 101);
INSERT INTO `sys_type_code` VALUES (21, '河西区应急局', '河西区应急局', 10, 4, 101);
INSERT INTO `sys_type_code` VALUES (22, '河北区应急局', '河北区应急局', 10, 5, 101);
INSERT INTO `sys_type_code` VALUES (23, '红桥区应急局', '红桥区应急局', 10, 6, 101);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '系统帐号',
  `phone` varchar(11) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `password` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户密码信息',
  `motto` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '格言',
  `third_type` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '第三方登录类型，wechat, qq, alipay',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '第三方登陆昵称',
  `open_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '第三方登陆openid',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '位置信息省、市、县、街道、社区编码',
  `location_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '位置信息省、市、县、街道、社区名称',
  `head` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '用户头像',
  `dept_id` int(6) DEFAULT '1000' COMMENT '部门ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `last_login` datetime DEFAULT NULL COMMENT '最后登录时间',
  `extra_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户扩展信息',
  `app_config` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '应用配置信息',
  `if_valid` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效1有效，0失效',
  `platform` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'dashboard' COMMENT '用户所属平台 portal OR dashboard',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_user_dept` (`dept_id`),
  CONSTRAINT `fk_user_dept` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100002 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (100000, 'super', '13821220101', 'atomgit@sina.com', '管理员', '$2a$10$hNMnqKOWn66d89Rqh0Tm1uSvbJ1Zt8/kfogJhjLFDKlQK0.nb6Kj.', '发掘冰山下的无限潜能', NULL, '', NULL, '12|1201|120103', '天津市|市辖区|河西区', 'data:image/png;base64,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', 1000, '2021-07-16 16:42:09', '2021-12-16 13:52:02', '2021-12-17 10:05:53', NULL, '{\"dialog\":{\"size\":\"720\",\"type\":\"modal\"},\"fixHeader\":true,\"layout\":\"mix\",\"multiTab\":true,\"multiTabDraggable\":false,\"primaryColor\":\"#9270CA\",\"theme\":\"color\",\"transition\":{\"direction\":\"Down\",\"disabled\":true,\"name\":\"back\"},\"waterMark\":false}', 1, 'dashboard');
INSERT INTO `sys_user` VALUES (100001, 'atom', '18630855886', 'zhangrui.ch@gmail.com', 'Atom.Z', '$2a$10$hNMnqKOWn66d89Rqh0Tm1uSvbJ1Zt8/kfogJhjLFDKlQK0.nb6Kj.', NULL, NULL, NULL, NULL, NULL, NULL, 'default_head.png', 1003, '2021-07-29 14:50:02', '2021-09-15 11:56:16', NULL, NULL, '{\"dialog\":{\"size\":520,\"type\":\"drawer\"},\"fixHeader\":true,\"layout\":\"mix\",\"multiTab\":true,\"multiTabDraggable\":false,\"primaryColor\":\"#E8684A\",\"theme\":\"mix\",\"transition\":{\"direction\":\"Right\",\"disabled\":false,\"name\":\"slide\"},\"waterMark\":false}', 1, 'dashboard');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列ID',
  `user_id` int(6) NOT NULL COMMENT '用户ID',
  `role_id` int(6) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  KEY `fk_user` (`user_id`),
  KEY `fk_role` (`role_id`),
  CONSTRAINT `fk_userrole_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_userrole_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 100000, 100);
INSERT INTO `sys_user_role` VALUES (4, 100001, 100);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
