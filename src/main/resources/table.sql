/*
Navicat MySQL Data Transfer

Source Server         : 114.255.162.72
Source Server Version : 50722
Source Host           : 114.255.162.72:3306
Source Database       : admindemo

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-07-10 14:56:56
*/


SET FOREIGN_KEY_CHECKS=0;
DROP DATABASE IF EXISTS `admindemo`;
CREATE DATABASE  `admindemo` DEFAULT CHARACTER SET utf8 ;
USE `admindemo`;
-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `uid` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '1',
  `salt` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `is_system` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `unique_username` (`username`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('0fd6fffaca664fc19dd97d62ad7dbbfe', 'manage', '9bc73548e9024a58781a8905d07ccdf9', '1', '1ed6d86fd69f94bb4d7cd2269f8e2c05', '0', '2018-07-06 10:50:26', '2018-07-06 10:50:26');
INSERT INTO `admin` VALUES ('ad313d38fe9447ce863fe8584743a010', 'admin', 'c5941c5f3bc693a75e6e863bd2c55ce3', '1', '1ab6d62faa91ae7deec76d6f13ef1600', '1', '2016-12-06 11:16:51', '2017-05-11 13:59:25');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `admin_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`admin_id`,`role_id`),
  KEY `admin_role_foreign` (`role_id`) USING BTREE,
  CONSTRAINT `fk_ref_admin` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`uid`),
  CONSTRAINT `fk_ref_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('0fd6fffaca664fc19dd97d62ad7dbbfe', 'cbe8356d64a8433cb5dad5c7fccf8dce');

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` char(20) NOT NULL,
  `device_name` varchar(20) NOT NULL,
  `device_brand` varchar(20) NOT NULL COMMENT '设备品牌',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('2', '23165484', '机器一', '联想', '2018-07-09 17:51:20', '2018-07-09 17:51:20');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `log_id` varchar(32) NOT NULL,
  `log_user` varchar(32) DEFAULT NULL,
  `log_time` datetime DEFAULT NULL,
  `log_ip` varchar(15) DEFAULT NULL,
  `log_action` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('01794fc4e1b44e8591c211122db65913', 'admin', '2018-07-09 18:23:11', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0199190b39394daf81f4851155b25502', 'manage', '2018-07-06 14:19:46', '127.0.0.1', '');
INSERT INTO `log` VALUES ('01b2f16103df476ea42ef2040ca13561', 'admin', '2018-07-09 16:31:24', '127.0.0.1', '');
INSERT INTO `log` VALUES ('024021f259444cbfbee7b40a2384d43b', 'admin', '2017-03-09 17:23:54', '127.0.0.1', '');
INSERT INTO `log` VALUES ('054d85212ecd4c7a9830075f1a5d764b', 'admin', '2017-03-31 09:54:13', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0611975d47d4452580a53c2c4a958ede', 'admin', '2018-07-10 13:57:46', '127.0.0.1', '');
INSERT INTO `log` VALUES ('06cabfb1ddea490d92375327f7fd36a5', 'admin', '2018-07-04 21:03:12', '120.138.40.242', '');
INSERT INTO `log` VALUES ('0912fa1c7f87460b9ff9fed5c515e29b', 'admin', '2017-05-05 13:47:14', '127.0.0.1', '');
INSERT INTO `log` VALUES ('091e50012ab2401386fa7d875374a04b', 'admin', '2018-07-09 16:23:09', '127.0.0.1', '');
INSERT INTO `log` VALUES ('09d3ea3e99574840a285fcf15642bdee', 'admin', '2017-05-09 16:31:40', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0b6a08723fae4ae2921886ec0a9c4dbc', 'admin', '2017-05-09 11:52:13', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0bc44c925bd0468c8fd7781d07e90b1d', 'admin', '2017-03-28 13:38:27', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0db331d133aa4782a7483a6a0d3507a0', 'admin', '2018-07-06 16:58:37', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0dc10963972d408dad7ee5dd94ad9f07', 'admin', '2017-05-24 14:55:17', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0e13550047de4bb191141b7946cca7c8', 'admin', '2018-07-09 21:28:24', '111.199.187.51', '');
INSERT INTO `log` VALUES ('0e98ebc076ba46a592734e1d20f9a245', 'admin', '2018-07-06 17:36:05', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0eb5488cc4744fe69d2c8b02faadf10d', 'admin', '2018-07-09 16:55:07', '127.0.0.1', '');
INSERT INTO `log` VALUES ('0ffd572add7647f5aa214a7576b111a9', 'admin', '2018-07-10 14:34:14', '127.0.0.1', '');
INSERT INTO `log` VALUES ('1154167ecd174fc990c283c25d66000b', 'admin', '2017-05-11 15:28:38', '127.0.0.1', '');
INSERT INTO `log` VALUES ('17583cb911c2456bad36b2fb19ff4f13', 'admin', '2018-07-06 16:03:50', '127.0.0.1', '');
INSERT INTO `log` VALUES ('198f4e96233d47c18366babd8412553d', 'manage', '2018-07-06 16:04:38', '127.0.0.1', '');
INSERT INTO `log` VALUES ('1a73e947096a4b1d944d718b58a996ff', 'admin', '2018-07-10 12:22:09', '127.0.0.1', '');
INSERT INTO `log` VALUES ('1c98d09be6134407bc040f9f90fc3670', 'admin', '2018-07-04 22:35:51', '111.199.185.154', '');
INSERT INTO `log` VALUES ('20789331aa7b49a78e60289222f71d78', 'admin', '2018-07-04 17:20:53', '111.205.43.253', '');
INSERT INTO `log` VALUES ('24255fdc7f7b42a19ed514d3c3608fa6', 'manage', '2018-07-09 13:57:04', '127.0.0.1', '');
INSERT INTO `log` VALUES ('28d3af9dae954d99824b3a2f5d48d2e7', 'admin', '2018-07-10 14:25:34', '127.0.0.1', '');
INSERT INTO `log` VALUES ('2fca78a92f5149b0b81c3066d103b2be', 'admin', '2018-07-09 18:05:32', '127.0.0.1', '');
INSERT INTO `log` VALUES ('35493d82d5de485ebd36b23b1b147adb', 'admin', '2018-07-09 17:32:15', '127.0.0.1', '');
INSERT INTO `log` VALUES ('3fb575604c8546579c6891d4a5e3ccb0', 'manage', '2018-07-06 15:45:41', '127.0.0.1', '');
INSERT INTO `log` VALUES ('4325541d267440fd80b2a61f83516f77', 'admin', '2018-07-09 17:01:10', '111.205.43.246', '');
INSERT INTO `log` VALUES ('44bbc2cb0fb246b7ae461f8b88afaef2', 'admin', '2018-07-09 16:17:53', '111.205.43.246', '');
INSERT INTO `log` VALUES ('463a849f25c245d39335c9204361f141', 'admin', '2018-07-10 14:38:33', '127.0.0.1', '');
INSERT INTO `log` VALUES ('46e4fe1b131a46be80853707e893a70b', 'admin', '2018-07-06 10:52:21', '127.0.0.1', '');
INSERT INTO `log` VALUES ('4706782a3c524349bc44f74400b8b4d8', 'admin', '2018-07-09 13:55:01', '210.12.5.210', '');
INSERT INTO `log` VALUES ('47bb0afe3052419bba5f9f177b655b1a', 'admin', '2018-07-10 12:15:31', '127.0.0.1', '');
INSERT INTO `log` VALUES ('47d8b1ddb2b048398e98f336ed565929', 'admin', '2018-07-09 17:22:17', '127.0.0.1', '');
INSERT INTO `log` VALUES ('4b489a74308344bd80b3ab9e96c4c8b0', 'admin', '2018-07-04 18:00:16', '111.205.43.253', '');
INSERT INTO `log` VALUES ('51860f90d0724dbf80bb205ebe1c006d', 'admin', '2018-07-06 17:41:56', '127.0.0.1', '');
INSERT INTO `log` VALUES ('5653e640f3ab4653b9b9038bc2537ed1', 'manage', '2018-07-06 10:53:42', '127.0.0.1', '');
INSERT INTO `log` VALUES ('585d7e8ca8ea4b1e8a1e50046dbfa29b', 'admin', '2018-07-10 14:41:17', '127.0.0.1', '');
INSERT INTO `log` VALUES ('5a2450d10e144ccab6fe22bdad2c1adb', 'manage', '2018-07-09 17:08:29', '111.205.43.246', '');
INSERT INTO `log` VALUES ('5a9e97813263464283476f27599ebb10', 'admin', '2018-07-06 10:48:43', '127.0.0.1', '');
INSERT INTO `log` VALUES ('5d0a5393fcfc430398f4d6e0c45e2696', 'admin', '2018-07-09 17:51:15', '127.0.0.1', '');
INSERT INTO `log` VALUES ('5dc1a454f4ac4017a50c08f182b96a8c', 'admin', '2018-07-09 17:25:49', '127.0.0.1', '');
INSERT INTO `log` VALUES ('634f3ccf965d4577a3ebf24d08b33bbd', 'admin', '2018-07-06 17:37:13', '127.0.0.1', '');
INSERT INTO `log` VALUES ('6435aba609b94059a34789c06721187c', 'admin', '2018-07-06 10:54:32', '127.0.0.1', '');
INSERT INTO `log` VALUES ('651ca84983734a0bbf145d38f65c772a', 'admin', '2018-07-10 14:21:16', '127.0.0.1', '');
INSERT INTO `log` VALUES ('69d69171f19a42a4baf57a8025119403', 'admin', '2018-07-10 14:44:54', '127.0.0.1', '');
INSERT INTO `log` VALUES ('6ac10fd154214c77a498ec9043b054b8', 'admin', '2018-07-10 09:33:57', '111.205.43.237', '');
INSERT INTO `log` VALUES ('6bde39b49a8d4858b0a08f6a98c795cf', 'manage', '2018-07-09 17:07:00', '111.205.43.246', '');
INSERT INTO `log` VALUES ('72507bc0cdc046ccaa8544cc5156afd8', 'manage', '2018-07-06 10:51:32', '127.0.0.1', '');
INSERT INTO `log` VALUES ('790476bc092f4faa8858c6a12582539a', 'admin', '2018-07-10 14:53:49', '127.0.0.1', '');
INSERT INTO `log` VALUES ('7a49eb2f5676465ca25e9bfd3b3008ec', 'admin', '2018-07-09 16:50:05', '127.0.0.1', '');
INSERT INTO `log` VALUES ('7e36fad494f44959a8a74a437b101741', 'admin', '2018-07-06 14:35:50', '127.0.0.1', '');
INSERT INTO `log` VALUES ('7f6b4d7b169c4b65889b8ee5b5613a0f', 'admin', '2018-07-09 17:17:05', '127.0.0.1', '');
INSERT INTO `log` VALUES ('80c42f87595f4590863464130b83ca46', 'admin', '2018-07-06 15:03:29', '127.0.0.1', '');
INSERT INTO `log` VALUES ('8237ba910fb2413e8ce914062a74fb00', 'admin', '2018-07-04 17:35:36', '111.205.43.253', '');
INSERT INTO `log` VALUES ('82b16da3b74444f986ab1804f350c8ba', 'admin', '2018-07-09 17:28:44', '127.0.0.1', '');
INSERT INTO `log` VALUES ('8892ee34d7484338a4d8423d9e53a689', 'admin', '2018-07-10 12:25:31', '127.0.0.1', '');
INSERT INTO `log` VALUES ('8c7d1c9e349a43ca882272fca3c9fca0', 'admin', '2018-07-06 15:25:10', '127.0.0.1', '');
INSERT INTO `log` VALUES ('8e4763327b50489eb87bacfac67637a9', 'admin', '2018-07-06 17:31:54', '127.0.0.1', '');
INSERT INTO `log` VALUES ('957e5901ae5946d596520ff0184cc4d4', 'admin', '2018-07-09 16:53:10', '127.0.0.1', '');
INSERT INTO `log` VALUES ('966a296edf684907ba893f399b0773c3', 'admin', '2018-07-04 17:48:45', '127.0.0.1', '');
INSERT INTO `log` VALUES ('97719ae8b1fe49a8b175d61a4cadd7c5', 'admin', '2018-07-09 16:58:05', '111.205.43.246', '');
INSERT INTO `log` VALUES ('98924d4bc30d47edad89c6dd6881f398', 'manage', '2018-07-06 16:58:45', '127.0.0.1', '');
INSERT INTO `log` VALUES ('9a4f0c062b1b4a1782853040b0255f82', 'admin', '2018-07-04 18:02:53', '127.0.0.1', '');
INSERT INTO `log` VALUES ('a17fcca31e6b465daa72759547ebe471', 'admin', '2018-07-10 11:19:17', '127.0.0.1', '');
INSERT INTO `log` VALUES ('a36a4f243e0240d9ab9b18896d8f5a86', 'admin', '2018-07-04 22:46:12', '111.199.185.154', '');
INSERT INTO `log` VALUES ('aa511f46a111434a8e41723165da0df0', 'admin', '2018-07-10 14:55:52', '127.0.0.1', '');
INSERT INTO `log` VALUES ('aaa46ad57d13443ab6390ec37b538fa3', 'admin', '2018-07-09 16:42:21', '127.0.0.1', '');
INSERT INTO `log` VALUES ('abb2ed6055a4406b877a69281d78d6a0', 'admin', '2018-07-06 17:30:14', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ac6ccae44a0d471e951415cd404f8ecb', 'manage', '2018-07-06 16:03:33', '127.0.0.1', '');
INSERT INTO `log` VALUES ('afe0c869b268410491047740990d2b6c', 'admin', '2018-07-10 10:45:19', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b1daa567d5624dc192570bb813937e7d', 'admin', '2018-07-06 16:03:19', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b308d46b7739448c921bb5f6b4a97f4b', 'manage', '2018-07-09 13:52:38', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b33533d465dc46d1955f74c13238bc5d', 'admin', '2018-07-10 11:30:39', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b3a730a81f0a4551ae0f8f657eac0ea8', 'admin', '2018-07-06 14:19:25', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b6cab35519134fccbcf4e034bcee2f1b', 'admin', '2018-07-06 17:49:30', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b6e3ace293824afc82e6049a29a48f99', 'admin', '2018-07-06 14:20:48', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b8abea4baf5c4cfbafcc11b867d2013b', 'admin', '2018-07-09 16:36:06', '127.0.0.1', '');
INSERT INTO `log` VALUES ('b8c77c3e55484f588b6ff031259746e0', 'admin', '2018-07-10 14:54:56', '127.0.0.1', '');
INSERT INTO `log` VALUES ('ba1dff9878f942d182d410b00cb30b9a', 'admin', '2018-07-10 14:49:45', '127.0.0.1', '');
INSERT INTO `log` VALUES ('bbc5c36fd8a54aa0a8986a4dfc5e9885', 'admin', '2018-07-10 09:34:39', '111.205.43.237', '');
INSERT INTO `log` VALUES ('bc0a28972f2e42d0b4792cbc5ba9d329', 'admin', '2018-07-10 11:13:38', '127.0.0.1', '');
INSERT INTO `log` VALUES ('bd1eade653d34f9f98ede9248bbfeed5', 'admin', '2018-07-10 11:20:15', '127.0.0.1', '');
INSERT INTO `log` VALUES ('bdc2add90475439881967c32b8b1112d', 'admin', '2018-07-09 17:48:38', '127.0.0.1', '');
INSERT INTO `log` VALUES ('bdfcfb3f13064499a4202cb20ce836b5', 'admin', '2018-07-06 16:57:30', '127.0.0.1', '');
INSERT INTO `log` VALUES ('c5968d602d794db89ad87c783021798b', 'admin', '2018-07-06 17:39:44', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d0dd89f2b1584eae838fa6c9dd8ed1e7', 'manage', '2018-07-06 15:44:19', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d6cfae2fa7da409dbea6706a397684f0', 'admin', '2018-07-09 17:07:51', '111.205.43.246', '');
INSERT INTO `log` VALUES ('d7ddda76daa64366b6e59d2466afde19', 'admin', '2018-07-09 18:02:36', '127.0.0.1', '');
INSERT INTO `log` VALUES ('d9ae441b1cad4b4799b480a094a54d30', 'admin', '2018-07-04 16:21:02', '127.0.0.1', '');
INSERT INTO `log` VALUES ('dc166a7d61b44235a27a7c8682783eb7', 'admin', '2018-07-06 17:27:58', '127.0.0.1', '');
INSERT INTO `log` VALUES ('de8807b9052041c18c3701d4968abe7f', 'admin', '2018-07-10 11:28:47', '127.0.0.1', '');
INSERT INTO `log` VALUES ('df0ea7a4d01547ef8c5c287d2d9bbf1a', 'admin', '2018-07-09 17:21:20', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e081cab188e841a79c3890cdc98117bd', 'admin', '2018-07-09 16:56:38', '127.0.0.1', '');
INSERT INTO `log` VALUES ('e1462ff7c2d64ac0853012d85c70d498', 'admin', '2018-07-06 10:50:47', '127.0.0.1', '');
INSERT INTO `log` VALUES ('eafae01e81b346c2b5b986e45b91c6bc', 'admin', '2018-07-06 15:56:21', '127.0.0.1', '');
INSERT INTO `log` VALUES ('eb1eb447a78946369cb69b0cca25c59a', 'admin', '2018-07-06 17:35:28', '127.0.0.1', '');
INSERT INTO `log` VALUES ('eb7d167b3cfd458fa42c9a3986295caa', 'admin', '2018-07-09 11:24:29', '111.205.43.237', '');
INSERT INTO `log` VALUES ('f1072e45cf0e471f9b519249754e8381', 'admin', '2018-07-10 14:32:36', '127.0.0.1', '');
INSERT INTO `log` VALUES ('f85be5f6113642998a0a5805f9b76b6a', 'admin', '2018-07-09 16:33:04', '127.0.0.1', '');
INSERT INTO `log` VALUES ('fd0f49c3e9654a439742d568b04553e0', 'admin', '2018-07-06 15:44:36', '127.0.0.1', '');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `uid` varchar(32) NOT NULL,
  `account` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `salt` varchar(32) NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`uid`,`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('3eb011e6638b4184aef77c3b095883df', 'flyshy', 'b2e61c4d4362ac061ad3bfe115b7b700', '6cfa0ce808a2ff68e61d248af75243a7', '1', '2017-03-14 09:44:35', '2017-03-14 09:44:35');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `menu_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `menu_type` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '资源类型，菜单或都按钮(menu,button)',
  `menu_url` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `menu_code` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `parent_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `parent_ids` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `child_num` int(10) NOT NULL DEFAULT '0',
  `listorder` int(10) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('00dc5c51e4824f49a30013385f680b0c', '日志管理', 'auth', '/console/log/index', 'log:index', 'e5f52fe2115e46229c60803e478d2e9a', null, '0', '1', '2017-01-06 14:11:23', '2017-05-08 14:55:21');
INSERT INTO `menu` VALUES ('16b6a67592f94442ab270be030b1bb15', '图片列表', 'auth', '/console/picture/index', 'picture:list', 'b3496a2ddaf445f881f865a094e71820', null, '2', '0', '2018-05-16 11:08:59', '2018-05-16 11:08:59');
INSERT INTO `menu` VALUES ('1cc3d9ad04e4424db1bb086d1678925e', '菜单删除', 'auth', '/console/menu/delete', 'menu:delete', '736bdf0b9aec4c59928a530e34bd9aad', null, '0', '0', '2017-05-10 16:45:30', '2017-05-10 16:45:30');
INSERT INTO `menu` VALUES ('1d5151ea84564f82b3978212d4084365', '日志统计', 'auth', '/console/log/count', 'log:count', 'e5f52fe2115e46229c60803e478d2e9a', null, '0', '0', '2018-07-06 14:28:24', '2018-07-06 14:28:24');
INSERT INTO `menu` VALUES ('203d58d4c700443e912f2846df5bd56a', '图片删除', 'auth', '/console/picture/delete', 'picture:delete', '16b6a67592f94442ab270be030b1bb15', null, '0', '0', '2018-05-16 11:08:59', '2018-05-16 11:08:59');
INSERT INTO `menu` VALUES ('2191c9efc2fa431bb427b81ad938e8aa', '角色保存', 'auth', '/console/role/save', 'role:save', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-10 16:41:21', '2017-05-10 16:41:21');
INSERT INTO `menu` VALUES ('292b22f5eb834d3eb6cb0a0d9733689c', 'APP应用列表', 'auth', '/console/app/index', 'app:index', '6ba1a9f520784278a4a04f01ff841da3', null, '0', '0', '2018-07-06 15:41:23', '2018-07-06 15:41:23');
INSERT INTO `menu` VALUES ('2b3081e2c106431983544ad957f2ba86', '设备列表', 'auth', '/console/device/index', 'device:index', 'c21c083805d644c9a908e7691bb11e74', null, '0', '0', '2018-07-06 14:27:24', '2018-07-06 14:27:24');
INSERT INTO `menu` VALUES ('3576373e60384ba9b02f445361a4c9b0', '政策管理', 'menu', '/console/policy/setting', 'policy:setting', '0', null, '1', '0', '2018-06-20 13:45:02', '2018-06-20 13:45:02');
INSERT INTO `menu` VALUES ('362923d31e064f84adb8c23ba91e54d8', '管理员编辑', 'auth', '/console/admin/from', 'admin:edit', 'e0dde3b9227c471eb3bd2ba0a7fab131', null, '0', '0', '2017-05-08 14:57:39', '2017-05-10 16:40:47');
INSERT INTO `menu` VALUES ('3ac96215e82f40b5bfe442e6828641df', '系统管理', 'menu', '/console/system/admin', 'system:admin', '0', null, '3', '1', '2016-12-07 16:00:00', '2017-05-10 16:46:27');
INSERT INTO `menu` VALUES ('444fa2ef57174a4d915417cf2f6cfc46', '用户管理', 'menu', '/console/user/setting', 'user:setting', '0', null, '1', '0', '2018-07-06 15:35:35', '2018-07-06 15:35:35');
INSERT INTO `menu` VALUES ('4bfd250431b640eeb8a8477dc6b3095a', '图片修改', 'auth', '/console/picture/edit', 'picture:edit', '16b6a67592f94442ab270be030b1bb15', null, '0', '0', '2018-05-16 11:08:14', '2018-05-16 11:08:14');
INSERT INTO `menu` VALUES ('6293ffc6c6d14bf8b6e0d9b5317edd49', '政策列表', 'auth', '/console/policy/index', 'policy:index', '3576373e60384ba9b02f445361a4c9b0', null, '0', '0', '2018-06-20 13:45:02', '2018-06-20 13:45:02');
INSERT INTO `menu` VALUES ('6580896645d046a0acf3c1194d7bbf8e', '管理员删除', 'auth', '/console/admin/delete', 'admin:delete', 'e0dde3b9227c471eb3bd2ba0a7fab131', null, '0', '0', '2018-07-09 17:04:00', '2018-07-09 17:04:00');
INSERT INTO `menu` VALUES ('6ba1a9f520784278a4a04f01ff841da3', 'APP应用管理', 'menu', '/console/app/setting', 'app:setting', '0', null, '1', '0', '2018-07-06 15:41:23', '2018-07-06 15:41:23');
INSERT INTO `menu` VALUES ('6cda978dc9404ba2bf5854b74735b0bc', '角色管理', 'auth', '/console/role/index', 'role:index', '3ac96215e82f40b5bfe442e6828641df', null, '4', '2', '2016-12-07 16:47:40', '2016-12-07 16:47:40');
INSERT INTO `menu` VALUES ('6ee9911cd9ea496d91cf963dd6dd593c', '域名管理', 'menu', '/console/domain/setting', 'domain:setting', '0', null, '1', '0', '2018-07-09 17:05:21', '2018-07-09 17:05:21');
INSERT INTO `menu` VALUES ('736bdf0b9aec4c59928a530e34bd9aad', '菜单管理', 'auth', '/console/menu/index', 'menu:index', '3ac96215e82f40b5bfe442e6828641df', null, '3', '3', '2016-12-07 16:50:17', '2016-12-07 16:50:17');
INSERT INTO `menu` VALUES ('85dad2bd9023451fab632dcfc4357d3b', '管理员保存', 'auth', '/console/admin/save', 'admin:save', 'e0dde3b9227c471eb3bd2ba0a7fab131', null, '0', '0', '2017-05-10 16:38:07', '2017-05-10 16:41:00');
INSERT INTO `menu` VALUES ('8a653e3fb15642d9be6aad13b02009fb', '角色授权', 'auth', '/console/role/grant', 'role:grant', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-10 16:42:37', '2017-05-10 16:42:37');
INSERT INTO `menu` VALUES ('984909260a06410d9be37c300e3df09d', '会员管理', 'menu', '/console/member/default', 'member:default', '0', null, '1', '0', '2017-05-10 16:50:16', '2017-05-10 16:51:06');
INSERT INTO `menu` VALUES ('9f41af1454d046b596023a2822c5078c', '角色编辑', 'auth', '/console/role/from', 'role:edit', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-08 14:59:25', '2017-05-08 14:59:25');
INSERT INTO `menu` VALUES ('aab7966c97db4643a36cb5afa24be38b', '角色删除', 'menu', '/console/role/delete', 'role:delete', '6cda978dc9404ba2bf5854b74735b0bc', null, '0', '0', '2017-05-10 16:43:37', '2017-05-10 16:43:37');
INSERT INTO `menu` VALUES ('b3496a2ddaf445f881f865a094e71820', '图片管理', 'menu', '/console/picture/setting', 'picture:setting', '0', null, '1', '0', '2018-07-06 15:42:15', '2018-07-06 15:42:15');
INSERT INTO `menu` VALUES ('c11b592271db4468a99d52e0d089c52b', '安全管理', 'menu', '/console/security/setting', 'security:setting', '0', null, '0', '0', '2018-07-06 15:59:58', '2018-07-06 15:59:58');
INSERT INTO `menu` VALUES ('c21c083805d644c9a908e7691bb11e74', '设备管理', 'menu', '/console/device/setting', 'device:setting', '0', null, '1', '0', '2018-07-06 14:27:24', '2018-07-06 14:27:24');
INSERT INTO `menu` VALUES ('c5cca135ee534bfeb482fb04b9311982', '菜单编辑', 'auth', '/console/menu/from', 'menu:from', '736bdf0b9aec4c59928a530e34bd9aad', null, '0', '0', '2016-12-07 16:51:31', '2017-05-08 15:00:02');
INSERT INTO `menu` VALUES ('d582ffb2c47d4d358e59fb254297939e', '技术支持', 'menu', '/console/support/setting', 'support:setting', '0', null, '0', '0', '2018-07-06 16:01:00', '2018-07-06 16:01:00');
INSERT INTO `menu` VALUES ('e0dde3b9227c471eb3bd2ba0a7fab131', '管理员管理', 'auth', '/console/admin/index', 'admin:index', '3ac96215e82f40b5bfe442e6828641df', null, '3', '1', '2018-07-09 17:04:00', '2018-07-09 17:04:00');
INSERT INTO `menu` VALUES ('e5f52fe2115e46229c60803e478d2e9a', '扩展设置', 'menu', '/console/system/setting', 'system:setting', '0', null, '2', '3', '2018-07-06 14:28:24', '2018-07-06 14:28:24');
INSERT INTO `menu` VALUES ('e85b2fb3e6ee4d0a9711c577bc842821', '会员管理', 'auth', '/console/member/index', 'member:index', '984909260a06410d9be37c300e3df09d', null, '0', '0', '2017-05-10 16:51:20', '2017-05-10 16:51:20');
INSERT INTO `menu` VALUES ('e97416378c0e4ca289f6d6fd33aa0a05', '组织管理', 'menu', '/console/organization/setting', 'organization:setting', '0', null, '1', '10', '2018-07-06 15:07:22', '2018-07-06 15:07:22');
INSERT INTO `menu` VALUES ('eb4f9286525248488c463f06aa9e43a6', '组织列表', 'auth', '/console/organization/index', 'organization:index', 'e97416378c0e4ca289f6d6fd33aa0a05', null, '0', '0', '2018-07-06 15:07:22', '2018-07-06 15:07:22');
INSERT INTO `menu` VALUES ('f1921044ed9c4c01ab14dd2ffb2dcd21', '用户列表', 'auth', '/console/user/index', 'user:setting', '444fa2ef57174a4d915417cf2f6cfc46', null, '0', '0', '2018-07-06 15:35:35', '2018-07-06 15:35:35');
INSERT INTO `menu` VALUES ('f4237d06c0c94906bdc04f5ed19cbaeb', '菜单保存', 'auth', '/console/menu/save', 'menu:save', '736bdf0b9aec4c59928a530e34bd9aad', null, '0', '0', '2017-05-10 16:44:51', '2017-05-10 16:44:51');
INSERT INTO `menu` VALUES ('f6f54f4ab74f477ba7a519fcf6270dd4', '计费管理', 'menu', '/console/billing/setting', 'billing:setting', '0', null, '0', '0', '2018-07-06 15:57:22', '2018-07-06 15:57:22');

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `organization_name` varchar(20) NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of organization
-- ----------------------------

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `origin_pic_url` varchar(50) NOT NULL COMMENT '原始图片名称',
  `fixed_pic_url` varchar(50) NOT NULL COMMENT '修改后图片名称',
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime NOT NULL,
  `uploader_name` varchar(20) NOT NULL COMMENT '上传者名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of picture
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `role_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `role_desc` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name_unique` (`role_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('36f1dd1296674fc08484c5abf6a5806b', '系统管理员', '系统管理员', '1', '2016-12-07 08:53:57', '2017-05-11 13:59:03');
INSERT INTO `role` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '普通管理员', '普通管理员', '1', '2016-12-07 13:21:21', '2017-05-05 12:58:38');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `menu_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `role_menu_foreign` (`menu_id`) USING BTREE,
  CONSTRAINT `fk_ref_menu` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`),
  CONSTRAINT `fk_ref_role2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '292b22f5eb834d3eb6cb0a0d9733689c');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '2b3081e2c106431983544ad957f2ba86');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '3576373e60384ba9b02f445361a4c9b0');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '3ac96215e82f40b5bfe442e6828641df');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '444fa2ef57174a4d915417cf2f6cfc46');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '6293ffc6c6d14bf8b6e0d9b5317edd49');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '6ba1a9f520784278a4a04f01ff841da3');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', '6ee9911cd9ea496d91cf963dd6dd593c');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'c11b592271db4468a99d52e0d089c52b');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'c21c083805d644c9a908e7691bb11e74');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'd582ffb2c47d4d358e59fb254297939e');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'e0dde3b9227c471eb3bd2ba0a7fab131');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'e97416378c0e4ca289f6d6fd33aa0a05');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'eb4f9286525248488c463f06aa9e43a6');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'f1921044ed9c4c01ab14dd2ffb2dcd21');
INSERT INTO `role_menu` VALUES ('cbe8356d64a8433cb5dad5c7fccf8dce', 'f6f54f4ab74f477ba7a519fcf6270dd4');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `sex` tinyint(1) NOT NULL COMMENT '1为男，0为女',
  `emale` varchar(50) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
