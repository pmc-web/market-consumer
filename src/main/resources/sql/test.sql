

-- CATEGORY
INSERT INTO `market`.`category` (`id`, `main_category`, `sub_category`) VALUES ('1', '악세사리', '반지');
INSERT INTO `market`.`category` (`id`, `main_category`, `sub_category`) VALUES ('2', '악세사리', '귀걸이');
INSERT INTO `market`.`category` (`id`, `main_category`, `sub_category`) VALUES ('3', '악세사리', '목걸');
INSERT INTO `market`.`category` (`id`, `main_category`, `sub_category`) VALUES ('4', '식품', '과일청');
INSERT INTO `market`.`category` (`id`, `main_category`, `sub_category`) VALUES ('5', '식품', '마카롱');

-- SHOP
INSERT INTO `market`.`shop` (`id`, `business_name`, `business_number`, `delivery_cost`, `full_description`, `name`, `owner`, `period`, `qna_description`, `reg_date`, `ship_description`, `short_description`, `telephone`, `category_id`, `user_id`)
VALUES ('1', '마켓사업이름', '사업자 번호', '0', '마켓설명 ', '마켓1', '사업자명', '2022-05-20 14:33:29.008736', 'qna상세 내용', '2021-05-20 14:33:29.008736', '배송비 정책 상세 내용', '짧은 마켓 설명 ', '010-0000-0000', '1', '1');
INSERT INTO `market`.`shop` (`id`, `business_name`, `business_number`, `delivery_cost`, `full_description`, `name`, `owner`, `period`, `qna_description`, `reg_date`, `ship_description`, `short_description`, `telephone`, `category_id`, `user_id`)
VALUES ('2', '마켓사업이름', '사업자 번호2', '0', '마켓설명2', '마켓2', '사업자명2', '2022-05-20 14:33:29.008736', 'qna상세 내용', '2021-05-20 14:33:29.008736', '배송비 정책 상세 내용', '짧은 마켓 설명 ', '010-0000-0000', '2', '1');


-- FAVORITE
INSERT INTO `market`.`favorite` (`id`, `shop_id`, `user_id`) VALUES ('1', '1', '1');

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


-- SEARCH
INSERT INTO `market`.`search` (`count`, `word`, `reg_date`, update_date) VALUES ('1', '검색1', NOW(), NOW());
INSERT INTO `market`.`search` (`count`, `word`, `reg_date`, update_date) VALUES ('1', '검색2', NOW(), NOW());
-- USER_SEARCH
INSERT INTO `market`.`user_search` (`search_id`, `user_id`) VALUES ('1', '1');
INSERT INTO `market`.`user_search` (`search_id`, `user_id`) VALUES ('1', '2');


-- SHIP-ADDRESS
INSERT INTO `market`.`ship_address` (`id`, `address`, `detail`, `zip_code`, `user_id`) VALUES ('1', '서울시 광진구 화양동', '00빌라 00호', '05022', '1');
