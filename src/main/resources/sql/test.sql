
-- CATEGORY
INSERT INTO `market`.`category` ( `main_category`, `sub_category`) VALUES ( '악세사리', '반지');
INSERT INTO `market`.`category` ( `main_category`, `sub_category`) VALUES ( '악세사리', '귀걸이');
INSERT INTO `market`.`category` ( `main_category`, `sub_category`) VALUES ( '악세사리', '목걸');
INSERT INTO `market`.`category` ( `main_category`, `sub_category`) VALUES ( '식품', '과일청');
INSERT INTO `market`.`category` ( `main_category`, `sub_category`) VALUES ( '식품', '마카롱');

-- SHOP
INSERT INTO `market`.`shop` (`business_name`, `business_number`, `delivery_cost`, `full_description`, `name`, `owner`, `period`, `qna_description`, `reg_date`, `ship_description`, `short_description`, `telephone`, `category_id`, `user_id`)
VALUES ('마켓사업이름1', '사업자 번호', '0', '마켓설명 ', '마켓1', '사업자명', '2022-05-20 14:33:29.008736', 'qna상세 내용', '2021-05-20 14:33:29.008736', '배송비 정책 상세 내용', '짧은 마켓 설명 ', '010-0000-0000', '1', '1');
INSERT INTO `market`.`shop` (`business_name`, `business_number`, `delivery_cost`, `full_description`, `name`, `owner`, `period`, `qna_description`, `reg_date`, `ship_description`, `short_description`, `telephone`, `category_id`, `user_id`)
VALUES ('마켓사업이름2', '사업자 번호2', '0', '마켓설명2', '마켓2', '사업자명2', '2022-05-20 14:33:29.008736', 'qna상세 내용', '2021-05-20 14:33:29.008736', '배송비 정책 상세 내용', '짧은 마켓 설명 ', '010-0000-0000', '2', '1');
INSERT INTO `market`.`shop` (`business_name`, `business_number`, `delivery_cost`, `full_description`, `name`, `owner`, `period`, `qna_description`, `reg_date`, `ship_description`, `short_description`, `telephone`, `category_id`, `user_id`)
VALUES ('마켓사업이름3', '사업자 번호2', '0', '마켓설명2', '마켓2', '사업자명2', '2022-05-20 14:33:29.008736', 'qna상세 내용', '2021-05-20 14:33:29.008736', '배송비 정책 상세 내용', '짧은 마켓 설명 ', '010-0000-0000', '2', '1');
INSERT INTO `market`.`shop` (`business_name`, `business_number`, `delivery_cost`, `full_description`, `name`, `owner`, `period`, `qna_description`, `reg_date`, `ship_description`, `short_description`, `telephone`, `category_id`, `user_id`)
VALUES ('마켓사업이름4', '사업자 번호2', '0', '마켓설명2', '마켓2', '사업자명2', '2022-05-20 14:33:29.008736', 'qna상세 내용', '2021-05-20 14:33:29.008736', '배송비 정책 상세 내용', '짧은 마켓 설명 ', '010-0000-0000', '2', '1');
INSERT INTO `market`.`shop` (`business_name`, `business_number`, `delivery_cost`, `full_description`, `name`, `owner`, `period`, `qna_description`, `reg_date`, `ship_description`, `short_description`, `telephone`, `category_id`, `user_id`)
VALUES ('마켓사업이름5', '사업자 번호2', '0', '마켓설명2', '마켓2', '사업자명2', NOW(), 'qna상세 내용', '2021-05-20 14:33:29.008736', '배송비 정책 상세 내용', '짧은 마켓 설명 ', '010-0000-0000', '2', '1');

-- FAVORITE
INSERT INTO `market`.`favorite` (`shop_id`, `user_id` ,`reg_date`) VALUES ( '1', '1', NOW());
INSERT INTO `market`.`favorite` (`shop_id`, `user_id`, `reg_date`) VALUES ( 2, 1, NOW());

-- SHOP_NOTICE
INSERT INTO `market`.`shop_notice` (`id`, `shop_id`, `content`, `reg_date`, `title`)
VALUES ('1', '1', '공지사항내용입니다', '2021-05-27', 'hi 에이치 아이~');
INSERT INTO `market`.`shop_notice` (`id`, `shop_id`, `content`, `reg_date`, `title`)
VALUES ('2', '1', '공지사항내용입니다2', '2021-05-27', '공지사항제목');

-- TAG
INSERT INTO `market`.`tag` (name) VALUES ('태그1');
INSERT INTO `market`.`tag` (name) VALUES ('태그2');

-- SHOP_TAG
INSERT INTO `market`.`shop_tag` (shop_id, tag_id) VALUES ('1', '1');
INSERT INTO `market`.`shop_tag` (shop_id, tag_id) VALUES ('1', '2');


-- PRODUCT

INSERT INTO `market`.`product` ( `amount`, `description`, `name`, `price`, `category_id`, `shop_id`)
 VALUES ( '100', '핸드메이스 반지입니다.', '반지A', '10000', '1', '1');
INSERT INTO `market`.`product` ( `amount`, `description`, `name`, `price`, `category_id`, `shop_id`)
VALUES ( '100', '핸드메이스 반지입니다.', '반지B', '12000', '1', '1');
INSERT INTO `market`.`product` ( `amount`, `description`, `name`, `price`, `category_id`, `shop_id`)
VALUES ( '100', '핸드메이스 반지입니다.', '반지C', '12000', '1', '1');


-- CART
INSERT INTO `market`.`cart` (`reg_date`, `shop_id`, `user_id`) VALUES (NOW(), '2', '3');

-- CART_PRODUCT