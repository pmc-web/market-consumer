package com.pmc.market.service;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.entity.ShopNotice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class ShopNoticeServiceTest {

    @Autowired
    private ShopService shopService;

    @DisplayName("마켓의 공지사항 리스트")
    @Test
    void getNoticeList() {
        long shopId = 1L;
        List<ShopNotice> shopNotice = shopService.getNoticeList(shopId);
        assertTrue(shopNotice.size() > 0);
    }

    @DisplayName("마켓의 공지사항 작성")
    @Test
    void insertNotice() {
    }

    @DisplayName("마켓의 공지사항 단일 조회")
    @Test
    void getNotice() {
    }

    @DisplayName("마켓의 공지사항 수정")
    @Test
    void updateNotice() {
    }

    @DisplayName("마켓의 공지사항 삭제")
    @Test
    void deleteNotice() {
    }
}