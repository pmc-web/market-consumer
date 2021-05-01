-- -----------------------------------------------------
-- Schema market
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `market` DEFAULT CHARACTER SET utf8mb4;
USE `market` ;

-- -----------------------------------------------------
-- Table `market`.`attachment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`attachment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '자료 아이디',
  `path` VARCHAR(200) NULL DEFAULT NULL COMMENT '경로',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '자료';


-- -----------------------------------------------------
-- Table `market`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '유저 아이디',
  `email` VARCHAR(50) NOT NULL COMMENT '이메일',
  `password` VARCHAR(255) NULL COMMENT '패스워드',
  `address` VARCHAR(255) NULL DEFAULT NULL COMMENT '주소',
  `name` VARCHAR(20) NULL DEFAULT NULL COMMENT '이름',
  `role` VARCHAR(20) NULL DEFAULT NULL COMMENT '권한',
  `status` VARCHAR(20) NULL DEFAULT NULL COMMENT '계정상태',
  `picture` VARCHAR(20) NULL DEFAULT NULL COMMENT '프로필이미지',
  `reg_date` DATE NOT NULL COMMENT '가입일자',
  `update_date` DATE NULL COMMENT '수정일자',
  `auth_key` VARCHAR(20) NULL COMMENT '인증 키',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '유저';


-- -----------------------------------------------------
-- Table `market`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '카테고리 아이디',
  `main_category` VARCHAR(50) NULL DEFAULT NULL COMMENT '주 카테고리',
  `sub_category` VARCHAR(50) NULL DEFAULT NULL COMMENT '서브 카테고리',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '카테고리';


-- -----------------------------------------------------
-- Table `market`.`shop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`shop` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '쇼핑몰 아이디',
  `name` VARCHAR(20) NOT NULL COMMENT '쇼핑몰 명',
  `period` DATE NULL DEFAULT NULL COMMENT '기간',
  `full_description` VARCHAR(100) NULL DEFAULT NULL COMMENT '전체 설명',
  `short_description` VARCHAR(100) NULL DEFAULT NULL COMMENT '짧은 설명',
  `reg_date` DATE NOT NULL COMMENT '생성 날짜',
  `business_number` VARCHAR(100) NULL DEFAULT NULL COMMENT '사업자 번호',
  `business_name` VARCHAR(30) NULL DEFAULT NULL COMMENT '사업자 명',
  `owner` VARCHAR(30) NULL DEFAULT NULL COMMENT '별도 사업자 명',
  `telephone` VARCHAR(30) NULL DEFAULT NULL COMMENT '번호',
  `category_id` BIGINT NOT NULL COMMENT '카테고리 아이디',
  `user_id` BIGINT NOT NULL COMMENT '유저 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_shop_category1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_shop_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_shop_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `market`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shop_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `market`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '쇼핑몰';


-- -----------------------------------------------------
-- Table `market`.`cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`cart` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '장바구니 아이디',
  `reg_date` DATE NOT NULL COMMENT '생성 날짜',
  `user_id` BIGINT NOT NULL COMMENT '유저 아이디',
  `shop_id` BIGINT NOT NULL COMMENT '쇼핑몰 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_cart_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_cart_shop1_idx` (`shop_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `market`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cart_shop`
    FOREIGN KEY (`shop_id`)
    REFERENCES `market`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '장바구니';


-- -----------------------------------------------------
-- Table `market`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '상품 아이디',
  `name` VARCHAR(50) NOT NULL COMMENT '상품 명',
  `price` INT NOT NULL COMMENT '가격',
  `amount` INT NULL DEFAULT NULL COMMENT '수량',
  `category_int` BIGINT NULL DEFAULT NULL COMMENT '카테고리 아이디',
  `description` VARCHAR(100) NULL DEFAULT NULL COMMENT '상품 내용',
  `attachment_id` BIGINT NOT NULL COMMENT '자료 아이디',
  `shop_id` BIGINT NOT NULL COMMENT '쇼핑몰 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_product_attachment_idx` (`attachment_id` ASC) VISIBLE,
  INDEX `fk_product_shop_idx` (`shop_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_attachment`
    FOREIGN KEY (`attachment_id`)
    REFERENCES `market`.`attachment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_shop`
    FOREIGN KEY (`shop_id`)
    REFERENCES `market`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '상품';


-- -----------------------------------------------------
-- Table `market`.`cart_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`cart_product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '장바구니 상품 아이디',
  `product_id` BIGINT NOT NULL COMMENT '상품 아이디',
  `cart_id` BIGINT NOT NULL COMMENT '장바구니 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_cart_product_product_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_cart_product_cart_idx` (`cart_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_product_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `market`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cart_product_cart`
    FOREIGN KEY (`cart_id`)
    REFERENCES `market`.`cart` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '장바구니 상품';


-- -----------------------------------------------------
-- Table `market`.`claim`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`claim` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '신고 아이디',
  `reg_date` DATE NOT NULL COMMENT '등록일',
  `title` VARCHAR(30) NOT NULL COMMENT '제목',
  `content` VARCHAR(200) NULL DEFAULT NULL COMMENT '내용',
  `shop_id` BIGINT NOT NULL COMMENT '쇼핑몰 아이디',
  `user_id` BIGINT NOT NULL COMMENT '유저 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_claim_shop_idx` (`shop_id` ASC) VISIBLE,
  INDEX `fk_claim_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_claim_shop`
    FOREIGN KEY (`shop_id`)
    REFERENCES `market`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_claim_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `market`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '신고';


-- -----------------------------------------------------
-- Table `market`.`delivery`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`delivery` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '배송 아이디',
  `status` VARCHAR(45) NOT NULL COMMENT '배송 상태',
  `shipping_number` VARCHAR(45) NULL DEFAULT NULL COMMENT '운송 번호',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '택배';


-- -----------------------------------------------------
-- Table `market`.`favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`favorite` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '즐겨찾기 아이디',
  `user_id` BIGINT NOT NULL COMMENT '유저 아이디',
  `shop_id` BIGINT NOT NULL COMMENT '쇼핑몰 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_favorite_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_favorite_shop1_idx` (`shop_id` ASC) VISIBLE,
  CONSTRAINT `fk_favorite_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `market`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_favorite_shop1`
    FOREIGN KEY (`shop_id`)
    REFERENCES `market`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '즐겨찾기';


-- -----------------------------------------------------
-- Table `market`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '주문 아이디',
  `address` VARCHAR(200) NULL DEFAULT NULL COMMENT '주소',
  `payment` VARCHAR(20) NULL DEFAULT NULL COMMENT '구입 방법',
  `total_price` INT NOT NULL COMMENT '금액',
  `status` VARCHAR(20) NULL DEFAULT NULL COMMENT '상태',
  `amount` INT NULL DEFAULT NULL COMMENT '구매 수량',
  `shop_id` BIGINT NOT NULL COMMENT '쇼핑몰 아이디',
  `user_id` BIGINT NOT NULL COMMENT '유저 아이디',
  `delivery_id` BIGINT NULL COMMENT '배송 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_order_shop_idx` (`shop_id` ASC) VISIBLE,
  INDEX `fk_order_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_order_delivery_idx` (`delivery_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_shop`
    FOREIGN KEY (`shop_id`)
    REFERENCES `market`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `market`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_delivery`
    FOREIGN KEY (`delivery_id`)
    REFERENCES `market`.`delivery` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '주문';


-- -----------------------------------------------------
-- Table `market`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`review` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '리뷰 아이디',
  `title` VARCHAR(50) NULL DEFAULT NULL COMMENT '제목',
  `content` VARCHAR(200) NULL DEFAULT NULL COMMENT '내용',
  `reg_date` DATE NOT NULL COMMENT '등록일',
  `update_date` DATE NULL DEFAULT NULL COMMENT '수정일',
  `user_id` BIGINT NOT NULL COMMENT '유저 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_review_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_review_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `market`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '리뷰';


-- -----------------------------------------------------
-- Table `market`.`order_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`order_product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '주문 상품 아이디',
  `review_id` BIGINT NULL COMMENT '리뷰 아이디',
  `order_id` BIGINT NOT NULL COMMENT '주문 아이디',
  `product_id` BIGINT NOT NULL COMMENT '상품 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_order_product_review_idx` (`review_id` ASC) VISIBLE,
  INDEX `fk_order_product_order_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_order_product_product_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_product_review`
    FOREIGN KEY (`review_id`)
    REFERENCES `market`.`review` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_product_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `market`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_product_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `market`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '구매 상품';


-- -----------------------------------------------------
-- Table `market`.`qna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`qna` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '문의 아이디',
  `title` VARCHAR(50) NOT NULL COMMENT '제목',
  `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '내용',
  `comment` VARCHAR(255) NULL DEFAULT NULL COMMENT '댓글',
  `reg_date` DATE NOT NULL COMMENT '생성 날짜',
  `type` VARCHAR(20) NULL DEFAULT NULL COMMENT '문의 타입',
  `user_id` BIGINT NOT NULL COMMENT '유저 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_qna_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_qna_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `market`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '문의';


-- -----------------------------------------------------
-- Table `market`.`shop_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`shop_image` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '쇼핑몰 이미지 아이디',
  `type` VARCHAR(20) NULL DEFAULT NULL COMMENT '이미지 타입',
  `shop_id` BIGINT NOT NULL COMMENT '쇼핑몰 아이디',
  `attachment_id` BIGINT NOT NULL COMMENT '자료 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_shop_image_shop_idx` (`shop_id` ASC) VISIBLE,
  INDEX `fk_shop_image_attachment_idx` (`attachment_id` ASC) VISIBLE,
  CONSTRAINT `fk_shop_image_shop`
    FOREIGN KEY (`shop_id`)
    REFERENCES `market`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shop_image_attachment`
    FOREIGN KEY (`attachment_id`)
    REFERENCES `market`.`attachment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '쇼핑몰 이미지';


-- -----------------------------------------------------
-- Table `market`.`shop_qna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `market`.`shop_qna` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '쇼핑몰 문의 아이디',
  `title` VARCHAR(50) NOT NULL COMMENT '제목',
  `description` VARCHAR(200) NULL DEFAULT NULL COMMENT '내용',
  `reg_date` DATE NOT NULL COMMENT '생성 날짜',
  `comment` VARCHAR(100) NULL DEFAULT NULL COMMENT '댓글',
  `shop_id` BIGINT NOT NULL COMMENT '쇼핑몰 아이디',
  PRIMARY KEY (`id`),
  INDEX `fk_shop_qna_shop_idx` (`shop_id` ASC) VISIBLE,
  CONSTRAINT `fk_shop_qna_shop`
    FOREIGN KEY (`shop_id`)
    REFERENCES `market`.`shop` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COMMENT = '쇼핑몰 문의';