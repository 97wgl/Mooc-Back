/*
 Navicat Premium Data Transfer

 Source Server         : mooc
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : 111.230.240.26:3306
 Source Schema         : remote_mooc

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 28/05/2019 14:37:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT ' ' COMMENT '名字',
  `pwd` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (2, 'wgl', '81dc9bdb52d04dc20036dbd8313ed055');
INSERT INTO `admin` VALUES (3, 'xxz', '81dc9bdb52d04dc20036dbd8313ed055');
INSERT INTO `admin` VALUES (4, 'psw', '81dc9bdb52d04dc20036dbd8313ed055');
INSERT INTO `admin` VALUES (5, 'yyy', '81dc9bdb52d04dc20036dbd8313ed055');
INSERT INTO `admin` VALUES (6, 'cmy', '81dc9bdb52d04dc20036dbd8313ed055');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `course_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课程名称',
  `classify` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类别',
  `tea_id` int(11) NOT NULL COMMENT '讲师编号',
  `level` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课程级别：1-初级，2-中级，3-高级',
  `time` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '时长',
  `brief` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程描述',
  `study_count` int(11) NOT NULL DEFAULT 0 COMMENT '学习人数',
  `weight` int(11) NOT NULL DEFAULT 1 COMMENT '权重',
  `picture` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程展示图片',
  `publish_time` datetime(0) NOT NULL COMMENT '课程发布时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '审核状态:0-审核未通过,1-审核通过',
  PRIMARY KEY (`course_id`) USING BTREE,
  INDEX `FK_course_teacher_course_owner`(`tea_id`) USING BTREE,
  CONSTRAINT `FK_course_teacher_course_owner` FOREIGN KEY (`tea_id`) REFERENCES `teacher` (`tea_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'AngularJS仿拉勾网WebApp', '后端开发', 1, '2', '7200', 'AngularJS仿拉勾网WebApp  开发移动端单页应用', 0, 1, '/image/c2.jpg', '2019-05-28 14:32:22', '1');

-- ----------------------------
-- Table structure for course_comment
-- ----------------------------
DROP TABLE IF EXISTS `course_comment`;
CREATE TABLE `course_comment`  (
  `id` int(11) NOT NULL COMMENT '留言编号',
  `course_id` int(11) NOT NULL COMMENT '课程编号',
  `section_id` int(11) NOT NULL COMMENT '章节编号',
  `u_id` int(11) NOT NULL COMMENT '用户编号',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '留言内容',
  `create_time` datetime(0) NOT NULL COMMENT '留言时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '留言状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_COURSE_COMMENT_USER_fk1`(`u_id`) USING BTREE,
  INDEX `FK_COURSE_COMMENT_COURSE_fk1`(`course_id`) USING BTREE,
  CONSTRAINT `FK_COURSE_COMMENT_COURSE_fk1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_COURSE_COMMENT_USER_fk1` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_comment_reply
-- ----------------------------
DROP TABLE IF EXISTS `course_comment_reply`;
CREATE TABLE `course_comment_reply`  (
  `id` int(11) NOT NULL COMMENT '留言回复编号',
  `comment_id` int(11) NOT NULL COMMENT '留言编号',
  `reply_u_id` int(11) NOT NULL COMMENT '回复者编号',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复内容',
  `create_time` datetime(0) NOT NULL COMMENT '回复时间',
  `reply_to_u_id` int(11) NOT NULL COMMENT '回复谁',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `course_evaluation`;
CREATE TABLE `course_evaluation`  (
  `id` int(11) NOT NULL COMMENT '评价编号',
  `course_id` int(11) NOT NULL COMMENT '课程编号',
  `u_id` int(11) NOT NULL COMMENT '学生编号',
  `time` datetime(0) NOT NULL COMMENT '评价时间',
  `score` tinyint(4) NOT NULL COMMENT '评分：1~5',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评价内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '评论状态：1-有效，2-无效',
  `is_reply` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '讲师是否回复：1-是，0-否',
  `tea_id` int(11) NULL DEFAULT NULL COMMENT '讲师编号',
  `reply_time` datetime(0) NULL DEFAULT NULL COMMENT '回复时间',
  `reply_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '讲师回复内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_section
-- ----------------------------
DROP TABLE IF EXISTS `course_section`;
CREATE TABLE `course_section`  (
  `course_id` int(11) NOT NULL COMMENT '课程编号',
  `section_id` int(11) NOT NULL COMMENT '章节编号',
  `parent_id` int(11) NOT NULL COMMENT '章节父级编号，根章节编号为0',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '章节名',
  `video_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频路径',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `time` int(11) NOT NULL COMMENT '时长，单位秒(s)',
  INDEX `FK_COURSE_SECTION_COURSE_fk1`(`course_id`) USING BTREE,
  CONSTRAINT `FK_COURSE_SECTION_COURSE_fk1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for study_record
-- ----------------------------
DROP TABLE IF EXISTS `study_record`;
CREATE TABLE `study_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学习记录编号',
  `u_id` int(11) NOT NULL COMMENT '学生编号',
  `course_id` int(11) NOT NULL COMMENT '课程编号',
  `section_id` int(11) NOT NULL COMMENT '章节编号',
  `lates_time` datetime(0) NOT NULL COMMENT '最近学习时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_STUDY_RECORD_USER_fk1`(`u_id`) USING BTREE,
  INDEX `FK_STUDY_RECORD_COURSE_fk1`(`course_id`) USING BTREE,
  CONSTRAINT `FK_STUDY_RECORD_COURSE_fk1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_STUDY_RECORD_USER_fk1` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `tea_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '讲师编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名字',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `pwd` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `tel` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `head_img` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '/image/headimg.jpg' COMMENT '头像',
  `orgnization` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `position` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '介绍',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '教师资格审核状态：1-审核通过，2-未通过',
  PRIMARY KEY (`tea_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, '覃老师', '男', '81dc9bdb52d04dc20036dbd8313ed055', '1', '1', '/image/headimg.jpg', '三峡大学', '讲师', NULL, '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `u_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `tel` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `head_img` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '/static/img/head_img.jpg' COMMENT '头像路径',
  `pwd` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码:md5加密',
  `regist_date` datetime(0) NOT NULL COMMENT '注册时间',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '个人签名',
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'cmy', NULL, NULL, 'seven1996@vip.qq.com', '/static/img/head_img.jpg', '81DC9BDB52D04DC20036DBD8313ED055', '2019-05-28 13:19:27', NULL);

SET FOREIGN_KEY_CHECKS = 1;
