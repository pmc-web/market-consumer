-- -----------------------------------------------------
-- Schema market
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `market` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci ;

-- -----------------------------------------------------
-- Table `market`.`USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`USER` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NULL,
  `social` VARCHAR(50) NULL,
  `password` VARCHAR(255) NULL,
  `address` VARCHAR(255) NULL,
  `nickname` VARCHAR(20) NULL,
  `role` VARCHAR(20) NULL,
  `reg_date` DATE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`DELIVERY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`DELIVERY` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NULL,
  `shipping_number` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`ORDER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`ORDER` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(200) NULL,
  `payment` INT NULL,
  `total_price` INT NULL,
  `status` VARCHAR(20) NULL,
  `delivery_id` BIGINT NULL,
  `shop_id` BIGINT NULL,
  `user_id` BIGINT NULL,
  `amount` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`ATTACHMENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`ATTACHMENT` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `path` VARCHAR(200) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`CART`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`CART` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `reg_date` DATE NULL,
  `user_id` BIGINT NULL,
  `shop_id` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`QNA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`QNA` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NULL,
  `description` VARCHAR(255) NULL,
  `comment` VARCHAR(255) NULL,
  `reg_date` DATE NULL,
  `type` VARCHAR(20) NULL,
  `user_id` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`CART_PRODUCT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`CART_PRODUCT` (
  `id` BIGINT NOT NULL,
  `cart_id` BIGINT NULL,
  `product_id` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`PRODUCT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`PRODUCT` (
  `id` BIGINT NOT NULL,
  `name` VARCHAR(50) NULL,
  `price` INT NULL,
  `amount` INT NULL,
  `category_int` BIGINT NULL,
  `shop_id` BIGINT NULL,
  `description` VARCHAR(100) NULL,
  `attachment_id` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`REVIEW`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`REVIEW` (
  `id` BIGINT NOT NULL,
  `title` VARCHAR(50) NULL,
  `content` VARCHAR(200) NULL,
  `reg_date` DATE NULL,
  `update_date` DATE NULL,
  `user_id` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`SHOP_IMAGE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`SHOP_IMAGE` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(20) NULL,
  `shop_id` BIGINT NULL,
  `attachment_id` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`CATEGORY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`CATEGORY` (
  `id` BIGINT NOT NULL,
  `main_category` VARCHAR(50) NULL,
  `sub_category` VARCHAR(50) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`SHOP_QNA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`SHOP_QNA` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NULL,
  `description` VARCHAR(200) NULL,
  `reg_date` DATE NULL,
  `comment` VARCHAR(100) NULL,
  `shop_id` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`FAVORITE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`FAVORITE` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NULL,
  `shop_id` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`CLAIM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`CLAIM` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NULL,
  `shop_id` BIGINT NULL,
  `reg_date` DATE NULL,
  `title` VARCHAR(30) NULL,
  `content` VARCHAR(200) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`ORDER_PRODUCT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`ORDER_PRODUCT` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `parchase_id` BIGINT NULL,
  `product_id` BIGINT NULL,
  `review_id` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `market`.`SHOP`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`SHOP` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL,
  `period` DATE NULL,
  `full_description` VARCHAR(100) NULL,
  `short_description` VARCHAR(100) NULL,
  `reg_date` DATE NULL,
  `business_number` VARCHAR(100) NULL,
  `business_name` VARCHAR(30) NULL,
  `owner` VARCHAR(30) NULL,
  `telephone` VARCHAR(30) NULL,
  `category_id` BIGINT NULL,
  `user_id` BIGINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
