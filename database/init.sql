-- =============================================
-- 便民超市收银系统 - 数据库初始化脚本
-- =============================================

CREATE DATABASE IF NOT EXISTS supermarket DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE supermarket;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS goods;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50) NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    role        VARCHAR(20) NOT NULL DEFAULT 'user',
    nickname    VARCHAR(100) DEFAULT '',
    phone       VARCHAR(20) DEFAULT '',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO users (username, password, role, nickname) VALUES
('lexluo', 'Lzh554535', 'admin', '管理员'),
('test01', '123456', 'user', '测试用户');

CREATE TABLE goods (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    original_price DECIMAL(10,2) DEFAULT NULL,
    img         VARCHAR(500) DEFAULT "",
    cate        VARCHAR(100) DEFAULT "",
    sub_cate    VARCHAR(100) DEFAULT "",
    brand       VARCHAR(100) DEFAULT "",
    sales       INT DEFAULT 0,
    stock       INT DEFAULT 0,
    description VARCHAR(500) DEFAULT "",
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cart (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    goods_id    INT NOT NULL,
    goods_name  VARCHAR(200) NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    quantity    INT NOT NULL DEFAULT 1,
    spec        VARCHAR(100) DEFAULT "",
    img         VARCHAR(500) DEFAULT "",
    added_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (goods_id) REFERENCES goods(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE orders (
    order_no    VARCHAR(50) PRIMARY KEY,
    total       DECIMAL(10,2) NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT "pending",
    create_time BIGINT NOT NULL,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE order_items (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    order_no    VARCHAR(50) NOT NULL,
    goods_id    INT NOT NULL,
    goods_name  VARCHAR(200) NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    quantity    INT NOT NULL,
    FOREIGN KEY (order_no) REFERENCES orders(order_no) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO goods (id, name, price, original_price, img, cate, sub_cate, brand, sales, stock, description) VALUES
(1, "伊利纯牛奶 250ml*24盒 整箱装", 59.9, 79.9, "伊利纯牛奶", "食品饮料", "牛奶乳品", "伊利", 1256, 568, "伊利纯牛奶源自优质牧场"),
(2, "蒙牛特仑苏纯牛奶 250ml*12盒", 65.9, 85.9, "蒙牛特仑苏", "食品饮料", "牛奶乳品", "蒙牛", 1089, 325, "蒙牛特仑苏，高端牛奶品牌"),
(3, "农夫山泉矿泉水 550ml*24瓶 整箱", 29.9, 39.9, "农夫山泉", "食品饮料", "酒水饮料", "农夫山泉", 2156, 896, "农夫山泉有点甜"),
(4, "乐事薯片大礼包 混合口味 70g*10包", 35.9, 45.9, "乐事薯片", "食品饮料", "休闲零食", "乐事", 987, 456, "乐事薯片，多种口味混合"),
(5, "康师傅红烧牛肉面 12碗 整箱装", 49.9, 59.9, "康师傅方便面", "食品饮料", "休闲零食", "康师傅", 1567, 678, "康师傅红烧牛肉面"),
(6, "红富士苹果 新鲜脆甜 5斤装", 29.9, 39.9, "红富士苹果", "生鲜果蔬", "新鲜水果", "红富士", 2345, 123, "红富士苹果新鲜采摘"),
(7, "东北五常大米 稻花香米 5kg", 79.9, 99.9, "大米", "食品饮料", "粮油调味", "五常", 876, 234, "东北五常大米稻花香"),
(8, "云南白药牙膏 留兰香型 180g*2支", 49.9, 59.9, "牙膏", "个人护理", "口腔护理", "云南白药", 678, 567, "云南白药牙膏"),
(9, "蓝月亮洗衣液 薰衣草香 3kg*2瓶", 69.9, 89.9, "蓝月亮洗衣液", "个人护理", "洗护清洁", "蓝月亮", 2567, 567, "蓝月亮洗衣液，深层洁净"),
(10, "维达抽纸 软抽3层 20抽*24包 整箱", 39.9, 49.9, "维达抽纸", "日用百货", "纸巾湿巾", "维达", 3456, 890, "维达抽纸，品质保证"),
(11, "农家土鸡蛋 新鲜散养 30枚装", 45.9, 55.9, "鸡蛋", "生鲜果蔬", "蛋类制品", "农家", 1890, 234, "农家散养土鸡蛋"),
(12, "桃李醇熟切片面包 400g 营养早餐", 12.9, 15.9, "面包", "食品饮料", "休闲零食", "桃李", 2100, 456, "桃李醇熟切片面包");

ALTER TABLE goods AUTO_INCREMENT = 13;

INSERT INTO orders (order_no, total, status, create_time) VALUES
("ORD202606010001", 119.8, "completed", 1748313600000),
("ORD202606020002", 89.7,  "shipped",   1748400000000),
("ORD202606030003", 29.9,  "pending",   1748486400000);

INSERT INTO order_items (order_no, goods_id, goods_name, price, quantity) VALUES
("ORD202606010001", 1, "伊利纯牛奶 250ml*24盒 整箱装", 59.9, 2),
("ORD202606020002", 3, "农夫山泉矿泉水 550ml*24瓶 整箱", 29.9, 3),
("ORD202606030003", 6, "红富士苹果 新鲜脆甜 5斤装", 29.9, 1);

SELECT "=== 初始化完成 ===" AS STATUS;
SELECT COUNT(*) AS goods_count FROM goods;
SELECT COUNT(*) AS order_count FROM orders;
