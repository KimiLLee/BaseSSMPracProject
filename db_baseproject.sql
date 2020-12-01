/*
 Navicat Premium Data Transfer

 Source Server         : MYSQLConnect
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : db_baseproject

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 01/12/2020 12:10:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) NOT NULL,
  `menuId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `roleId`(`roleId`) USING BTREE,
  INDEX `menuId`(`menuId`) USING BTREE,
  CONSTRAINT `authority_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `authority_ibfk_2` FOREIGN KEY (`menuId`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 264 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES (237, 3, 8);
INSERT INTO `authority` VALUES (238, 3, 10);
INSERT INTO `authority` VALUES (239, 3, 17);
INSERT INTO `authority` VALUES (240, 3, 18);
INSERT INTO `authority` VALUES (241, 3, 24);
INSERT INTO `authority` VALUES (242, 3, 25);
INSERT INTO `authority` VALUES (243, 3, 11);
INSERT INTO `authority` VALUES (244, 3, 26);
INSERT INTO `authority` VALUES (245, 3, 27);
INSERT INTO `authority` VALUES (246, 3, 28);
INSERT INTO `authority` VALUES (247, 3, 37);
INSERT INTO `authority` VALUES (248, 3, 38);
INSERT INTO `authority` VALUES (249, 3, 15);
INSERT INTO `authority` VALUES (250, 3, 16);
INSERT INTO `authority` VALUES (251, 3, 29);
INSERT INTO `authority` VALUES (252, 3, 30);
INSERT INTO `authority` VALUES (253, 3, 31);
INSERT INTO `authority` VALUES (254, 3, 33);
INSERT INTO `authority` VALUES (255, 3, 34);
INSERT INTO `authority` VALUES (256, 3, 35);
INSERT INTO `authority` VALUES (257, 3, 36);
INSERT INTO `authority` VALUES (258, 4, 37);
INSERT INTO `authority` VALUES (259, 4, 38);
INSERT INTO `authority` VALUES (260, 4, 30);
INSERT INTO `authority` VALUES (261, 4, 8);
INSERT INTO `authority` VALUES (262, 4, 15);
INSERT INTO `authority` VALUES (263, 4, 16);

-- ----------------------------
-- Table structure for c3p0testtable
-- ----------------------------
DROP TABLE IF EXISTS `c3p0testtable`;
CREATE TABLE `c3p0testtable`  (
  `a` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of c3p0testtable
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (36, '{超级管理员}组用户{qwe}登陆成功', '2020-11-30 21:48:54');
INSERT INTO `log` VALUES (37, '{普通用户}组用户{zxc}登陆成功', '2020-11-30 21:54:49');
INSERT INTO `log` VALUES (38, '{超级管理员}组用户{asd}登陆成功', '2020-11-30 21:56:10');
INSERT INTO `log` VALUES (39, '{超级管理员}组用户{qwe}登陆成功', '2020-12-01 11:06:45');
INSERT INTO `log` VALUES (40, '{超级管理员}组用户{qwe}登陆成功', '2020-12-01 11:29:36');
INSERT INTO `log` VALUES (41, '{超级管理员}组用户{qwe}登陆成功', '2020-12-01 11:32:24');
INSERT INTO `log` VALUES (42, '{超级管理员}组用户{qwe}登陆成功', '2020-12-01 12:04:39');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL DEFAULT 0,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (8, 0, '系统设置', '', 'icon-application-xp-terminal');
INSERT INTO `menu` VALUES (10, 8, '菜单管理', '../admin/menu/list', 'icon-arrow-divide');
INSERT INTO `menu` VALUES (11, 8, '角色管理', '../admin/role/list', 'icon-bricks');
INSERT INTO `menu` VALUES (15, 0, '用户管理', '', 'icon-group');
INSERT INTO `menu` VALUES (16, 15, '用户列表', '../admin/user/list', 'icon-group-add');
INSERT INTO `menu` VALUES (17, 10, '添加', 'openAdd()', 'icon-add');
INSERT INTO `menu` VALUES (18, 10, '修改', 'openEdit()', 'icon-edit');
INSERT INTO `menu` VALUES (24, 10, '删除', 'remove()', 'icon-remove');
INSERT INTO `menu` VALUES (25, 10, '添加按钮', 'openAddMenu()', 'icon-add1');
INSERT INTO `menu` VALUES (26, 11, '添加', 'openAdd()', 'icon-add');
INSERT INTO `menu` VALUES (27, 11, '修改', 'openEdit()', 'icon-edit');
INSERT INTO `menu` VALUES (28, 11, '删除', 'remove()', 'icon-remove');
INSERT INTO `menu` VALUES (29, 16, '添加', 'openAdd()', 'icon-add');
INSERT INTO `menu` VALUES (30, 16, '修改', 'openEdit()', 'icon-edit');
INSERT INTO `menu` VALUES (31, 16, '删除', 'remove()', 'icon-remove');
INSERT INTO `menu` VALUES (33, 0, '系统日志', '', 'icon-table-cell');
INSERT INTO `menu` VALUES (34, 33, '日志列表', '../admin/log/list', 'icon-table-cell');
INSERT INTO `menu` VALUES (35, 34, '添加日志', 'openAdd()', 'icon-add');
INSERT INTO `menu` VALUES (36, 34, '删除日志', 'remove()', 'icon-remove');
INSERT INTO `menu` VALUES (37, 8, '修改密码', 'edit_password', 'icon-lock-edit');
INSERT INTO `menu` VALUES (38, 37, '修改密码', 'openAdd()', 'icon-lock-edit');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (3, '超级管理员', '拥有全部权限');
INSERT INTO `role` VALUES (4, '普通用户', '拥有一般权限');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `roleId` int(11) NOT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` int(1) NULL DEFAULT 0,
  `age` int(3) NULL DEFAULT 0,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `roleId`(`roleId`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'qwe', 'qwe', 3, '/BaseProjectSSM/resources/upload/1606393580327.jpg', 1, 48, '倒霉大街');
INSERT INTO `user` VALUES (18, 'asd', 'asd', 3, '/BaseProjectSSM/resources/upload/1606308190136.jpg', 2, 34, '');
INSERT INTO `user` VALUES (19, 'zxc', 'zxc', 4, '/BaseProjectSSM/resources/upload/1606394287504.jpg', 1, NULL, '');
INSERT INTO `user` VALUES (20, '111', '111', 3, '/BaseProjectSSM/resources/admin/easyui/images/user_photo.jpg', 1, 23, '');

SET FOREIGN_KEY_CHECKS = 1;
