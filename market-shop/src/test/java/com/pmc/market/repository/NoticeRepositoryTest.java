package com.pmc.market.repository;

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
class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @DisplayName("shopId를 가진 noticeList 조회 - LAZY 로 N+1문제 해결")
    @Test
    void getNoticeList() {
        long shopId = 1L;

        List<ShopNotice> notices = noticeRepository.findAllByShopId(shopId);
        assertTrue(notices.size() > 0);
    }

}