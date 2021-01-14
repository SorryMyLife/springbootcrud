/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 192.168.198.130:3306
 Source Schema         : crudcrm

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 14/01/2021 11:09:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for crm_system_code
-- ----------------------------
DROP TABLE IF EXISTS `crm_system_code`;
CREATE TABLE `crm_system_code`  (
  `cid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`, `cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of crm_system_code
-- ----------------------------
INSERT INTO `crm_system_code` VALUES ('9C0HGDQ20DNE2M1LQ4UP9ONM5H', 'wP61Tv', 1);
INSERT INTO `crm_system_code` VALUES ('6570HV5417LHCHLHU6FQOBGS1M', '1EHdja', 2);
INSERT INTO `crm_system_code` VALUES ('R5ABVOTLG6EDK7H7RS189A2FGT', 'RUrgwD', 3);
INSERT INTO `crm_system_code` VALUES ('55SAULG4N0R85GG4NOC703MLB5', '6uHu5K', 4);
INSERT INTO `crm_system_code` VALUES ('PT8DGKHGCTSG0RFHT49GV9ATE6', '2TmZkW', 5);
INSERT INTO `crm_system_code` VALUES ('E7MT4BIPGUPM96LPD10OOF6QFS', 'h28If3', 6);
INSERT INTO `crm_system_code` VALUES ('N5S89UURMSQ8FGN0VSSK2BKM1R', 'Fbk5uj', 7);
INSERT INTO `crm_system_code` VALUES ('8MU2DF09FNM8AOLR3IBS44PL90', 'RRLpHz', 8);
INSERT INTO `crm_system_code` VALUES ('8L71HIL61IKGVIU7G9IUMG64AK', 'FiPyt4', 9);
INSERT INTO `crm_system_code` VALUES ('LL7PH1C0LBQM421TC7NAVAUP30', 'FLqUoi', 10);
INSERT INTO `crm_system_code` VALUES ('O3K1GS6GS5BPC1TOF3KO0VI9I7', 'FiUTmu', 11);
INSERT INTO `crm_system_code` VALUES ('9OEOM4I4U2TO51SV1FQ4M2GTTT', 'jb49Fm', 12);
INSERT INTO `crm_system_code` VALUES ('O0T0MMM2IU56E80G1RNOHLQOOM', 'b6N8UY', 13);

-- ----------------------------
-- Table structure for crm_system_dept
-- ----------------------------
DROP TABLE IF EXISTS `crm_system_dept`;
CREATE TABLE `crm_system_dept`  (
  `deptid` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司id，唯一的',
  `deptname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司名称',
  `edittime` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `edituser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '谁修改的',
  `addtime` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `adduser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '谁添加的',
  PRIMARY KEY (`deptid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of crm_system_dept
-- ----------------------------
INSERT INTO `crm_system_dept` VALUES ('00067586794504715361159', '老王科技股份制有限公司9', '2021-01-13 16:26:08', 'root', '2021-01-13 16:26:08', 'root');
INSERT INTO `crm_system_dept` VALUES ('24195564321192112762227', '老王科技股份制有限公司4', '2021-01-13 16:26:08', 'root', '2021-01-13 16:26:08', 'root');
INSERT INTO `crm_system_dept` VALUES ('33097264112709389656028', '老王科技股份制有限公司6', '2021-01-13 16:26:08', 'root', '2021-01-13 16:26:08', 'root');
INSERT INTO `crm_system_dept` VALUES ('5555', '尔特人', '2021-01-13 17:29:31', 'root', '2021-01-13 17:29:31', 'root');
INSERT INTO `crm_system_dept` VALUES ('59240944596217419902944', '老王科技股份制有限公司7', '2021-01-13 16:26:08', 'root', '2021-01-13 16:26:08', 'root');
INSERT INTO `crm_system_dept` VALUES ('65124792721988915880543', '老王科技股份制有限公司5', '2021-01-13 16:26:08', 'root', '2021-01-13 16:26:08', 'root');
INSERT INTO `crm_system_dept` VALUES ('66445826798969330291572', '老王科技股份制有限公司2', '2021-01-13 16:26:08', 'root', '2021-01-13 16:26:08', 'root');
INSERT INTO `crm_system_dept` VALUES ('77316571534680356487415', '老王科技股份制有限公司3', '2021-01-13 16:26:08', 'root', '2021-01-13 16:26:08', 'root');
INSERT INTO `crm_system_dept` VALUES ('85255945061340863470764', '老王科技股份制有限公司0', '2021-01-13 16:26:08', 'root', '2021-01-13 16:26:08', 'root');
INSERT INTO `crm_system_dept` VALUES ('86711656808397763331740', '老王科技股份制有限公司8', '2021-01-13 16:26:08', 'root', '2021-01-13 16:26:08', 'root');
INSERT INTO `crm_system_dept` VALUES ('90309212233944331442444', '老王科技股份制有限公司1', '2021-01-13 16:26:08', 'root', '2021-01-13 16:26:08', 'root');

-- ----------------------------
-- Table structure for crm_system_role
-- ----------------------------
DROP TABLE IF EXISTS `crm_system_role`;
CREATE TABLE `crm_system_role`  (
  `roleid` int(12) NOT NULL COMMENT '用户等级，不能为空',
  `userrealname` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户等级名称',
  `edittime` datetime(0) NULL DEFAULT NULL COMMENT '编辑时间',
  `edituser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编辑用户',
  `addtime` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `adduser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '添加用户'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of crm_system_role
-- ----------------------------
INSERT INTO `crm_system_role` VALUES (0, 'root', '2021-01-13 16:26:08', '0', '2021-01-13 16:26:08', '0');
INSERT INTO `crm_system_role` VALUES (1, '系统管理员', '2021-01-13 16:26:08', '0', '2021-01-13 16:26:08', '0');
INSERT INTO `crm_system_role` VALUES (2, '二级系统管理员', '2021-01-13 16:26:08', '0', '2021-01-13 16:26:08', '0');
INSERT INTO `crm_system_role` VALUES (3, '三级系统管理员', '2021-01-13 16:26:08', '0', '2021-01-13 16:26:08', '0');
INSERT INTO `crm_system_role` VALUES (4, '用户', '2021-01-13 16:26:08', '0', '2021-01-13 16:26:08', '0');

-- ----------------------------
-- Table structure for crm_system_user
-- ----------------------------
DROP TABLE IF EXISTS `crm_system_user`;
CREATE TABLE `crm_system_user`  (
  `userid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id，唯一性',
  `username` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名字',
  `usersex` tinyint(2) NULL DEFAULT NULL COMMENT '用户性别',
  `useremail` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `usermobile` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户联系方式',
  `userpassword` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of crm_system_user
-- ----------------------------
INSERT INTO `crm_system_user` VALUES ('20210113162607896', '老王-0号', 0, '227089@qq.com', '03545341590', '4968226956397666');
INSERT INTO `crm_system_user` VALUES ('20210113162608135', '老王-1号', 1, '06844@qq.com', '17807464523', '6405566738810027');
INSERT INTO `crm_system_user` VALUES ('20210113162608150', '老王-2号', 1, '1830112@qq.com', '55959120762', '6524878019074997');
INSERT INTO `crm_system_user` VALUES ('20210113162608165', '老王-3号', 1, '9337901642@qq.com', '01296551108', '1409467815272206');
INSERT INTO `crm_system_user` VALUES ('20210113162608184', '老王-4号', 1, '00989468@qq.com', '16320354962', '5271313607493390');
INSERT INTO `crm_system_user` VALUES ('20210113162608199', '老王-5号', 1, '9688125654@qq.com', '04465008908', '9504622582779644');
INSERT INTO `crm_system_user` VALUES ('20210113162608214', '老王-6号', 1, '57301334@qq.com', '01344525601', '4759722595251965');
INSERT INTO `crm_system_user` VALUES ('20210113162608228', '老王-7号', 1, '7469973550@qq.com', '58334109586', '6318114001761226');
INSERT INTO `crm_system_user` VALUES ('20210113162608245', '老王-8号', 1, '6071263@qq.com', '34345105177', '7001962127298452');
INSERT INTO `crm_system_user` VALUES ('20210113162608260', '老王-9号', 1, '82556@qq.com', '96346807363', '3624798613384609');
INSERT INTO `crm_system_user` VALUES ('root', 'root', 1, '4234234', '234234234243', 'root');

-- ----------------------------
-- Table structure for crm_system_user_manage
-- ----------------------------
DROP TABLE IF EXISTS `crm_system_user_manage`;
CREATE TABLE `crm_system_user_manage`  (
  `userId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id,不能为空',
  `adduser` int(12) NULL DEFAULT NULL COMMENT '添加用户的上级,如果为空则为系统',
  `edituser` int(12) NULL DEFAULT NULL COMMENT '添加用户的上级',
  `addtime` datetime(0) NULL DEFAULT NULL COMMENT '添加用户的时间',
  `edittime` datetime(0) NULL DEFAULT NULL COMMENT '修改用户的时间',
  `islock` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否锁定,(Y/N,是/否)',
  `deptid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户所属公司',
  `roleid` int(12) NULL DEFAULT NULL COMMENT '用户等级(如果为空则为普通用户)',
  `isonline` tinyint(1) NULL DEFAULT NULL COMMENT '用户是否在线'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of crm_system_user_manage
-- ----------------------------
INSERT INTO `crm_system_user_manage` VALUES ('20210113162607896', 2, 0, '2021-01-13 16:26:17', '2021-01-13 16:26:17', '1', '33097264112709389656028', 2, 0);
INSERT INTO `crm_system_user_manage` VALUES ('20210113162608135', 1, 0, '2021-01-13 16:26:17', '2021-01-13 16:26:17', '0', '86711656808397763331740', 3, 0);
INSERT INTO `crm_system_user_manage` VALUES ('20210113162608150', 3, 0, '2021-01-13 16:26:17', '2021-01-13 16:26:17', '1', '59240944596217419902944', 3, 0);
INSERT INTO `crm_system_user_manage` VALUES ('20210113162608165', 3, 0, '2021-01-13 16:26:17', '2021-01-13 16:26:17', '1', '66445826798969330291572', 4, 0);
INSERT INTO `crm_system_user_manage` VALUES ('20210113162608184', 0, 0, '2021-01-13 16:26:17', '2021-01-13 16:26:17', '1', '66445826798969330291572', 1, 0);
INSERT INTO `crm_system_user_manage` VALUES ('20210113162608199', 2, 0, '2021-01-13 16:26:17', '2021-01-13 16:26:17', '1', '24195564321192112762227', 3, 0);
INSERT INTO `crm_system_user_manage` VALUES ('20210113162608214', 3, 0, '2021-01-13 16:26:17', '2021-01-13 16:26:17', '0', '85255945061340863470764', 4, 0);
INSERT INTO `crm_system_user_manage` VALUES ('20210113162608228', 1, 0, '2021-01-13 16:26:17', '2021-01-13 16:26:17', '1', '33097264112709389656028', 4, 0);
INSERT INTO `crm_system_user_manage` VALUES ('20210113162608245', 3, 0, '2021-01-13 16:26:17', '2021-01-13 16:26:17', '1', '59240944596217419902944', 2, 0);
INSERT INTO `crm_system_user_manage` VALUES ('20210113162608260', 1, 0, '2021-01-13 16:26:17', '2021-01-13 16:26:17', '0', '24195564321192112762227', 0, 0);
INSERT INTO `crm_system_user_manage` VALUES ('root', 0, 0, '2021-01-13 16:43:31', '2021-01-13 17:21:22', '0', '00067586794504715361159', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
