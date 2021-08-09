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

 Date: 09/08/2021 14:51:53
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
) ENGINE=InnoDB AUTO_INCREMENT=100055 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统api动作资源信息，对应菜单按钮';

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
INSERT INTO `sys_action` VALUES (100008, '/system/file/upload', '新增-新增', 2, '2021-06-03 15:14:09', 100, '系统附件管理', 0);
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
COMMIT;

-- ----------------------------
-- Table structure for sys_action_topic
-- ----------------------------
DROP TABLE IF EXISTS `sys_action_topic`;
CREATE TABLE `sys_action_topic` (
  `id` int(3) NOT NULL AUTO_INCREMENT COMMENT '自增序列',
  `name` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '响应主题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统动作响应资源对应的主题，一个菜单可以有N个主题';

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
) ENGINE=InnoDB AUTO_INCREMENT=1024 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统组织机构';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (1000, '管理平台', '管理平台', 'Atom', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (1001, '门户平台', '门户平台', 'Atom', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (1003, '市应急局', '市应急局', NULL, NULL, 1000, 1);
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
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `file_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '节点名称',
  `file_type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件类型 ',
  `file_size` bigint(11) DEFAULT NULL COMMENT '节点大小，单位为KB',
  `file_authority` int(1) DEFAULT NULL COMMENT '操作权限：1-只读；2-读写；3读写和删除',
  `file_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '对象存储文件key',
  `bucket_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '对象存储的桶名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '所属父节点id',
  `creator_id` int(6) NOT NULL COMMENT '创建人id',
  `creator_name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人名称',
  `modify_time` datetime NOT NULL COMMENT '创建/修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `INDEX_OF_NAME` (`file_name`) USING BTREE,
  KEY `INDEX_OF_CREATORID` (`creator_id`) USING BTREE,
  KEY `INDEX_OF_FILETYPE` (`file_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10000000000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='附件管理';

-- ----------------------------
-- Records of sys_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `account` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作账号名称',
  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作用户姓名',
  `type` int(2) NOT NULL DEFAULT '1' COMMENT '日志类型 1：认证登录日志 2：服务调用日志 3：数据同步日志',
  `action_type` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作类型 同接口ActionType',
  `create_time` bigint(13) NOT NULL COMMENT '日志产生时间',
  `request_url` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接口URL',
  `request_params` text COLLATE utf8mb4_general_ci COMMENT '接口参数',
  `platform_type` int(1) NOT NULL DEFAULT '1' COMMENT '平台类型 同PlatformType',
  `result_status` int(1) NOT NULL COMMENT '请求结果 1:成功 0:失败',
  `execution_time` bigint(20) NOT NULL COMMENT '执行耗时ms',
  `exception` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行异常信息',
  `result` mediumtext COLLATE utf8mb4_general_ci COMMENT '响应内容',
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
INSERT INTO `sys_menu` VALUES (100000, '首页', '工作台首页', 'DashboardOutlined', 'home', NULL, 1, '2021-07-16 16:24:24', 0, 1);
INSERT INTO `sys_menu` VALUES (100001, '列表展示', '列表展示', 'TableOutlined', 'table', NULL, 2, '2021-07-16 16:25:30', 0, 1);
INSERT INTO `sys_menu` VALUES (100002, '基础列表', '基础列表', NULL, 'basicTable', 100001, 1, '2021-07-16 16:26:59', 0, 1);
INSERT INTO `sys_menu` VALUES (100003, '高级列表', '高级列表', NULL, 'advanceTable', 100001, 2, '2021-07-16 16:27:23', 0, 1);
INSERT INTO `sys_menu` VALUES (100004, '卡片列表', '卡片列表', NULL, 'cardTable', 100001, 3, '2021-07-16 16:27:32', 0, 1);
INSERT INTO `sys_menu` VALUES (100005, '表单呈现', '表单呈现', 'FormOutlined', 'form', NULL, 3, '2021-07-16 16:27:59', 0, 1);
INSERT INTO `sys_menu` VALUES (100006, '基础表单', '基础表单', NULL, 'basicForm', 100005, 1, '2021-07-16 16:28:21', 0, 1);
INSERT INTO `sys_menu` VALUES (100007, '分步表单', '分步表单', NULL, 'stepForm', 100005, 2, '2021-07-16 16:28:51', 0, 1);
INSERT INTO `sys_menu` VALUES (100008, '高级表单', '高级表单', NULL, 'advanceForm', 100005, 3, '2021-07-16 16:29:06', 0, 1);
INSERT INTO `sys_menu` VALUES (100009, '动态表单', '动态表单', NULL, 'dynamicForm', 100005, 4, '2021-07-16 16:29:22', 0, 1);
INSERT INTO `sys_menu` VALUES (100010, '系统管理', '系统管理', 'SettingOutlined', 'system', NULL, 4, '2021-07-16 16:29:47', 0, 1);
INSERT INTO `sys_menu` VALUES (100011, '组织管理', '组织管理', NULL, 'sysdept', 100010, 1, '2021-07-16 16:30:08', 0, 1);
INSERT INTO `sys_menu` VALUES (100012, '用户管理', '用户管理', NULL, 'sysuser', 100010, 2, '2021-07-16 16:30:20', 0, 1);
INSERT INTO `sys_menu` VALUES (100013, '角色管理', '角色管理', NULL, 'sysrole', 100010, 3, '2021-07-16 16:30:35', 0, 1);
INSERT INTO `sys_menu` VALUES (100014, '菜单管理', '菜单管理', NULL, 'sysmenu', 100010, 4, '2021-07-16 16:30:47', 0, 1);
INSERT INTO `sys_menu` VALUES (100015, '数据字典', '数据字典', NULL, 'systype', 100010, 5, '2021-07-16 16:31:00', 0, 1);
INSERT INTO `sys_menu` VALUES (100016, '消息管理', '消息管理', NULL, 'sysmessage', 100010, 6, '2021-07-16 16:31:14', 0, 1);
INSERT INTO `sys_menu` VALUES (100017, '文件管理', '文件管理', NULL, 'sysfile', 100010, 7, '2021-07-16 16:31:26', 0, 1);
INSERT INTO `sys_menu` VALUES (100018, '日志管理', '日志管理', NULL, 'syslog', 100010, 8, '2021-07-16 16:31:40', 0, 1);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统菜单动作资源主题';

-- ----------------------------
-- Records of sys_menu_topic
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `topic_id` int(6) NOT NULL COMMENT '消息类别id',
  `domain` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '消息域',
  `content` varchar(1000) COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息主体',
  `msg_json` varchar(4000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '消息其他参数',
  `msg_type` int(2) NOT NULL DEFAULT '1' COMMENT '消息类型，1文字，2图片，3文件',
  `create_time` int(10) NOT NULL COMMENT '创建时间',
  `user_id` int(6) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户消息';

-- ----------------------------
-- Records of sys_message
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_message_topic
-- ----------------------------
DROP TABLE IF EXISTS `sys_message_topic`;
CREATE TABLE `sys_message_topic` (
  `id` int(6) NOT NULL COMMENT '序列ID',
  `type` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息类别',
  `avatar` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'github-circle-fill' COMMENT '消息头像',
  `color` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '#1890ff' COMMENT '头像颜色',
  `title` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息名称',
  `route` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '路由地址',
  `user_id` int(6) NOT NULL COMMENT '用户ID',
  `read_flag` int(2) NOT NULL DEFAULT '0' COMMENT '已读状态：0未读，1已读',
  `create_time` datetime NOT NULL COMMENT '消息类别创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户消息类别';

-- ----------------------------
-- Records of sys_message_topic
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
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (100, '超级管理员', '超级管理员', '2021-07-16 16:46:36', 0);
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
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色资源关系表';

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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色菜单关系表';

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
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统数据字典表';

-- ----------------------------
-- Records of sys_type
-- ----------------------------
BEGIN;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统码表';

-- ----------------------------
-- Records of sys_type_code
-- ----------------------------
BEGIN;
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
  `extra_info` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '平台类型portal门户 dashboard 管理后台',
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
INSERT INTO `sys_user` VALUES (100000, 'super', '13821220101', '管理员', '$2a$10$hNMnqKOWn66d89Rqh0Tm1uSvbJ1Zt8/kfogJhjLFDKlQK0.nb6Kj.', '', NULL, 'default_head.png', 1000, '2021-07-16 16:42:09', '2021-07-30 15:12:36', NULL, 1, 'dashboard');
INSERT INTO `sys_user` VALUES (100001, 'atom', '18630855886', 'Atom.Z', '$2a$10$cMuoPeLN25MmOko2qMHHUuouj8l/2gGrWeMU/ikW/E.felt/9FWPC', NULL, NULL, 'default_head.png', 1003, '2021-07-29 14:50:02', NULL, NULL, 1, 'dashboard');
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
