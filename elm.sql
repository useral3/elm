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

 Date: 14/07/2026 15:53:48
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
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of business
-- ----------------------------
INSERT INTO `business` VALUES (1, '老王麻辣烫', '学府路12号', '正宗川味麻辣烫', 'sj01.png', 1, 20.00, 3.00, NULL);
INSERT INTO `business` VALUES (3, '川味小馆', '美食街1号', '地道川菜，麻辣鲜香', 'sj02.png', 1, 22.00, 3.00, NULL);
INSERT INTO `business` VALUES (4, '湘聚一堂', '解放路45号', '湖南风味，香辣过瘾', 'sj03.png', 1, 25.00, 4.00, NULL);
INSERT INTO `business` VALUES (5, '粤式茶餐厅', '中山路88号', '广式烧腊，原汁原味', 'sj04.png', 1, 18.00, 2.00, NULL);
INSERT INTO `business` VALUES (6, '东北铁锅炖', '建设路32号', '大锅炖菜，实惠管饱', 'sj05.png', 1, 30.00, 5.00, NULL);
INSERT INTO `business` VALUES (7, '西北面馆', '人民路56号', '兰州拉面，正宗清真', 'sj06.png', 1, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (8, '鲁味家常菜', '和平路78号', '山东风味，量大实惠', 'sj07.png', 1, 20.00, 3.00, NULL);
INSERT INTO `business` VALUES (9, '苏帮菜馆', '文化路90号', '精致苏帮菜，甜鲜可口', 'sj08.png', 1, 28.00, 4.00, NULL);
INSERT INTO `business` VALUES (10, '闽南海鲜', '滨海路12号', '新鲜海味，闽南做法', 'sj09.png', 1, 35.00, 5.00, NULL);
INSERT INTO `business` VALUES (11, '徽味食府', '长江路34号', '徽派名菜，臭鳜鱼一绝', 'sj01.png', 1, 26.00, 3.00, NULL);
INSERT INTO `business` VALUES (12, '晨光早餐铺', '阳光街5号', '豆浆油条包子，温暖每一天', 'sj02.png', 2, 8.00, 2.00, NULL);
INSERT INTO `business` VALUES (13, '永和豆浆', '新华路10号', '现磨豆浆，健康早餐', 'sj03.png', 2, 10.00, 2.00, NULL);
INSERT INTO `business` VALUES (14, '老台门包子', '学院路22号', '皮薄馅大，现包现蒸', 'sj04.png', 2, 7.00, 1.00, NULL);
INSERT INTO `business` VALUES (15, '五谷杂粮煎饼', '人民路44号', '杂粮煎饼果子，酥脆可口', 'sj05.png', 2, 6.00, 1.00, NULL);
INSERT INTO `business` VALUES (16, '粥员外', '建设路66号', '百种粥品，慢火熬制', 'sj06.png', 2, 8.00, 2.00, NULL);
INSERT INTO `business` VALUES (17, '麦香村烘焙', '文化路88号', '现烤面包，新鲜出炉', 'sj07.png', 2, 12.00, 3.00, NULL);
INSERT INTO `business` VALUES (18, '小杨生煎', '商业街12号', '上海生煎包，底脆汤多', 'sj08.png', 2, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (19, '馄饨王', '中山路34号', '大馄饨小馄饨，鲜美无比', 'sj09.png', 2, 10.00, 2.00, NULL);
INSERT INTO `business` VALUES (20, '山东煎饼铺', '解放路56号', '正宗山东杂粮煎饼', 'sj01.png', 2, 7.00, 1.00, NULL);
INSERT INTO `business` VALUES (21, '四季早餐店', '和平路78号', '营养早餐，现做现卖', 'sj02.png', 2, 8.00, 2.00, NULL);
INSERT INTO `business` VALUES (22, '闪电跑腿', '全市覆盖', '代买代送代排队，使命必达', 'sj03.png', 3, 5.00, 5.00, NULL);
INSERT INTO `business` VALUES (23, '飞毛腿代办', '城区最快', '30分钟极速送达', 'sj04.png', 3, 6.00, 4.00, NULL);
INSERT INTO `business` VALUES (24, '顺风速运', '同城配送专家', '安全可靠，准时送达', 'sj05.png', 3, 5.00, 5.00, NULL);
INSERT INTO `business` VALUES (25, '帮帮跑腿', '您的事我来办', '代购代办代取件', 'sj06.png', 3, 4.00, 4.00, NULL);
INSERT INTO `business` VALUES (26, '极速蜂鸟', '全城极速达', '专人专送，安全快捷', 'sj07.png', 3, 7.00, 6.00, NULL);
INSERT INTO `business` VALUES (27, '快马加鞭', '同城急送', '加急件优先配送', 'sj08.png', 3, 8.00, 8.00, NULL);
INSERT INTO `business` VALUES (28, '万能小哥', '什么都帮你做', '代买代送代排队代缴', 'sj09.png', 3, 5.00, 5.00, NULL);
INSERT INTO `business` VALUES (29, '随身办', '跑腿代办平台', '专业团队，随叫随到', 'sj01.png', 3, 5.00, 4.00, NULL);
INSERT INTO `business` VALUES (30, '急事帮', '急事急办', '加急30分钟必达', 'sj02.png', 3, 10.00, 6.00, NULL);
INSERT INTO `business` VALUES (31, '全城通', '全城无死角', '无论多远，使命必达', 'sj03.png', 3, 5.00, 5.00, NULL);
INSERT INTO `business` VALUES (32, '汉堡大王', '商业街88号', '现做现卖，肉饼多汁', 'sj04.png', 4, 25.00, 4.00, NULL);
INSERT INTO `business` VALUES (33, '必胜堡', '万达广场1楼', '意式风味披萨汉堡', 'sj05.png', 4, 30.00, 5.00, NULL);
INSERT INTO `business` VALUES (34, '麦香基', '步行街100号', '经典美式汉堡', 'sj06.png', 4, 22.00, 3.00, NULL);
INSERT INTO `business` VALUES (35, '意式披萨屋', '购物中心2楼', '手工现做披萨', 'sj07.png', 4, 28.00, 4.00, NULL);
INSERT INTO `business` VALUES (36, '炸鸡汉堡店', '美食城1楼', '炸鸡配汉堡，绝配', 'sj08.png', 4, 20.00, 3.00, NULL);
INSERT INTO `business` VALUES (37, '美式快餐', '大学城店', '学生最爱的汉堡', 'sj09.png', 4, 18.00, 2.00, NULL);
INSERT INTO `business` VALUES (38, '薄饼披萨', '银泰城B1', '意式薄底披萨', 'sj01.png', 4, 32.00, 4.00, NULL);
INSERT INTO `business` VALUES (39, '芝士工厂', 'CBD中心', '芝士就是力量', 'sj02.png', 4, 35.00, 5.00, NULL);
INSERT INTO `business` VALUES (40, '烤鸡堡', '社区店', '现烤鸡肉汉堡', 'sj03.png', 4, 20.00, 3.00, NULL);
INSERT INTO `business` VALUES (41, '双层牛堡', '火车站店', '双层牛肉饼满足你', 'sj04.png', 4, 28.00, 4.00, NULL);
INSERT INTO `business` VALUES (42, '甜蜜时光', '步行街33号', '手工甜品，每日新鲜制作', 'sj05.png', 5, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (43, '茶颜悦色', '商业街12号', '新中式鲜茶，好喝不腻', 'sj06.png', 5, 12.00, 2.00, NULL);
INSERT INTO `business` VALUES (44, '满记甜品', '万达广场3楼', '港式甜品专家', 'sj07.png', 5, 18.00, 3.00, NULL);
INSERT INTO `business` VALUES (45, '许留山', '银泰城1楼', '芒果甜品专门店', 'sj08.png', 5, 20.00, 3.00, NULL);
INSERT INTO `business` VALUES (46, '鲜芋仙', '购物中心B1', '台式芋圆甜品', 'sj09.png', 5, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (47, '蜜雪冰城', '大学城店', '高性价比饮品', 'sj01.png', 5, 6.00, 1.00, NULL);
INSERT INTO `business` VALUES (48, '喜茶', 'CBD旗舰店', '灵感之茶', 'sj02.png', 5, 20.00, 3.00, NULL);
INSERT INTO `business` VALUES (49, '奈雪的茶', '万象城1楼', '一杯好茶一口软欧包', 'sj03.png', 5, 22.00, 3.00, NULL);
INSERT INTO `business` VALUES (50, '星巴克', '商务中心店', '咖啡文化体验', 'sj04.png', 5, 25.00, 4.00, NULL);
INSERT INTO `business` VALUES (51, '瑞幸咖啡', '写字楼1楼', ' luckin coffee 幸运咖啡', 'sj05.png', 5, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (52, '速食客', '科技园1楼', '快速出餐，上班族首选', 'sj06.png', 6, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (53, '真功夫', '写字楼B1', '中式快餐，营养蒸品', 'sj07.png', 6, 18.00, 2.00, NULL);
INSERT INTO `business` VALUES (54, '吉野家', '商场B1', '日式牛丼饭', 'sj08.png', 6, 20.00, 3.00, NULL);
INSERT INTO `business` VALUES (55, '沙县小吃', '社区店', '国民小吃，经济实惠', 'sj09.png', 6, 10.00, 1.00, NULL);
INSERT INTO `business` VALUES (56, '兰州拉面王', '街边店', '手工拉面，汤清面劲', 'sj01.png', 6, 12.00, 2.00, NULL);
INSERT INTO `business` VALUES (57, '黄焖鸡米饭', '美食城店', '秘制酱料，一绝', 'sj02.png', 6, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (58, '杨国福麻辣烫', '大学城店', '自选麻辣烫', 'sj03.png', 6, 16.00, 2.00, NULL);
INSERT INTO `business` VALUES (59, '张亮麻辣烫', '社区店', '骨汤麻辣烫', 'sj04.png', 6, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (60, '大娘水饺', '商业街店', '手工水饺，家的味道', 'sj05.png', 6, 12.00, 2.00, NULL);
INSERT INTO `business` VALUES (61, '老乡鸡', '写字楼店', '中式快餐连锁', 'sj06.png', 6, 18.00, 2.00, NULL);
INSERT INTO `business` VALUES (62, '湘味小厨', '美食城2楼', '地道湖南风味，香辣过瘾', 'sj07.png', 7, 22.00, 3.00, NULL);
INSERT INTO `business` VALUES (63, '西安肉夹馍', '老街店', '正宗陕西风味', 'sj08.png', 7, 12.00, 2.00, NULL);
INSERT INTO `business` VALUES (64, '重庆小面', '街边总店', '麻辣鲜香重庆味', 'sj09.png', 7, 10.00, 2.00, NULL);
INSERT INTO `business` VALUES (65, '柳州螺蛳粉', '大学城店', '酸辣鲜爽，灵魂美食', 'sj01.png', 7, 12.00, 2.00, NULL);
INSERT INTO `business` VALUES (66, '四川担担面', '美食街店', '正宗川味面食', 'sj02.png', 7, 11.00, 2.00, NULL);
INSERT INTO `business` VALUES (67, '武汉热干面', '社区店', '正宗武汉过早', 'sj03.png', 7, 8.00, 1.00, NULL);
INSERT INTO `business` VALUES (68, '新疆烤羊肉', '夜市店', '炭火现烤，香气四溢', 'sj04.png', 7, 30.00, 5.00, NULL);
INSERT INTO `business` VALUES (69, '北京炸酱面', '胡同口店', '老北京地道味', 'sj05.png', 7, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (70, '云南过桥米线', '商场B1', '正宗过桥米线', 'sj06.png', 7, 20.00, 3.00, NULL);
INSERT INTO `business` VALUES (71, '广东肠粉王', '社区店', '现蒸肠粉，滑嫩爽口', 'sj07.png', 7, 10.00, 2.00, NULL);
INSERT INTO `business` VALUES (72, '一碗好面', '学院路56号', '手工拉面，汤鲜面劲道', 'sj08.png', 8, 18.00, 2.00, NULL);
INSERT INTO `business` VALUES (73, '重庆小面馆', '解放路店', '正宗麻辣小面', 'sj09.png', 8, 12.00, 2.00, NULL);
INSERT INTO `business` VALUES (74, '山西刀削面', '美食街店', '刀削面一绝', 'sj01.png', 8, 13.00, 2.00, NULL);
INSERT INTO `business` VALUES (75, '潮汕粿条', '南方路店', '潮汕地道风味', 'sj02.png', 8, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (76, '桂林米粉', '商业街店', '正宗桂林卤粉', 'sj03.png', 8, 12.00, 2.00, NULL);
INSERT INTO `business` VALUES (77, '遵义羊肉粉', '老街店', '贵州羊肉粉', 'sj04.png', 8, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (78, '延吉冷面', '韩国街店', '朝鲜族冷面', 'sj05.png', 8, 16.00, 3.00, NULL);
INSERT INTO `business` VALUES (79, '厦门沙茶面', '闽南风味店', '沙茶汤底浓郁', 'sj06.png', 8, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (80, '南洋叻沙', '商场B1', '新加坡风味', 'sj07.png', 8, 22.00, 3.00, NULL);
INSERT INTO `business` VALUES (81, '襄阳牛肉面', '火车站店', '正宗襄阳味', 'sj08.png', 8, 16.00, 2.00, NULL);
INSERT INTO `business` VALUES (82, '老字号包子铺', '老街口', '三代传承，皮薄馅大', 'sj09.png', 9, 8.00, 1.00, NULL);
INSERT INTO `business` VALUES (83, '庆丰包子铺', '社区店', '京城老字号', 'sj01.png', 9, 10.00, 2.00, NULL);
INSERT INTO `business` VALUES (84, '狗不理包子', '商业街旗舰店', '天津三绝之一', 'sj02.png', 9, 15.00, 3.00, NULL);
INSERT INTO `business` VALUES (85, '粥公粥婆', '美食城店', '百种粥品，温暖你的胃', 'sj03.png', 9, 8.00, 1.00, NULL);
INSERT INTO `business` VALUES (86, '清晨粥道', '社区店', '清晨一碗粥，精神一整天', 'sj04.png', 9, 7.00, 1.00, NULL);
INSERT INTO `business` VALUES (87, '蒸功夫包子', '大学城店', '现包现蒸，鲜香可口', 'sj05.png', 9, 6.00, 1.00, NULL);
INSERT INTO `business` VALUES (88, '鼎泰丰', 'CBD旗舰店', '小笼包中的爱马仕', 'sj06.png', 9, 30.00, 5.00, NULL);
INSERT INTO `business` VALUES (89, '南翔小笼', '老街店', '上海味道，蟹粉小笼', 'sj07.png', 9, 20.00, 3.00, NULL);
INSERT INTO `business` VALUES (90, '甘其食', '社区店', '健康包子品牌', 'sj08.png', 9, 8.00, 2.00, NULL);
INSERT INTO `business` VALUES (91, '金陵灌汤包', '美食街店', '南京风味灌汤包', 'sj09.png', 9, 12.00, 2.00, NULL);
INSERT INTO `business` VALUES (92, '炸鸡星球', '步行街总店', '韩式炸鸡，外酥里嫩', 'sj01.png', 10, 20.00, 3.00, NULL);
INSERT INTO `business` VALUES (93, '正新鸡排', '商业街店', '大鸡排，实惠美味', 'sj02.png', 10, 12.00, 2.00, NULL);
INSERT INTO `business` VALUES (94, '叫了个炸鸡', '大学城店', '整只炸鸡，幸福满满', 'sj03.png', 10, 25.00, 3.00, NULL);
INSERT INTO `business` VALUES (95, '第一佳炸鸡', '社区店', '台式炸鸡排', 'sj04.png', 10, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (96, '肯德炸鸡', '商场B1', '美式炸鸡专家', 'sj05.png', 10, 22.00, 3.00, NULL);
INSERT INTO `business` VALUES (97, '老北京炸串', '胡同口店', '地道老北京炸串', 'sj06.png', 10, 8.00, 1.00, NULL);
INSERT INTO `business` VALUES (98, '桥头排骨', '老街店', '秘制炸排骨一绝', 'sj07.png', 10, 15.00, 2.00, NULL);
INSERT INTO `business` VALUES (99, '韩国炸鸡屋', '韩国街店', '正宗韩式炸鸡啤酒', 'sj08.png', 10, 28.00, 4.00, NULL);
INSERT INTO `business` VALUES (100, '烧烤部落', '夜市店', '炸串烧烤一应俱全', 'sj09.png', 10, 10.00, 2.00, NULL);
INSERT INTO `business` VALUES (101, '鸡排达人', '学校门口', '学生最爱的鸡排', 'sj01.png', 10, 10.00, 1.00, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of deliveryaddress
-- ----------------------------
INSERT INTO `deliveryaddress` VALUES (9, 'zdq', 0, '12345678911', '广州市天河区', '1');
INSERT INTO `deliveryaddress` VALUES (10, 'useral3', 1, '12345678', '广东省广州市', 'useral3');

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite`  (
  `favoriteId` int NOT NULL AUTO_INCREMENT,
  `userId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `businessId` int NOT NULL,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`favoriteId`) USING BTREE,
  UNIQUE INDEX `uk_user_biz`(`userId` ASC, `businessId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favorite
-- ----------------------------
INSERT INTO `favorite` VALUES (3, 'useral3', 5, '2026-07-14 15:20:27');

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
) ENGINE = InnoDB AUTO_INCREMENT = 207 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of food
-- ----------------------------
INSERT INTO `food` VALUES (1, '麻辣烫小份', '自选6种菜，微辣', 'img1.png', 18.00, 1, NULL);
INSERT INTO `food` VALUES (2, '麻辣烫大份', '自选10种菜', 'img2.png', 28.00, 1, NULL);
INSERT INTO `food` VALUES (3, '酸辣粉', '重庆风味', 'img3.png', 12.00, 1, NULL);
INSERT INTO `food` VALUES (4, '红油抄手', '手工包制', 'img4.png', 15.00, 1, NULL);
INSERT INTO `food` VALUES (9, '剁椒鱼头', '鲜辣入味', 'img1.png', 48.00, 3, NULL);
INSERT INTO `food` VALUES (10, '辣椒炒肉', '湖南招牌', 'img2.png', 28.00, 3, NULL);
INSERT INTO `food` VALUES (11, '蒜蓉空心菜', '清爽解腻', 'img3.png', 12.00, 3, NULL);
INSERT INTO `food` VALUES (12, '农家小炒肉', '香辣下饭', 'img4.png', 22.00, 3, NULL);
INSERT INTO `food` VALUES (13, '叉烧饭', '蜜汁叉烧', 'img1.png', 22.00, 4, NULL);
INSERT INTO `food` VALUES (14, '烧鸭饭', '皮脆肉嫩', 'img2.png', 25.00, 4, NULL);
INSERT INTO `food` VALUES (15, '白切鸡', '原汁原味', 'img3.png', 28.00, 4, NULL);
INSERT INTO `food` VALUES (16, '铁锅炖大鹅', '东北硬菜', 'img4.png', 58.00, 5, NULL);
INSERT INTO `food` VALUES (17, '锅包肉', '酸甜酥脆', 'img1.png', 28.00, 5, NULL);
INSERT INTO `food` VALUES (18, '地三鲜', '素菜经典', 'img2.png', 16.00, 5, NULL);
INSERT INTO `food` VALUES (19, '牛肉拉面', '一清二白', 'img3.png', 15.00, 6, NULL);
INSERT INTO `food` VALUES (20, '大盘鸡', '西北风味', 'img4.png', 38.00, 6, NULL);
INSERT INTO `food` VALUES (21, '九转大肠', '鲁菜名品', 'img1.png', 45.00, 7, NULL);
INSERT INTO `food` VALUES (22, '葱烧海参', '高端鲁菜', 'img2.png', 68.00, 7, NULL);
INSERT INTO `food` VALUES (23, '松鼠桂鱼', '苏帮名菜', 'img3.png', 52.00, 8, NULL);
INSERT INTO `food` VALUES (24, '红烧狮子头', '肥而不腻', 'img4.png', 32.00, 8, NULL);
INSERT INTO `food` VALUES (25, '清蒸石斑鱼', '新鲜海味', 'img1.png', 68.00, 9, NULL);
INSERT INTO `food` VALUES (26, '海蛎煎', '闽南小吃', 'img2.png', 18.00, 9, NULL);
INSERT INTO `food` VALUES (27, '臭鳜鱼', '徽派名品', 'img3.png', 48.00, 10, NULL);
INSERT INTO `food` VALUES (28, '毛豆腐', '徽州特色', 'img4.png', 15.00, 10, NULL);
INSERT INTO `food` VALUES (29, '豆浆油条套餐', '现磨豆浆+油条', 'img1.png', 8.00, 11, NULL);
INSERT INTO `food` VALUES (30, '鲜肉小笼包', '一笼8个', 'img2.png', 12.00, 11, NULL);
INSERT INTO `food` VALUES (31, '鸡蛋灌饼', '加蛋加火腿', 'img3.png', 7.00, 11, NULL);
INSERT INTO `food` VALUES (32, '饭团套餐', '紫米饭团+豆浆', 'img1.png', 10.00, 12, NULL);
INSERT INTO `food` VALUES (33, '咸豆浆', '传统风味', 'img2.png', 5.00, 12, NULL);
INSERT INTO `food` VALUES (34, '鲜肉大包', '现包现蒸', 'img3.png', 3.00, 13, NULL);
INSERT INTO `food` VALUES (35, '三丁包', '鸡肉笋丁', 'img4.png', 3.50, 13, NULL);
INSERT INTO `food` VALUES (36, '杂粮煎饼', '加薄脆', 'img1.png', 6.00, 14, NULL);
INSERT INTO `food` VALUES (37, '手抓饼', '加鸡蛋', 'img2.png', 7.00, 14, NULL);
INSERT INTO `food` VALUES (38, '皮蛋瘦肉粥', '慢熬两小时', 'img3.png', 9.00, 15, NULL);
INSERT INTO `food` VALUES (39, '南瓜小米粥', '养胃暖身', 'img4.png', 7.00, 15, NULL);
INSERT INTO `food` VALUES (40, '全麦吐司', '现烤出炉', 'img1.png', 12.00, 16, NULL);
INSERT INTO `food` VALUES (41, '牛角包', '黄油香气', 'img2.png', 8.00, 16, NULL);
INSERT INTO `food` VALUES (42, '招牌生煎', '底脆汤多', 'img3.png', 15.00, 17, NULL);
INSERT INTO `food` VALUES (43, '虾仁生煎', '鲜虾馅料', 'img4.png', 18.00, 17, NULL);
INSERT INTO `food` VALUES (44, '鲜肉大馄饨', '手工包制', 'img1.png', 12.00, 18, NULL);
INSERT INTO `food` VALUES (45, '虾肉小馄饨', '鲜嫩爽滑', 'img2.png', 10.00, 18, NULL);
INSERT INTO `food` VALUES (46, '五谷煎饼', '山东风味', 'img3.png', 7.00, 19, NULL);
INSERT INTO `food` VALUES (47, '鸡蛋饼', '软嫩可口', 'img4.png', 5.00, 19, NULL);
INSERT INTO `food` VALUES (48, '营养三明治', '蔬菜鸡蛋', 'img1.png', 10.00, 20, NULL);
INSERT INTO `food` VALUES (49, '牛奶燕麦', '健康早餐', 'img2.png', 8.00, 20, NULL);
INSERT INTO `food` VALUES (50, '代买服务', '超市/药店代买', 'img1.png', 10.00, 21, NULL);
INSERT INTO `food` VALUES (51, '代送文件', '同城急送', 'img2.png', 15.00, 21, NULL);
INSERT INTO `food` VALUES (52, '排队代办', '按小时计费', 'img3.png', 20.00, 21, NULL);
INSERT INTO `food` VALUES (53, '急件代送', '30分钟送达', 'img4.png', 18.00, 22, NULL);
INSERT INTO `food` VALUES (54, '代买药品', '正规药店采购', 'img1.png', 12.00, 22, NULL);
INSERT INTO `food` VALUES (55, '文件专送', '保密配送', 'img2.png', 16.00, 23, NULL);
INSERT INTO `food` VALUES (56, '生鲜代买', '菜场超市代购', 'img3.png', 15.00, 23, NULL);
INSERT INTO `food` VALUES (57, '代取快递', '送货上门', 'img4.png', 8.00, 24, NULL);
INSERT INTO `food` VALUES (58, '代缴费用', '水电煤代缴', 'img1.png', 5.00, 24, NULL);
INSERT INTO `food` VALUES (59, '专人急送', '全程跟踪', 'img2.png', 25.00, 25, NULL);
INSERT INTO `food` VALUES (60, '加急配送', '30分钟必达', 'img3.png', 20.00, 26, NULL);
INSERT INTO `food` VALUES (61, '帮买帮送', '一站式代办', 'img4.png', 12.00, 27, NULL);
INSERT INTO `food` VALUES (62, '排队取号', '医院银行排队', 'img1.png', 15.00, 28, NULL);
INSERT INTO `food` VALUES (63, '急事急办', '2小时完成', 'img2.png', 30.00, 29, NULL);
INSERT INTO `food` VALUES (64, '全城配送', '不限距离', 'img3.png', 10.00, 30, NULL);
INSERT INTO `food` VALUES (65, '经典牛肉堡', '厚切牛肉饼+芝士', 'img1.png', 25.00, 31, NULL);
INSERT INTO `food` VALUES (66, '香辣鸡腿堡', '现炸鸡腿肉', 'img2.png', 22.00, 31, NULL);
INSERT INTO `food` VALUES (67, '薯条大份', '金黄酥脆', 'img3.png', 10.00, 31, NULL);
INSERT INTO `food` VALUES (68, '可乐中杯', '冰爽畅饮', 'img4.png', 6.00, 31, NULL);
INSERT INTO `food` VALUES (69, '至尊披萨', '12寸意式经典', 'img1.png', 48.00, 32, NULL);
INSERT INTO `food` VALUES (70, '海鲜披萨', '虾仁+鱿鱼', 'img2.png', 55.00, 32, NULL);
INSERT INTO `food` VALUES (71, '双层芝士堡', '双倍芝士', 'img3.png', 28.00, 33, NULL);
INSERT INTO `food` VALUES (72, '麦辣鸡翅', '4只装', 'img4.png', 12.00, 33, NULL);
INSERT INTO `food` VALUES (73, '玛格丽特披萨', '经典番茄芝士', 'img1.png', 35.00, 34, NULL);
INSERT INTO `food` VALUES (74, '培根披萨', '培根+蘑菇', 'img2.png', 42.00, 34, NULL);
INSERT INTO `food` VALUES (75, '脆皮炸鸡', '整只炸鸡', 'img3.png', 32.00, 35, NULL);
INSERT INTO `food` VALUES (76, '鸡米花', '一口一个', 'img4.png', 10.00, 35, NULL);
INSERT INTO `food` VALUES (77, '美式热狗', '经典美式', 'img1.png', 15.00, 36, NULL);
INSERT INTO `food` VALUES (78, '洋葱圈', '酥脆可口', 'img2.png', 8.00, 36, NULL);
INSERT INTO `food` VALUES (79, '榴莲披萨', '榴莲控最爱', 'img3.png', 58.00, 37, NULL);
INSERT INTO `food` VALUES (80, '萨拉米披萨', '意式经典', 'img4.png', 45.00, 37, NULL);
INSERT INTO `food` VALUES (81, '芝士焗饭', '拉丝芝士', 'img1.png', 28.00, 38, NULL);
INSERT INTO `food` VALUES (82, '奶油意面', '白酱意面', 'img2.png', 25.00, 38, NULL);
INSERT INTO `food` VALUES (83, '烤鸡堡', '现烤鸡腿', 'img3.png', 20.00, 39, NULL);
INSERT INTO `food` VALUES (84, '双层牛堡', '双倍满足', 'img4.png', 32.00, 40, NULL);
INSERT INTO `food` VALUES (85, '提拉米苏', '意大利经典', 'img1.png', 18.00, 41, NULL);
INSERT INTO `food` VALUES (86, '芒果慕斯', '新鲜芒果', 'img2.png', 16.00, 41, NULL);
INSERT INTO `food` VALUES (87, '杨枝甘露', '港式经典', 'img3.png', 14.00, 41, NULL);
INSERT INTO `food` VALUES (88, '双皮奶', '顺德传统', 'img4.png', 12.00, 41, NULL);
INSERT INTO `food` VALUES (89, '幽兰拿铁', '招牌必点', 'img1.png', 16.00, 42, NULL);
INSERT INTO `food` VALUES (90, '声声乌龙', '清爽茶香', 'img2.png', 14.00, 42, NULL);
INSERT INTO `food` VALUES (91, '芒果班戟', '港式经典', 'img3.png', 22.00, 43, NULL);
INSERT INTO `food` VALUES (92, '榴莲忘返', '榴莲控最爱', 'img4.png', 28.00, 43, NULL);
INSERT INTO `food` VALUES (93, '多芒小丸子', 'Q弹软糯', 'img1.png', 25.00, 44, NULL);
INSERT INTO `food` VALUES (94, '芒果西米露', '夏日清凉', 'img2.png', 20.00, 44, NULL);
INSERT INTO `food` VALUES (95, '芋圆4号', '招牌芋圆', 'img3.png', 18.00, 45, NULL);
INSERT INTO `food` VALUES (96, '仙草冰', '消暑解热', 'img4.png', 15.00, 45, NULL);
INSERT INTO `food` VALUES (97, '柠檬水', '冰爽解渴', 'img1.png', 4.00, 46, NULL);
INSERT INTO `food` VALUES (98, '冰淇淋', '奶香浓郁', 'img2.png', 3.00, 46, NULL);
INSERT INTO `food` VALUES (99, '多肉葡萄', '果肉满满', 'img3.png', 25.00, 47, NULL);
INSERT INTO `food` VALUES (100, '芝芝莓莓', '芝士奶盖', 'img4.png', 28.00, 47, NULL);
INSERT INTO `food` VALUES (101, '霸气橙子', '整颗鲜橙', 'img1.png', 25.00, 48, NULL);
INSERT INTO `food` VALUES (102, '抹茶芝士', '日式抹茶', 'img2.png', 28.00, 48, NULL);
INSERT INTO `food` VALUES (103, '拿铁咖啡', '经典意式', 'img3.png', 28.00, 49, NULL);
INSERT INTO `food` VALUES (104, '抹茶星冰乐', '夏日冰饮', 'img4.png', 32.00, 49, NULL);
INSERT INTO `food` VALUES (105, '生椰拿铁', '椰香浓郁', 'img1.png', 18.00, 50, NULL);
INSERT INTO `food` VALUES (106, '陨石拿铁', '黑糖风味', 'img2.png', 20.00, 50, NULL);
INSERT INTO `food` VALUES (107, '黄焖鸡米饭', '秘制酱料', 'img1.png', 18.00, 51, NULL);
INSERT INTO `food` VALUES (108, '鱼香肉丝饭', '经典盖饭', 'img2.png', 16.00, 51, NULL);
INSERT INTO `food` VALUES (109, '番茄鸡蛋面', '家常味道', 'img3.png', 12.00, 51, NULL);
INSERT INTO `food` VALUES (110, '香菇滑鸡', '蒸品营养', 'img1.png', 22.00, 52, NULL);
INSERT INTO `food` VALUES (111, '排骨饭', '豉汁蒸排骨', 'img2.png', 25.00, 52, NULL);
INSERT INTO `food` VALUES (112, '牛肉饭', '日式牛丼', 'img3.png', 22.00, 53, NULL);
INSERT INTO `food` VALUES (113, '照烧鸡腿饭', '日式照烧', 'img4.png', 20.00, 53, NULL);
INSERT INTO `food` VALUES (114, '飘香拌面', '花生酱拌面', 'img1.png', 7.00, 54, NULL);
INSERT INTO `food` VALUES (115, '扁肉汤', '沙县经典', 'img2.png', 6.00, 54, NULL);
INSERT INTO `food` VALUES (116, '牛肉拉面', '一清二白', 'img3.png', 12.00, 55, NULL);
INSERT INTO `food` VALUES (117, '炒刀削面', '劲道Q弹', 'img4.png', 14.00, 55, NULL);
INSERT INTO `food` VALUES (118, '黄焖鸡大份', '加量不加价', 'img1.png', 20.00, 56, NULL);
INSERT INTO `food` VALUES (119, '麻辣烫自选', '荤素任选', 'img2.png', 16.00, 57, NULL);
INSERT INTO `food` VALUES (120, '骨汤麻辣烫', '浓郁骨汤', 'img3.png', 15.00, 58, NULL);
INSERT INTO `food` VALUES (121, '猪肉白菜饺', '15只装', 'img4.png', 14.00, 59, NULL);
INSERT INTO `food` VALUES (122, '韭菜鸡蛋饺', '素馅经典', 'img1.png', 12.00, 59, NULL);
INSERT INTO `food` VALUES (123, '肥西老母鸡汤', '招牌炖品', 'img2.png', 22.00, 60, NULL);
INSERT INTO `food` VALUES (124, '农家小炒肉饭', '下饭神器', 'img3.png', 18.00, 60, NULL);
INSERT INTO `food` VALUES (125, '辣椒炒肉', '湖南招牌', 'img1.png', 28.00, 61, NULL);
INSERT INTO `food` VALUES (126, '剁椒鱼头', '鲜辣入味', 'img2.png', 48.00, 61, NULL);
INSERT INTO `food` VALUES (127, '腊肉炒蒜苔', '湘西风味', 'img3.png', 26.00, 61, NULL);
INSERT INTO `food` VALUES (128, '肉夹馍', '卤肉+馍', 'img4.png', 10.00, 62, NULL);
INSERT INTO `food` VALUES (129, '羊肉泡馍', '汤鲜肉烂', 'img1.png', 25.00, 62, NULL);
INSERT INTO `food` VALUES (130, '凉皮', '夏日爽口', 'img2.png', 8.00, 62, NULL);
INSERT INTO `food` VALUES (131, '麻辣小面', '麻辣鲜香', 'img3.png', 10.00, 63, NULL);
INSERT INTO `food` VALUES (132, '豌杂面', '豌豆杂酱', 'img4.png', 13.00, 63, NULL);
INSERT INTO `food` VALUES (133, '原味螺蛳粉', '酸辣鲜爽', 'img1.png', 12.00, 64, NULL);
INSERT INTO `food` VALUES (134, '干捞螺蛳粉', '无汤版本', 'img2.png', 14.00, 64, NULL);
INSERT INTO `food` VALUES (135, '担担面', '四川名面', 'img3.png', 12.00, 65, NULL);
INSERT INTO `food` VALUES (136, '红油抄手', '麻辣鲜香', 'img4.png', 14.00, 65, NULL);
INSERT INTO `food` VALUES (137, '热干面', '芝麻酱拌面', 'img1.png', 8.00, 66, NULL);
INSERT INTO `food` VALUES (138, '三鲜豆皮', '武汉名小吃', 'img2.png', 10.00, 66, NULL);
INSERT INTO `food` VALUES (139, '羊肉串x10', '现烤羊肉', 'img3.png', 35.00, 67, NULL);
INSERT INTO `food` VALUES (140, '烤羊排', '外焦里嫩', 'img4.png', 58.00, 67, NULL);
INSERT INTO `food` VALUES (141, '老北京炸酱面', '经典口味', 'img1.png', 15.00, 68, NULL);
INSERT INTO `food` VALUES (142, '过桥米线', '鸡汤烫熟', 'img2.png', 22.00, 69, NULL);
INSERT INTO `food` VALUES (143, '秀才米线', '丰富配菜', 'img3.png', 18.00, 69, NULL);
INSERT INTO `food` VALUES (144, '鲜虾肠粉', '虾仁满满', 'img4.png', 14.00, 70, NULL);
INSERT INTO `food` VALUES (145, '牛肉肠粉', '滑嫩可口', 'img1.png', 12.00, 70, NULL);
INSERT INTO `food` VALUES (146, '红烧牛肉面', '大块牛肉+手工面', 'img1.png', 20.00, 71, NULL);
INSERT INTO `food` VALUES (147, '炸酱面', '老北京风味', 'img2.png', 15.00, 71, NULL);
INSERT INTO `food` VALUES (148, '西红柿鸡蛋面', '清淡鲜美', 'img3.png', 12.00, 71, NULL);
INSERT INTO `food` VALUES (149, '重庆小面', '麻辣鲜香', 'img4.png', 12.00, 72, NULL);
INSERT INTO `food` VALUES (150, '肥肠面', '软糯入味', 'img1.png', 16.00, 72, NULL);
INSERT INTO `food` VALUES (151, '刀削面', '劲道爽滑', 'img2.png', 13.00, 73, NULL);
INSERT INTO `food` VALUES (152, '炒刀削', '镬气十足', 'img3.png', 15.00, 73, NULL);
INSERT INTO `food` VALUES (153, '牛肉粿条', '潮汕经典', 'img4.png', 18.00, 74, NULL);
INSERT INTO `food` VALUES (154, '猪杂汤粿条', '鲜甜汤底', 'img1.png', 15.00, 74, NULL);
INSERT INTO `food` VALUES (155, '桂林卤粉', '卤水香浓', 'img2.png', 12.00, 75, NULL);
INSERT INTO `food` VALUES (156, '酸辣粉', '桂林风味', 'img3.png', 10.00, 75, NULL);
INSERT INTO `food` VALUES (157, '遵义羊肉粉', '汤鲜肉嫩', 'img4.png', 16.00, 76, NULL);
INSERT INTO `food` VALUES (158, '花溪牛肉粉', '贵州名粉', 'img1.png', 15.00, 76, NULL);
INSERT INTO `food` VALUES (159, '冷面', '酸甜爽口', 'img2.png', 16.00, 77, NULL);
INSERT INTO `food` VALUES (160, '石锅拌饭', '韩式经典', 'img3.png', 22.00, 77, NULL);
INSERT INTO `food` VALUES (161, '沙茶面', '浓郁沙茶汤', 'img4.png', 15.00, 78, NULL);
INSERT INTO `food` VALUES (162, '海鲜沙茶面', '虾蟹贝类', 'img1.png', 25.00, 78, NULL);
INSERT INTO `food` VALUES (163, '叻沙面', '椰香浓郁', 'img2.png', 25.00, 79, NULL);
INSERT INTO `food` VALUES (164, '海南鸡饭', '新马经典', 'img3.png', 22.00, 79, NULL);
INSERT INTO `food` VALUES (165, '襄阳牛肉面', '一辣二麻三鲜', 'img4.png', 16.00, 80, NULL);
INSERT INTO `food` VALUES (166, '鲜肉大包', '皮薄馅大', 'img1.png', 3.00, 81, NULL);
INSERT INTO `food` VALUES (167, '三鲜包子', '虾仁猪肉', 'img2.png', 4.00, 81, NULL);
INSERT INTO `food` VALUES (168, '豆沙包', '香甜绵密', 'img3.png', 2.50, 81, NULL);
INSERT INTO `food` VALUES (169, '小米粥', '养胃暖身', 'img4.png', 3.00, 81, NULL);
INSERT INTO `food` VALUES (170, '猪肉大葱包', '京城经典', 'img1.png', 3.50, 82, NULL);
INSERT INTO `food` VALUES (171, '梅干菜肉包', '香气扑鼻', 'img2.png', 3.50, 82, NULL);
INSERT INTO `food` VALUES (172, '三鲜包', '鲜香味美', 'img3.png', 6.00, 83, NULL);
INSERT INTO `food` VALUES (173, '酱肉包', '秘制酱料', 'img4.png', 8.00, 83, NULL);
INSERT INTO `food` VALUES (174, '白粥', '清淡养胃', 'img1.png', 3.00, 84, NULL);
INSERT INTO `food` VALUES (175, '八宝粥', '料足香甜', 'img2.png', 6.00, 84, NULL);
INSERT INTO `food` VALUES (176, '皮蛋瘦肉粥', '经典搭配', 'img3.png', 8.00, 85, NULL);
INSERT INTO `food` VALUES (177, '香菇鸡肉粥', '鲜香营养', 'img4.png', 7.00, 85, NULL);
INSERT INTO `food` VALUES (178, '鲜汁肉包', '一口爆汁', 'img1.png', 3.00, 86, NULL);
INSERT INTO `food` VALUES (179, '韭菜鸡蛋包', '素食经典', 'img2.png', 2.50, 86, NULL);
INSERT INTO `food` VALUES (180, '蟹粉小笼', '招牌必点', 'img3.png', 45.00, 87, NULL);
INSERT INTO `food` VALUES (181, '鲜肉小笼', '薄皮多汁', 'img4.png', 25.00, 87, NULL);
INSERT INTO `food` VALUES (182, '蟹黄汤包', '蟹香浓郁', 'img1.png', 28.00, 88, NULL);
INSERT INTO `food` VALUES (183, '鲜肉汤包', '汤汁鲜美', 'img2.png', 18.00, 88, NULL);
INSERT INTO `food` VALUES (184, '素菜包', '健康轻食', 'img3.png', 2.50, 89, NULL);
INSERT INTO `food` VALUES (185, '灌汤包', '一咬爆汁', 'img4.png', 14.00, 90, NULL);
INSERT INTO `food` VALUES (186, '鸡汁汤包', '鸡汤冻制成', 'img1.png', 16.00, 90, NULL);
INSERT INTO `food` VALUES (187, '韩式炸鸡', '甜辣酱/原味', 'img1.png', 28.00, 91, NULL);
INSERT INTO `food` VALUES (188, '芝士炸鸡', '芝士粉撒满', 'img2.png', 32.00, 91, NULL);
INSERT INTO `food` VALUES (189, '鸡排大份', '比脸还大', 'img3.png', 15.00, 92, NULL);
INSERT INTO `food` VALUES (190, '香辣鸡排', '微辣酥脆', 'img4.png', 15.00, 92, NULL);
INSERT INTO `food` VALUES (191, '整只炸鸡', '现炸出炉', 'img1.png', 28.00, 93, NULL);
INSERT INTO `food` VALUES (192, '鸡翅桶', '10只装', 'img2.png', 25.00, 93, NULL);
INSERT INTO `food` VALUES (193, '台式大鸡排', '酥脆鲜嫩', 'img3.png', 16.00, 94, NULL);
INSERT INTO `food` VALUES (194, '甘梅地瓜', '酸甜美', 'img4.png', 8.00, 94, NULL);
INSERT INTO `food` VALUES (195, '美式炸鸡桶', '6块装', 'img1.png', 35.00, 95, NULL);
INSERT INTO `food` VALUES (196, '鸡米花', '一口一个', 'img2.png', 10.00, 95, NULL);
INSERT INTO `food` VALUES (197, '炸串混搭x10', '荤素搭配', 'img3.png', 10.00, 96, NULL);
INSERT INTO `food` VALUES (198, '炸年糕', '外酥里糯', 'img4.png', 5.00, 96, NULL);
INSERT INTO `food` VALUES (199, '桥头排骨', '秘制配方', 'img1.png', 18.00, 97, NULL);
INSERT INTO `food` VALUES (200, '酥肉小份', '现炸酥肉', 'img2.png', 12.00, 97, NULL);
INSERT INTO `food` VALUES (201, '原味炸鸡', '半只装', 'img3.png', 25.00, 98, NULL);
INSERT INTO `food` VALUES (202, '蒜香酱油炸鸡', '韩式经典', 'img4.png', 30.00, 98, NULL);
INSERT INTO `food` VALUES (203, '烤串x20', '牛羊肉各半', 'img1.png', 25.00, 99, NULL);
INSERT INTO `food` VALUES (204, '炸鸡皮', '香脆可口', 'img2.png', 8.00, 99, NULL);
INSERT INTO `food` VALUES (205, '大鸡排', '酥脆多汁', 'img3.png', 12.00, 100, NULL);
INSERT INTO `food` VALUES (206, '盐酥鸡', '台式小吃', 'img4.png', 10.00, 100, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderdetailet
-- ----------------------------
INSERT INTO `orderdetailet` VALUES (64, 67, 34, 4);
INSERT INTO `orderdetailet` VALUES (65, 68, 35, 4);
INSERT INTO `orderdetailet` VALUES (66, 68, 34, 1);
INSERT INTO `orderdetailet` VALUES (67, 69, 154, 1);
INSERT INTO `orderdetailet` VALUES (68, 70, 157, 1);
INSERT INTO `orderdetailet` VALUES (69, 71, 1, 1);
INSERT INTO `orderdetailet` VALUES (70, 71, 2, 1);
INSERT INTO `orderdetailet` VALUES (71, 72, 16, 1);
INSERT INTO `orderdetailet` VALUES (72, 73, 2, 2);
INSERT INTO `orderdetailet` VALUES (73, 74, 1, 2);
INSERT INTO `orderdetailet` VALUES (74, 75, 1, 2);
INSERT INTO `orderdetailet` VALUES (75, 76, 84, 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (67, '1', 13, '2026-07-14 15:01:57', 14.00, 9, 1);
INSERT INTO `orders` VALUES (68, '1', 13, '2026-07-14 15:07:29', 19.00, 9, 1);
INSERT INTO `orders` VALUES (69, '1', 74, '2026-07-14 15:07:45', 17.00, 9, 1);
INSERT INTO `orders` VALUES (70, '1', 76, '2026-07-14 15:07:56', 18.00, 9, 1);
INSERT INTO `orders` VALUES (71, 'useral3', 1, '2026-07-14 15:21:42', 49.00, 10, 2);
INSERT INTO `orders` VALUES (72, 'useral3', 5, '2026-07-14 15:26:27', 60.00, 10, 2);
INSERT INTO `orders` VALUES (73, 'useral3', 1, '2026-07-14 15:33:29', 59.00, 10, 1);
INSERT INTO `orders` VALUES (74, 'useral3', 1, '2026-07-14 15:33:50', 39.00, 10, 2);
INSERT INTO `orders` VALUES (75, 'useral3', 1, '2026-07-14 15:34:00', 39.00, 10, 1);
INSERT INTO `orders` VALUES (76, 'useral3', 40, '2026-07-14 15:38:50', 35.00, 10, 2);

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
INSERT INTO `user` VALUES ('1', '123', 'zhangsan', 1, '/uploads/1_1781067893677.jpg', 1);
INSERT INTO `user` VALUES ('12001', '123', 'zdq', 1, NULL, 1);
INSERT INTO `user` VALUES ('2', '123', 'lisi', 1, NULL, 1);
INSERT INTO `user` VALUES ('3', '123', 'wangwu', 2, NULL, 1);
INSERT INTO `user` VALUES ('4', '123', 'zhaoliu', 2, NULL, 1);
INSERT INTO `user` VALUES ('useral3', '525435', 'useral3', 1, '/uploads/useral3_1784010386261.jpg', 1);
INSERT INTO `user` VALUES ('zdq123', '1234', 'zhang', 1, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
