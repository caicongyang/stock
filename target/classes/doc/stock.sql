/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : stock

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 19/10/2024 21:46:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_concept
-- ----------------------------
DROP TABLE IF EXISTS `t_concept`;
CREATE TABLE `t_concept` (
  `concept_name` varchar(64) NOT NULL,
  `concept_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `source` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`concept_name`,`concept_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='行业/概念';

-- ----------------------------
-- Table structure for t_concept_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_concept_stock`;
CREATE TABLE `t_concept_stock` (
  `concept_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `concept_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `stock_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `stock_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`stock_code`,`concept_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_etf
-- ----------------------------
DROP TABLE IF EXISTS `t_etf`;
CREATE TABLE `t_etf` (
  `stock_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '股票代码',
  `stock_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '股票名称',
  `trade_date` date NOT NULL COMMENT '交易日期',
  `open` double DEFAULT NULL COMMENT '开盘价',
  `close` double DEFAULT NULL COMMENT '收盘价',
  `high` double DEFAULT NULL COMMENT '最高价',
  `low` double DEFAULT NULL COMMENT '最低价',
  `volume` bigint DEFAULT NULL COMMENT '成交量',
  `amount` double DEFAULT NULL COMMENT '成交金额',
  PRIMARY KEY (`stock_code`,`trade_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ETF表';

-- ----------------------------
-- Table structure for t_etf_higher
-- ----------------------------
DROP TABLE IF EXISTS `t_etf_higher`;
CREATE TABLE `t_etf_higher` (
  `stock_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '股票代码',
  `trading_day` date NOT NULL COMMENT '交易日期',
  `interval_days` int DEFAULT NULL COMMENT '多少天内创新高',
  `previous_highs_date` date DEFAULT NULL COMMENT '前期高点日期',
  PRIMARY KEY (`stock_code`,`trading_day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='TEtfHigher对象';

-- ----------------------------
-- Table structure for t_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_stock`;
CREATE TABLE `t_stock` (
  `stock_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `trade_date` date NOT NULL,
  `open` double DEFAULT NULL,
  `high` double DEFAULT NULL,
  `low` double DEFAULT NULL,
  `close` double DEFAULT NULL,
  `volume` bigint DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `pre_close` double DEFAULT NULL COMMENT '昨日收盘点',
  `pct_chg` double DEFAULT NULL COMMENT '涨跌幅（%）',
  `change` double DEFAULT NULL COMMENT '涨跌',
  PRIMARY KEY (`stock_code`,`trade_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_stock_limit
-- ----------------------------
DROP TABLE IF EXISTS `t_stock_limit`;
CREATE TABLE `t_stock_limit` (
  `stock_code` varchar(32) NOT NULL COMMENT '股票代码',
  `trading_day` date NOT NULL COMMENT '交易日期',
  `gain` double DEFAULT NULL COMMENT '涨幅',
  PRIMARY KEY (`stock_code`,`trading_day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_stock_main
-- ----------------------------
DROP TABLE IF EXISTS `t_stock_main`;
CREATE TABLE `t_stock_main` (
  `stockCode` varchar(64) NOT NULL,
  `stockName` varchar(64) NOT NULL,
  `swL3` varchar(255) DEFAULT NULL,
  `jqL2` varchar(255) DEFAULT NULL,
  `zjw` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`stockCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_transaction_etf
-- ----------------------------
DROP TABLE IF EXISTS `t_transaction_etf`;
CREATE TABLE `t_transaction_etf` (
  `stock_code` varchar(64) NOT NULL,
  `trading_day` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `stock_name` varchar(64) DEFAULT NULL,
  `last_day_compare` double DEFAULT NULL,
  `mean_ratio` double DEFAULT NULL,
  `sw_l3` varchar(255) DEFAULT NULL COMMENT '申万行业',
  `jq_l2` varchar(255) DEFAULT NULL COMMENT '聚宽行业',
  `zjw` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`stock_code`,`trading_day`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='TTransactionEtf对象';

-- ----------------------------
-- Table structure for t_transaction_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_transaction_stock`;
CREATE TABLE `t_transaction_stock` (
  `stock_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '股票代码',
  `trading_day` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易日期',
  `last_day_compare` double NOT NULL COMMENT '昨日对比值',
  `mean_ratio` double DEFAULT NULL COMMENT '平均比率',
  `concept_str` varchar(255) DEFAULT NULL COMMENT '概念字符串',
  `sw_l3` varchar(255) DEFAULT NULL COMMENT '申万三级行业',
  `jq_l2` varchar(255) DEFAULT NULL COMMENT '聚宽二级行业',
  `zjw` varchar(255) DEFAULT NULL COMMENT '证监会行业',
  `gain` double DEFAULT NULL COMMENT '当日涨幅',
  PRIMARY KEY (`stock_code`,`trading_day`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='TTransactionStock对象';

SET FOREIGN_KEY_CHECKS = 1;
