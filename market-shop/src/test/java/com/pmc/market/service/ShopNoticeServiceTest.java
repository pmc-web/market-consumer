package com.pmc.market.service;

import com.pmc.market.ShopApplication;
import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.dto.NoticeRequestDto;
import com.pmc.market.model.dto.NoticeResponseDto;
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

    NoticeRequestDto noticeRequestDto = NoticeRequestDto.builder()
            .title("공지사항 제목 !!")
            .content("공지사항 내용입니다,공지사항 내용입니다,공지사항 내용입니다,공지사항 내용입니다,공지사항 내용입니다")
            .build();
    @Autowired
    private ShopService shopService;

    @DisplayName("마켓의 공지사항 리스트")
    @Test
    void getNoticeList() {
        long shopId = 1L;
        List<ShopNotice> shopNotice = shopService.getNoticeList(shopId);
        assertTrue(shopNotice.size() > 0);
    }

    @DisplayName("마켓의 공지사항 작성 - 20210530 통과")
    @Test
    void insertNotice() {
        long shopId = 1L;
        NoticeResponseDto noticeResponseDto = shopService.insertNotice(shopId, noticeRequestDto);
        assertTrue(noticeResponseDto.getShopId().equals(shopId));
    }

    @DisplayName("마켓의 공지사항 단일 조회 - 20210530 통과")
    @Test
    void getNotice() {
        long noticeId = 2L;
        NoticeResponseDto noticeResponseDto = shopService.getNotice(noticeId);
        assertTrue(noticeResponseDto.getId().equals(noticeId));
    }

    @DisplayName("마켓의 공지사항 수정 - 20210530 통과")
    @Test
    void updateNotice() {
        long noticeId = 2L;
        NoticeRequestDto noticeRequestDto = NoticeRequestDto.builder()
                .title("공지사항 제목 ")
                .content("공지사항 수정 내용!!")
                .build();
        NoticeResponseDto noticeResponseDto = shopService.updateNotice(noticeId, noticeRequestDto);
        assertTrue(noticeResponseDto.getTitle().equals(noticeRequestDto.getTitle()));
    }

    @DisplayName("마켓의 공지사항 삭제 - 20210530 통과")
    @Test
    void deleteNotice() {
        long noticeId = 3L;
        try {
            shopService.deleteNotice(noticeId);
        } catch (EntityNotFoundException e) {
            System.out.println("error test");
        }
    }
}