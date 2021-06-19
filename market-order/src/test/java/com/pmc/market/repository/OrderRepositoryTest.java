package com.pmc.market.repository;

import com.pmc.market.OrderApplication;
import com.pmc.market.model.order.entity.Purchase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderApplication.class})
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("카카오페이 tid 정보 업데이트")
    @Test
    void updateKakaoOrderInfo() {
        long id = 6L;
        String tid = "T2909608776002405594";
        orderRepository.updateKakaoOrderInfo(id, tid, LocalDateTime.now());
    }

    @DisplayName("카카오페이 tid 로 조회")
    @Test
    void findByPayInfo() {
        String tid = "T2909608776002405594";
        Optional<Purchase> purchase = orderRepository.findByPayInfo(tid);

        assertThat(purchase).isNotEqualTo(null);
    }
}