/*
Navicat MySQL Data Transfer

Source Server         : 积分银行测试
Source Server Version : 50725
Source Host           : rm-wz9zsr19fng3h66zkqo.mysql.rds.aliyuncs.com:3306
Source Database       : detection

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-03-26 18:25:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) DEFAULT NULL COMMENT '设备id',
  `device_name` varchar(32) DEFAULT NULL COMMENT '设备名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `device_address` varchar(128) DEFAULT NULL COMMENT '地址',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `district` varchar(32) DEFAULT NULL COMMENT '描述',
  `company_name` varchar(32) DEFAULT NULL COMMENT '所属公司',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `ip` varchar(32) DEFAULT NULL COMMENT '当前ip',
  `static_ip` varchar(32) DEFAULT NULL COMMENT '静态ip',
  `static_subnet_mask` varchar(23) DEFAULT NULL COMMENT '静态ip掩码',
  `static_gateway` varchar(23) DEFAULT NULL COMMENT '静态网关',
  `ssid` varchar(32) DEFAULT NULL,
  `wifi_password` varchar(32) DEFAULT NULL,
  `bluetooth_id` varchar(32) DEFAULT NULL,
  `ip_set_static` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99250976 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('30351489', '0001', 'test1', '2020-03-20 18:16:59', '火星', '2', 'test', null, null, 'LAPTOP-8UQGBI7U', null, null, null, null, null, null, null);
INSERT INTO `device` VALUES ('92084974', '0002', 'test2', '2019-01-10 12:46:32', '水星', '2', 'test', null, null, '192.168.1.68', '192.168.1.89', '255.255.255.0', '192.168.1.1', 'detection-89', '123456', null, '1');
INSERT INTO `device` VALUES ('99250951', '0003', 'test3', '2019-01-17 11:35:47', '地球', '2', 'test', null, null, '192.168.1.69', '192.168.1.69', '255.255.255.0', '192.168.1.1', 'detection-69', '123456', null, '1');
INSERT INTO `device` VALUES ('99250952', '0004', 'test4', '2019-01-17 11:39:35', '太阳', '2', 'test', null, null, '192.168.1.70', '192.168.1.67', '255.255.255.0', '192.168.1.1', 'detection-67', '123456', '1234', '1');
INSERT INTO `device` VALUES ('99250953', '0005', 'test5', '2019-01-18 09:30:04', '月球', '2', 'test', null, null, '192.168.1.69', '192.168.1.68', '255.255.255.0', '192.168.1.1', 'detection-68', '123456', null, '1');

-- ----------------------------
-- Table structure for device_illuminating_task
-- ----------------------------
DROP TABLE IF EXISTS `device_illuminating_task`;
CREATE TABLE `device_illuminating_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `illuminating_task_name` varchar(32) DEFAULT NULL,
  `open_time` varchar(32) DEFAULT NULL,
  `play_date` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `device_id` varchar(32) DEFAULT NULL,
  `illuminating_brightness` double DEFAULT NULL,
  `close_time` varchar(32) DEFAULT NULL,
  `flash` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80584431 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device_illuminating_task
-- ----------------------------
INSERT INTO `device_illuminating_task` VALUES ('80584430', 'thanks', '11:32', '2', '2', 'xxx01', '20', '12:32', '2');

-- ----------------------------
-- Table structure for device_limit
-- ----------------------------
DROP TABLE IF EXISTS `device_limit`;
CREATE TABLE `device_limit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) DEFAULT NULL COMMENT '设备id',
  `temperature_min` double DEFAULT NULL COMMENT '温度最小',
  `temperature_max` double DEFAULT NULL COMMENT '温度最大',
  `humidity_min` double DEFAULT NULL COMMENT '湿度最小',
  `humidity_max` double DEFAULT NULL COMMENT '湿度最大',
  `pm25_min` double DEFAULT NULL COMMENT 'PM2.5最小',
  `pm25_max` double DEFAULT NULL COMMENT 'PM2.5最大',
  `temperature_alarm` int(1) DEFAULT '0' COMMENT '温度报警开关',
  `humidity_alarm` int(1) DEFAULT '0' COMMENT '湿度报警开关',
  `optical_inductor_alarm` int(1) DEFAULT '0' COMMENT '光报警开关',
  `pm25_alarm` int(1) DEFAULT '0' COMMENT 'PM2.5报警',
  `body_inducto_alarm` int(1) DEFAULT '0' COMMENT '人体报警开关',
  `smoke_sensors_alarm` int(1) DEFAULT '0' COMMENT '烟报警开关',
  `optical_inductor_max` double DEFAULT NULL COMMENT '光报警大',
  `optical_inductor_min` double DEFAULT NULL COMMENT '光报警小',
  `mode` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66952605 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device_limit
-- ----------------------------
INSERT INTO `device_limit` VALUES ('29838874', '0001', '-5', '39', '10', '100', null, null, '1', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `device_limit` VALUES ('32749280', '0002', '-30', '37', '10', '100', '0.1', '0.4', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `device_limit` VALUES ('38597864', '0003', null, null, null, null, null, null, '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `device_limit` VALUES ('61651700', '0004', null, null, null, null, null, null, '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `device_limit` VALUES ('66952582', '0005', '-20', '30', '13', '95', '0.5', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for device_record
-- ----------------------------
DROP TABLE IF EXISTS `device_record`;
CREATE TABLE `device_record` (
  `id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `device_address` varchar(128) DEFAULT NULL,
  `device_id` varchar(32) DEFAULT NULL,
  `device_name` varchar(32) DEFAULT NULL,
  `latlng` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device_record
-- ----------------------------

-- ----------------------------
-- Table structure for device_sensor
-- ----------------------------
DROP TABLE IF EXISTS `device_sensor`;
CREATE TABLE `device_sensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) DEFAULT NULL COMMENT '设备id',
  `temperature` double DEFAULT NULL COMMENT '温度',
  `pm25` double DEFAULT NULL COMMENT 'PM2.5',
  `humidity` double DEFAULT NULL COMMENT '湿度',
  `optical_inductor` double DEFAULT NULL COMMENT '光',
  `smoke_sensors` int(1) DEFAULT NULL COMMENT '烟',
  `illuminating_brightness` double DEFAULT NULL COMMENT '灯亮度',
  `latlng` varchar(32) DEFAULT NULL COMMENT '经纬度',
  `body_inductor` int(1) DEFAULT NULL COMMENT '人体感应',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98538594 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device_sensor
-- ----------------------------
INSERT INTO `device_sensor` VALUES ('48048578', '0001', '54', '50', '50', '50', '0', '0', '[23.1666981,113.429923]', '0');
INSERT INTO `device_sensor` VALUES ('68357774', '0002', '18.7', '0', '99.900002', '135', '0', '0', '[23.162988,113.434850]', '0');
INSERT INTO `device_sensor` VALUES ('98538571', '0003', '21.2', '0.23', '45.700001', '3612', '0', '100', '[-0.003602,0.005651]', '0');
INSERT INTO `device_sensor` VALUES ('98538572', '0004', '26.7', '0.23', '51.599998', '0', '0', '0', '[-356.003602,-355.994349]', '0');
INSERT INTO `device_sensor` VALUES ('98538576', '0005', '26.5', '0.23', '57.799999', '0', '0', '0', '[-356.003602,-355.994349]', '1');

-- ----------------------------
-- Table structure for illuminating_task
-- ----------------------------
DROP TABLE IF EXISTS `illuminating_task`;
CREATE TABLE `illuminating_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `illuminating_task_name` varchar(32) DEFAULT NULL,
  `open_time` varchar(32) DEFAULT NULL,
  `play_date` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `group_name` varchar(32) DEFAULT NULL,
  `device_id` varchar(32) DEFAULT NULL,
  `illuminating_brightness` double DEFAULT NULL,
  `close_time` varchar(32) DEFAULT NULL,
  `flash` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94311798 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of illuminating_task
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1视频警报，2传感器报警，3配置，4控制',
  `message` text,
  `device_name` varchar(32) DEFAULT NULL,
  `company_name` varchar(32) DEFAULT NULL,
  `device_id` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27318 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('27290', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 16:43:05');
INSERT INTO `log` VALUES ('27291', 'admin', '4', '登陆', null, null, null, '2020-03-26 16:43:14');
INSERT INTO `log` VALUES ('27292', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 16:43:21');
INSERT INTO `log` VALUES ('27293', null, '2', '设备离线;', 'test1', null, '0001', '2020-03-26 16:44:33');
INSERT INTO `log` VALUES ('27294', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 16:45:51');
INSERT INTO `log` VALUES ('27295', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 16:45:55');
INSERT INTO `log` VALUES ('27296', null, '2', '设备离线;', 'test1', null, '0001', '2020-03-26 16:47:20');
INSERT INTO `log` VALUES ('27297', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 16:57:28');
INSERT INTO `log` VALUES ('27298', null, '2', '设备离线;', 'test1', null, '0001', '2020-03-26 16:58:58');
INSERT INTO `log` VALUES ('27299', 'admin', '4', '登陆', null, null, null, '2020-03-26 17:04:40');
INSERT INTO `log` VALUES ('27300', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 17:09:40');
INSERT INTO `log` VALUES ('27301', null, '2', '设备离线;', 'test1', null, '0001', '2020-03-26 17:10:56');
INSERT INTO `log` VALUES ('27302', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 17:18:08');
INSERT INTO `log` VALUES ('27303', null, '2', '设备离线;', 'test1', null, '0001', '2020-03-26 17:19:18');
INSERT INTO `log` VALUES ('27304', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 17:19:52');
INSERT INTO `log` VALUES ('27305', null, '2', '设备离线;', 'test1', null, '0001', '2020-03-26 17:20:54');
INSERT INTO `log` VALUES ('27306', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 17:21:00');
INSERT INTO `log` VALUES ('27307', null, '2', '设备离线;', 'test1', null, '0001', '2020-03-26 17:22:14');
INSERT INTO `log` VALUES ('27308', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 17:24:30');
INSERT INTO `log` VALUES ('27309', null, '2', '设备离线;', 'test1', null, '0001', '2020-03-26 17:26:09');
INSERT INTO `log` VALUES ('27310', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 17:31:24');
INSERT INTO `log` VALUES ('27311', 'admin', '4', '登陆', null, null, null, '2020-03-26 17:31:50');
INSERT INTO `log` VALUES ('27312', 'admin', '3', '修改设备预警信息为：{\"id\":29838874,\"bodyInductoAlarm\":0,\"deviceId\":\"0001\",\"humidityAlarm\":0,\"humidityMax\":100.0,\"humidityMin\":10.0,\"opticalInductorAlarm\":0,\"pm25Alarm\":0,\"pm25Max\":null,\"pm25Min\":null,\"smokeSensorsAlarm\":0,\"temperatureAlarm\":1,\"temperatureMax\":39.0,\"temperatureMin\":-5.0,\"opticalInductorMin\":0.0,\"opticalInductorMax\":0.0,\"mode\":0}', null, null, '0001', '2020-03-26 17:33:01');
INSERT INTO `log` VALUES ('27313', null, '2', '温度高达54.0;', 'test1', null, '0001', '2020-03-26 17:33:50');
INSERT INTO `log` VALUES ('27314', null, '2', '设备离线;', 'test1', null, '0001', '2020-03-26 17:34:19');
INSERT INTO `log` VALUES ('27315', null, '2', '设备上线;', 'test1', null, '0001', '2020-03-26 17:35:07');
INSERT INTO `log` VALUES ('27316', null, '2', '设备离线;', 'test1', null, '0001', '2020-03-26 17:36:09');
INSERT INTO `log` VALUES ('27317', 'admin', '4', '登陆', null, null, null, '2020-03-26 18:18:48');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL COMMENT '菜单名',
  `url` varchar(60) NOT NULL COMMENT '选择后提交 url',
  `paret_id` int(11) NOT NULL COMMENT '所属id  0为主菜单',
  `type` int(4) NOT NULL COMMENT '管理员菜单与普通用户菜单',
  `icon` varchar(30) DEFAULT NULL COMMENT '图标样式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '终端状态', '', '0', '2', 'fa fa-tachometer');
INSERT INTO `menu` VALUES ('4', '照明', '', '0', '2', 'fa  fa-lightbulb-o');
INSERT INTO `menu` VALUES ('5', '分组管理', '', '0', '2', 'fa fa-sitemap');
INSERT INTO `menu` VALUES ('6', '用户管理', 'userMgt/list.do', '0', '2', ' fa fa-user');
INSERT INTO `menu` VALUES ('7', '日志', '', '0', '2', ' fa fa-archive');
INSERT INTO `menu` VALUES ('8', '系统设置', '', '0', '2', ' fa fa-cogs');
INSERT INTO `menu` VALUES ('9', '数据报表', '', '0', '2', 'fa fa-bar-chart-o');
INSERT INTO `menu` VALUES ('11', '状态信息', 'deviceMgt/list.do', '1', '2', '');
INSERT INTO `menu` VALUES ('12', '设备位置', 'deviceMgt/map.do', '1', '2', '');
INSERT INTO `menu` VALUES ('41', '设置照明', '', '4', '2', '');
INSERT INTO `menu` VALUES ('51', '群组', '', '5', '2', '');
INSERT INTO `menu` VALUES ('61', '用户管理', 'userMgt/list.do', '6', '2', '');
INSERT INTO `menu` VALUES ('71', '日志', 'userMgt/log.do', '7', '2', '');
INSERT INTO `menu` VALUES ('81', '系统设置', 'systemMgt/system.do', '8', '2', '');
INSERT INTO `menu` VALUES ('91', '报表', 'reportFromMgt/list.do', '9', '2', '');
INSERT INTO `menu` VALUES ('1000', '终端状态', '', '0', '1', 'fa fa-tachometer');
INSERT INTO `menu` VALUES ('4000', '照明', '', '0', '1', 'fa  fa-lightbulb-o');
INSERT INTO `menu` VALUES ('5000', '分组管理', '', '0', '1', 'fa fa-sitemap');
INSERT INTO `menu` VALUES ('9000', '数据报表', '', '0', '1', 'fa fa-bar-chart-o');
INSERT INTO `menu` VALUES ('11000', '状态信息', 'deviceMgt/list.do', '1000', '1', '');
INSERT INTO `menu` VALUES ('12000', '实时位置', 'deviceMgt/map.do', '1000', '1', '');
INSERT INTO `menu` VALUES ('41000', '设置照明', '', '4000', '1', '');
INSERT INTO `menu` VALUES ('51000', '群组', '', '5000', '1', '');
INSERT INTO `menu` VALUES ('91000', '报表', 'reportFromMgt/list.do', '9000', '1', '');

-- ----------------------------
-- Table structure for report_forms
-- ----------------------------
DROP TABLE IF EXISTS `report_forms`;
CREATE TABLE `report_forms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) DEFAULT NULL,
  `temperature` double DEFAULT NULL COMMENT '温度',
  `humidity` double DEFAULT NULL COMMENT '湿度',
  `pm25` double DEFAULT NULL,
  `illuminating_brightness` double DEFAULT NULL COMMENT '照明亮度',
  `body_inductor` int(3) DEFAULT NULL COMMENT '人体感应',
  `smoke_sensors` int(3) DEFAULT NULL COMMENT '烟感',
  `optical_inductor` double(10,0) DEFAULT NULL COMMENT '光度',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59891 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_forms
-- ----------------------------
INSERT INTO `report_forms` VALUES ('59881', '0001', '20.7', '47.900002', '0.22', '0', '0', '0', '84', '2020-03-26 17:00:05');
INSERT INTO `report_forms` VALUES ('59882', '0002', '18.7', '99.900002', '0', '0', '0', '0', '135', '2020-03-26 17:00:05');
INSERT INTO `report_forms` VALUES ('59883', '0003', '21.2', '45.700001', '0.23', '100', '0', '0', '3612', '2020-03-26 17:00:05');
INSERT INTO `report_forms` VALUES ('59884', '0004', '26.7', '51.599998', '0.23', '0', '0', '0', '0', '2020-03-26 17:00:05');
INSERT INTO `report_forms` VALUES ('59885', '0005', '26.5', '57.799999', '0.23', '0', '1', '0', '0', '2020-03-26 17:00:05');
INSERT INTO `report_forms` VALUES ('59886', '0001', '54', '50', '50', '0', '0', '0', '50', '2020-03-26 18:00:05');
INSERT INTO `report_forms` VALUES ('59887', '0002', '18.7', '99.900002', '0', '0', '0', '0', '135', '2020-03-26 18:00:05');
INSERT INTO `report_forms` VALUES ('59888', '0003', '21.2', '45.700001', '0.23', '100', '0', '0', '3612', '2020-03-26 18:00:05');
INSERT INTO `report_forms` VALUES ('59889', '0004', '26.7', '51.599998', '0.23', '0', '0', '0', '0', '2020-03-26 18:00:05');
INSERT INTO `report_forms` VALUES ('59890', '0005', '26.5', '57.799999', '0.23', '0', '1', '0', '0', '2020-03-26 18:00:05');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户账号',
  `role` varchar(20) DEFAULT NULL COMMENT '身份',
  `state` varchar(20) DEFAULT NULL COMMENT '状态',
  `phone` varchar(11) DEFAULT NULL,
  `alias` varchar(32) DEFAULT NULL COMMENT '别名',
  `email` varchar(32) DEFAULT NULL,
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `landing_time` datetime DEFAULT NULL COMMENT '上次登陆时间',
  `note` text COMMENT '备注',
  `user_password` varchar(64) DEFAULT NULL,
  `wx_account` varchar(32) DEFAULT NULL COMMENT '微信号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17849738 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '在线', null, null, null, null, '2020-03-26 18:18:48', null, '679A8F775D61E1F8067995676B797357B8550D10606DDC0F99DE0A1A', null);
INSERT INTO `user` VALUES ('17849736', 'test', 'user', '离线', '123456', '测试1', null, '2018-12-17 16:23:15', '2020-03-20 18:21:36', null, '679A8F775D61E1F8067995676B797357B8550D10606DDC0F99DE0A1A', null);
INSERT INTO `user` VALUES ('17849737', 'test69', 'user', '注册未登录', '132132132', 'aaa', null, '2019-01-17 10:07:18', '2019-01-17 10:07:18', '', 'B10AFC0D968A78020269352C73AE117D9323050F3FB92F0D3A3C4F4A', null);
