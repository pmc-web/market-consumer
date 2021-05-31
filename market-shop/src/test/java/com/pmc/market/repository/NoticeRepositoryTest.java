package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.dto.NoticeRequestDto;
import com.pmc.market.model.entity.Shop;
import com.pmc.market.model.entity.ShopNotice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("shopId를 가진 noticeList 조회 - LAZY 로 N+1문제 해결")
    @Test
    void getNoticeList() {
        long shopId = 1L;

        List<ShopNotice> notices = noticeRepository.findAllByShopId(shopId);
        assertTrue(notices.size() > 0);
    }


    @DisplayName("마켓의 공지사항 작성 - 20210530 통과")
    @Test
    void insertNotice() {
        long shopId = 1L;
        Shop shop = shopRepository.findById(shopId).get();
        ShopNotice shopNotice = ShopNotice.builder()
                .shop(shop)
                .title("공지사항 작성 테스트 repository ")
                .content("공지사항 작성하기 테스트입니다-content")
                .regDate(LocalDateTime.now())
                .build();

        noticeRepository.save(shopNotice);
    }

    @DisplayName("마켓의 공지사항 단일 조회 - 20210530 통과")
    @Test
    void getNotice() {
        long noticeId = 1L;
        ShopNotice shopNotice = noticeRepository.findById(noticeId).get();
        assertTrue(shopNotice.getId().equals(noticeId));
    }

    @DisplayName("마켓의 공지사항 수정 - 20210530 통과")
    @Test
    void updateNotice() {
        long noticeId = 1L;
        String title = "수정된 공지사항11111111";
        ShopNotice shopNotice = noticeRepository.findById(noticeId).get();
        NoticeRequestDto noticeRequestDto = NoticeRequestDto.builder()
                .title(title)
                .content("수정된 공지사항 내용")
                .build();
        shopNotice.updateNotice(noticeRequestDto);
        noticeRepository.save(shopNotice);
        assertTrue(shopNotice.getTitle().equals(title));
    }

    @DisplayName("마켓의 공지사항 삭제 - 20210530 통과")
    @Test
    void deleteNotice() {
        long noticeId = 4L;
        noticeRepository.deleteById(noticeId);
        assertTrue(!noticeRepository.findById(noticeId).isPresent());
    }
}