/*
 Navicat Premium Dump SQL

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : elm

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 31/12/2025 10:10:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for business
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business`  (
  `businessId` int NOT NULL AUTO_INCREMENT COMMENT '商家编号',
  `businessName` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商家名称',
  `businessAddress` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商家地址',
  `businessExplain` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商家介绍',
  `businessImg` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商家图片',
  `orderTypeId` int NOT NULL COMMENT '点餐分类（1:美食、2:早餐、3:跑腿代购、4:汉堡披萨、5:甜品饮品、6:速食简餐、7:地方小吃、8:米粉面馆、9:包子粥铺、10:炸鸡炸串）',
  `starPrice` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '起送费',
  `deliveryPrice` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '配送费',
  `remarks` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`businessId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of business
-- ----------------------------

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `cartId` int NOT NULL AUTO_INCREMENT COMMENT '无意义编号',
  `foodId` int NOT NULL COMMENT '食品编号',
  `businessId` int NOT NULL COMMENT '所属商家编号',
  `userId` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属用户编号',
  `quantity` int NOT NULL COMMENT '同一类型食品的购买数量',
  PRIMARY KEY (`cartId`) USING BTREE,
  INDEX `fk_cart_foodId`(`foodId` ASC) USING BTREE,
  INDEX `fk_cart_businessId`(`businessId` ASC) USING BTREE,
  INDEX `fk_cart_userId`(`userId` ASC) USING BTREE,
  CONSTRAINT `fk_cart_businessId` FOREIGN KEY (`businessId`) REFERENCES `business` (`businessId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cart_foodId` FOREIGN KEY (`foodId`) REFERENCES `food` (`foodId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cart_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------

-- ----------------------------
-- Table structure for deliveryaddress
-- ----------------------------
DROP TABLE IF EXISTS `deliveryaddress`;
CREATE TABLE `deliveryaddress`  (
  `daId` int NOT NULL AUTO_INCREMENT COMMENT '送货地址编号',
  `contactName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '联系人姓名',
  `contactSex` int NOT NULL COMMENT '联系人性别',
  `contactTel` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '联系人电话',
  `address` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '送货地址',
  `userId` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属用户编号',
  PRIMARY KEY (`daId`) USING BTREE,
  INDEX `fk_da_userId`(`userId` ASC) USING BTREE,
  CONSTRAINT `fk_da_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of deliveryaddress
-- ----------------------------

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food`  (
  `foodId` int NOT NULL AUTO_INCREMENT COMMENT '食品编号',
  `foodName` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '食品名称',
  `foodExplain` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '食品介绍',
  `foodImg` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '食品图片',
  `foodPrice` decimal(5, 2) NOT NULL COMMENT '食品价格',
  `businessId` int NOT NULL COMMENT '所属商家编号',
  `remarks` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`foodId`) USING BTREE,
  INDEX `fk_food_businessId`(`businessId` ASC) USING BTREE,
  CONSTRAINT `fk_food_businessId` FOREIGN KEY (`businessId`) REFERENCES `business` (`businessId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of food
-- ----------------------------

-- ----------------------------
-- Table structure for orderdetailet
-- ----------------------------
DROP TABLE IF EXISTS `orderdetailet`;
CREATE TABLE `orderdetailet`  (
  `odId` int NOT NULL AUTO_INCREMENT COMMENT '订单明细编号',
  `orderId` int NOT NULL COMMENT '所属订单编号',
  `foodId` int NOT NULL COMMENT '所属食品编号',
  `quantity` int NOT NULL COMMENT '数量',
  PRIMARY KEY (`odId`) USING BTREE,
  INDEX `fk_od_orderId`(`orderId` ASC) USING BTREE,
  INDEX `fk_od_foodId`(`foodId` ASC) USING BTREE,
  CONSTRAINT `fk_od_foodId` FOREIGN KEY (`foodId`) REFERENCES `food` (`foodId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_od_orderId` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderdetailet
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `orderId` int NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `userId` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属用户编号',
  `businessId` int NOT NULL COMMENT '所属商家编号',
  `orderDate` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订购日期',
  `orderTotal` decimal(7, 2) NOT NULL DEFAULT 0.00 COMMENT '订单总价',
  `daId` int NOT NULL COMMENT '所属送货地址编号',
  `orderState` int NOT NULL DEFAULT 0 COMMENT '订单状态（0:未支付;1:已支付）',
  PRIMARY KEY (`orderId`) USING BTREE,
  INDEX `fk_orders_userId`(`userId` ASC) USING BTREE,
  INDEX `fk_orders_businessId`(`businessId` ASC) USING BTREE,
  INDEX `fk_orders_daId`(`daId` ASC) USING BTREE,
  CONSTRAINT `fk_orders_businessId` FOREIGN KEY (`businessId`) REFERENCES `business` (`businessId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_orders_daId` FOREIGN KEY (`daId`) REFERENCES `deliveryaddress` (`daId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_orders_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户编号',
  `password` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `userName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名称',
  `userSex` int NOT NULL DEFAULT 1 COMMENT '用户性别（1:男;0:女）',
  `userImg` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '用户头像',
  `delTag` int NOT NULL DEFAULT 1 COMMENT '删除标记（1:正常;0:删除）',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
