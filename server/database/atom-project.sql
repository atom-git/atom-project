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

 Date: 15/09/2021 10:39:55
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
) ENGINE=InnoDB AUTO_INCREMENT=100064 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统api动作资源信息，对应菜单按钮';

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
COMMIT;

-- ----------------------------
-- Table structure for sys_action_topic
-- ----------------------------
DROP TABLE IF EXISTS `sys_action_topic`;
CREATE TABLE `sys_action_topic` (
  `id` int(3) NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `name` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '响应主题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统动作响应资源对应的主题，一个菜单可以有N个主题';

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
  `english_name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '英文名称',
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
) ENGINE=InnoDB AUTO_INCREMENT=100019 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统菜单信息';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (100000, '首页', 'Dashboard', '工作台首页', 'DashboardOutlined', 'home', NULL, 1, '2021-07-16 16:24:24', 0, 1);
INSERT INTO `sys_menu` VALUES (100001, '列表展示', 'Table', '列表展示', 'TableOutlined', 'table', NULL, 2, '2021-07-16 16:25:30', 0, 1);
INSERT INTO `sys_menu` VALUES (100002, '基础列表', 'Basic Table', '基础列表', NULL, 'basicTable', 100001, 1, '2021-07-16 16:26:59', 0, 1);
INSERT INTO `sys_menu` VALUES (100003, '高级列表', 'Advance Table', '高级列表', NULL, 'advanceTable', 100001, 2, '2021-07-16 16:27:23', 0, 1);
INSERT INTO `sys_menu` VALUES (100004, '卡片列表', 'Card Table', '卡片列表', NULL, 'cardTable', 100001, 3, '2021-07-16 16:27:32', 0, 1);
INSERT INTO `sys_menu` VALUES (100005, '表单呈现', 'Form', '表单呈现', 'FormOutlined', 'form', NULL, 3, '2021-07-16 16:27:59', 0, 1);
INSERT INTO `sys_menu` VALUES (100006, '基础表单', 'Basic Form', '基础表单', NULL, 'basicForm', 100005, 1, '2021-07-16 16:28:21', 0, 1);
INSERT INTO `sys_menu` VALUES (100007, '分步表单', 'Step Form', '分步表单', NULL, 'stepForm', 100005, 4, '2021-07-16 16:28:51', 0, 1);
INSERT INTO `sys_menu` VALUES (100008, '高级表单', 'Advance Form', '高级表单', NULL, 'advanceForm', 100005, 2, '2021-07-16 16:29:06', 0, 1);
INSERT INTO `sys_menu` VALUES (100009, '动态表单', 'Dynamic Form', '动态表单', NULL, 'dynamicForm', 100005, 3, '2021-07-16 16:29:22', 0, 1);
INSERT INTO `sys_menu` VALUES (100010, '系统管理', 'System', '系统管理', 'SettingOutlined', 'system', NULL, 4, '2021-07-16 16:29:47', 0, 1);
INSERT INTO `sys_menu` VALUES (100011, '组织管理', 'Department', '组织管理', NULL, 'sysdept', 100010, 1, '2021-08-10 12:05:29', 0, 1);
INSERT INTO `sys_menu` VALUES (100012, '用户管理', 'User', '用户管理', NULL, 'sysuser', 100010, 2, '2021-08-10 12:06:18', 0, 1);
INSERT INTO `sys_menu` VALUES (100013, '角色管理', 'Role', '角色管理', NULL, 'sysrole', 100010, 3, '2021-08-10 12:06:47', 0, 1);
INSERT INTO `sys_menu` VALUES (100014, '菜单管理', 'Menu', '菜单管理', NULL, 'sysmenu', 100010, 4, '2021-08-10 12:07:11', 0, 1);
INSERT INTO `sys_menu` VALUES (100015, '数据字典', 'Directory', '数据字典', NULL, 'systype', 100010, 5, '2021-08-10 12:07:35', 0, 1);
INSERT INTO `sys_menu` VALUES (100016, '消息管理', 'News', '消息管理', NULL, 'sysnews', 100010, 6, '2021-08-24 10:22:47', 0, 1);
INSERT INTO `sys_menu` VALUES (100017, '文件管理', 'File', '文件管理', NULL, 'sysfile', 100010, 7, '2021-07-16 16:31:26', 0, 1);
INSERT INTO `sys_menu` VALUES (100018, '日志管理', 'Log', '日志管理', NULL, 'syslog', 100010, 8, '2021-07-16 16:31:40', 0, 1);
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
  `type` int(2) NOT NULL DEFAULT '1' COMMENT '信息类型1通知，2消息，3待办',
  `title` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
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
INSERT INTO `sys_news` VALUES (1, 1, '收到面试邀约', NULL, 100000, '/', 0, '2020-09-03 10:59:01', 1);
INSERT INTO `sys_news` VALUES (2, 1, '来自未来的邮件', NULL, 100000, '/', 0, '2021-04-08 10:59:35', 0);
INSERT INTO `sys_news` VALUES (3, 1, '明天检查消防设施', NULL, 100000, '/', 0, '2021-06-17 11:00:21', 0);
INSERT INTO `sys_news` VALUES (4, 1, '出货单已发', NULL, 100000, '/', 1, '2021-07-20 11:00:50', 1);
INSERT INTO `sys_news` VALUES (5, 1, '努力才会有收获', NULL, 100000, '/', 1, '2021-08-23 11:01:06', 1);
INSERT INTO `sys_news` VALUES (6, 2, '来自未来的评论', NULL, 100000, '/', 1, '2021-06-03 11:01:30', 1);
INSERT INTO `sys_news` VALUES (7, 2, 'Do Your Job, Good Job', NULL, 100000, '/', 1, '2021-07-15 11:02:05', 1);
INSERT INTO `sys_news` VALUES (8, 2, 'Atom评论了你', NULL, 100000, '/', 0, '2021-08-24 11:02:23', 1);
INSERT INTO `sys_news` VALUES (9, 3, '预案智能化', NULL, 100000, '/', 0, '2021-08-01 11:02:52', 1);
INSERT INTO `sys_news` VALUES (10, 3, '上报每周工作进展', NULL, 100000, '/', 1, '2021-08-17 11:03:13', 1);
INSERT INTO `sys_news` VALUES (11, 3, 'LTE装备调试', NULL, 100000, '/', 0, '2021-08-24 11:03:42', 1);
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
) ENGINE=InnoDB AUTO_INCREMENT=1575 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色资源关系表';

-- ----------------------------
-- Records of sys_role_action
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_action` VALUES (1, 100, 100000);
INSERT INTO `sys_role_action` VALUES (2, 100, 100001);
INSERT INTO `sys_role_action` VALUES (3, 100, 100002);
INSERT INTO `sys_role_action` VALUES (4, 100, 100003);
INSERT INTO `sys_role_action` VALUES (5, 100, 100004);
INSERT INTO `sys_role_action` VALUES (6, 100, 100005);
INSERT INTO `sys_role_action` VALUES (7, 100, 100006);
INSERT INTO `sys_role_action` VALUES (8, 100, 100007);
INSERT INTO `sys_role_action` VALUES (9, 100, 100008);
INSERT INTO `sys_role_action` VALUES (10, 100, 100009);
INSERT INTO `sys_role_action` VALUES (11, 100, 100010);
INSERT INTO `sys_role_action` VALUES (12, 100, 100011);
INSERT INTO `sys_role_action` VALUES (13, 100, 100012);
INSERT INTO `sys_role_action` VALUES (14, 100, 100013);
INSERT INTO `sys_role_action` VALUES (15, 100, 100014);
INSERT INTO `sys_role_action` VALUES (16, 100, 100015);
INSERT INTO `sys_role_action` VALUES (17, 100, 100016);
INSERT INTO `sys_role_action` VALUES (18, 100, 100017);
INSERT INTO `sys_role_action` VALUES (19, 100, 100018);
INSERT INTO `sys_role_action` VALUES (20, 100, 100019);
INSERT INTO `sys_role_action` VALUES (21, 100, 100020);
INSERT INTO `sys_role_action` VALUES (22, 100, 100021);
INSERT INTO `sys_role_action` VALUES (23, 100, 100022);
INSERT INTO `sys_role_action` VALUES (24, 100, 100023);
INSERT INTO `sys_role_action` VALUES (25, 100, 100024);
INSERT INTO `sys_role_action` VALUES (26, 100, 100025);
INSERT INTO `sys_role_action` VALUES (27, 100, 100026);
INSERT INTO `sys_role_action` VALUES (28, 100, 100027);
INSERT INTO `sys_role_action` VALUES (29, 100, 100028);
INSERT INTO `sys_role_action` VALUES (30, 100, 100029);
INSERT INTO `sys_role_action` VALUES (31, 100, 100030);
INSERT INTO `sys_role_action` VALUES (32, 100, 100031);
INSERT INTO `sys_role_action` VALUES (33, 100, 100032);
INSERT INTO `sys_role_action` VALUES (34, 100, 100033);
INSERT INTO `sys_role_action` VALUES (35, 100, 100034);
INSERT INTO `sys_role_action` VALUES (36, 100, 100035);
INSERT INTO `sys_role_action` VALUES (37, 100, 100036);
INSERT INTO `sys_role_action` VALUES (38, 100, 100037);
INSERT INTO `sys_role_action` VALUES (39, 100, 100038);
INSERT INTO `sys_role_action` VALUES (40, 100, 100039);
INSERT INTO `sys_role_action` VALUES (41, 100, 100040);
INSERT INTO `sys_role_action` VALUES (42, 100, 100041);
INSERT INTO `sys_role_action` VALUES (43, 100, 100042);
INSERT INTO `sys_role_action` VALUES (44, 100, 100043);
INSERT INTO `sys_role_action` VALUES (45, 100, 100044);
INSERT INTO `sys_role_action` VALUES (46, 100, 100045);
INSERT INTO `sys_role_action` VALUES (47, 100, 100046);
INSERT INTO `sys_role_action` VALUES (48, 100, 100047);
INSERT INTO `sys_role_action` VALUES (49, 100, 100048);
INSERT INTO `sys_role_action` VALUES (50, 100, 100049);
INSERT INTO `sys_role_action` VALUES (51, 100, 100050);
INSERT INTO `sys_role_action` VALUES (52, 100, 100051);
INSERT INTO `sys_role_action` VALUES (1541, 101, 100006);
INSERT INTO `sys_role_action` VALUES (1542, 101, 100009);
INSERT INTO `sys_role_action` VALUES (1543, 101, 100044);
INSERT INTO `sys_role_action` VALUES (1544, 101, 100045);
INSERT INTO `sys_role_action` VALUES (1545, 101, 100046);
INSERT INTO `sys_role_action` VALUES (1546, 101, 100047);
INSERT INTO `sys_role_action` VALUES (1547, 101, 100048);
INSERT INTO `sys_role_action` VALUES (1548, 101, 100049);
INSERT INTO `sys_role_action` VALUES (1549, 101, 100050);
INSERT INTO `sys_role_action` VALUES (1550, 101, 100051);
INSERT INTO `sys_role_action` VALUES (1551, 101, 100052);
INSERT INTO `sys_role_action` VALUES (1552, 101, 100054);
INSERT INTO `sys_role_action` VALUES (1553, 101, 100027);
INSERT INTO `sys_role_action` VALUES (1554, 101, 100028);
INSERT INTO `sys_role_action` VALUES (1555, 101, 100029);
INSERT INTO `sys_role_action` VALUES (1556, 101, 100012);
INSERT INTO `sys_role_action` VALUES (1557, 101, 100002);
INSERT INTO `sys_role_action` VALUES (1558, 101, 100003);
INSERT INTO `sys_role_action` VALUES (1559, 101, 100004);
INSERT INTO `sys_role_action` VALUES (1560, 101, 100005);
INSERT INTO `sys_role_action` VALUES (1561, 101, 100016);
INSERT INTO `sys_role_action` VALUES (1562, 101, 100023);
INSERT INTO `sys_role_action` VALUES (1563, 101, 100000);
INSERT INTO `sys_role_action` VALUES (1564, 101, 100001);
INSERT INTO `sys_role_action` VALUES (1565, 101, 100035);
INSERT INTO `sys_role_action` VALUES (1566, 101, 100036);
INSERT INTO `sys_role_action` VALUES (1567, 101, 100037);
INSERT INTO `sys_role_action` VALUES (1568, 101, 100038);
INSERT INTO `sys_role_action` VALUES (1569, 101, 100039);
INSERT INTO `sys_role_action` VALUES (1570, 101, 100040);
INSERT INTO `sys_role_action` VALUES (1571, 101, 100041);
INSERT INTO `sys_role_action` VALUES (1572, 101, 100042);
INSERT INTO `sys_role_action` VALUES (1573, 101, 100043);
INSERT INTO `sys_role_action` VALUES (1574, 101, 100053);
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
) ENGINE=InnoDB AUTO_INCREMENT=477 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色菜单关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (1, 100, 100000);
INSERT INTO `sys_role_menu` VALUES (2, 100, 100001);
INSERT INTO `sys_role_menu` VALUES (3, 100, 100002);
INSERT INTO `sys_role_menu` VALUES (4, 100, 100003);
INSERT INTO `sys_role_menu` VALUES (5, 100, 100004);
INSERT INTO `sys_role_menu` VALUES (6, 100, 100005);
INSERT INTO `sys_role_menu` VALUES (7, 100, 100006);
INSERT INTO `sys_role_menu` VALUES (8, 100, 100007);
INSERT INTO `sys_role_menu` VALUES (9, 100, 100008);
INSERT INTO `sys_role_menu` VALUES (10, 100, 100009);
INSERT INTO `sys_role_menu` VALUES (11, 100, 100010);
INSERT INTO `sys_role_menu` VALUES (12, 100, 100011);
INSERT INTO `sys_role_menu` VALUES (13, 100, 100012);
INSERT INTO `sys_role_menu` VALUES (14, 100, 100013);
INSERT INTO `sys_role_menu` VALUES (15, 100, 100014);
INSERT INTO `sys_role_menu` VALUES (16, 100, 100015);
INSERT INTO `sys_role_menu` VALUES (17, 100, 100016);
INSERT INTO `sys_role_menu` VALUES (18, 100, 100017);
INSERT INTO `sys_role_menu` VALUES (19, 100, 100018);
INSERT INTO `sys_role_menu` VALUES (464, 101, 100000);
INSERT INTO `sys_role_menu` VALUES (465, 101, 100001);
INSERT INTO `sys_role_menu` VALUES (466, 101, 100005);
INSERT INTO `sys_role_menu` VALUES (467, 101, 100010);
INSERT INTO `sys_role_menu` VALUES (468, 101, 100002);
INSERT INTO `sys_role_menu` VALUES (469, 101, 100003);
INSERT INTO `sys_role_menu` VALUES (470, 101, 100004);
INSERT INTO `sys_role_menu` VALUES (471, 101, 100006);
INSERT INTO `sys_role_menu` VALUES (472, 101, 100008);
INSERT INTO `sys_role_menu` VALUES (473, 101, 100009);
INSERT INTO `sys_role_menu` VALUES (474, 101, 100007);
INSERT INTO `sys_role_menu` VALUES (475, 101, 100011);
INSERT INTO `sys_role_menu` VALUES (476, 101, 100012);
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
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统数据字典表';

-- ----------------------------
-- Records of sys_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_type` VALUES (100, '附件类型', '附件类型', 0);
INSERT INTO `sys_type` VALUES (101, '组织机构', '组织机构', 1);
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
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `password` varchar(150) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户密码信息',
  `nick_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信昵称',
  `open_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信openid',
  `head` varchar(200) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default_head.png' COMMENT '用户头像',
  `dept_id` int(6) DEFAULT '1000' COMMENT '部门ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
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
INSERT INTO `sys_user` VALUES (100000, 'super', '13821220101', '管理员', '$2a$10$hNMnqKOWn66d89Rqh0Tm1uSvbJ1Zt8/kfogJhjLFDKlQK0.nb6Kj.', '', NULL, 'default_head.png', 1000, '2021-07-16 16:42:09', '2021-07-30 15:12:36', NULL, '{\"dialog\":{\"size\":720,\"type\":\"modal\"},\"fixHeader\":true,\"layout\":\"mix\",\"locale\":\"en-US\",\"multiTab\":true,\"multiTabDraggable\":false,\"primaryColor\":\"#9270CA\",\"theme\":\"mix\",\"transition\":{\"direction\":\"Down\",\"disabled\":true,\"name\":\"back\"},\"waterMark\":false}', 1, 'dashboard');
INSERT INTO `sys_user` VALUES (100001, 'atom', '18630855886', 'Atom.Z', '$2a$10$hNMnqKOWn66d89Rqh0Tm1uSvbJ1Zt8/kfogJhjLFDKlQK0.nb6Kj.', NULL, NULL, 'default_head.png', 1003, '2021-07-29 14:50:02', '2021-09-03 18:00:15', NULL, '{\"dialog\":{\"size\":520,\"type\":\"drawer\"},\"fixHeader\":true,\"layout\":\"mix\",\"multiTab\":true,\"multiTabDraggable\":false,\"primaryColor\":\"#E8684A\",\"theme\":\"mix\",\"transition\":{\"direction\":\"Right\",\"disabled\":false,\"name\":\"slide\"},\"waterMark\":false}', 1, 'dashboard');
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
