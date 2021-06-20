package com.pmc.market.repository;

import com.pmc.market.OrderApplication;
import com.pmc.market.model.order.entity.OrderStatus;
import com.pmc.market.model.order.entity.Purchase;
import com.pmc.market.model.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderApplication.class})
class OrderRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("카카오페이 tid 정보 업데이트")
    @Test
    void updateKakaoOrderInfo() {
        long id = 6L;
        String tid = "T2909608776002405594";
        orderRepository.updateKakaoOrderInfo(id, tid, LocalDateTime.now(), OrderStatus.PAYMENT_COMPLETE);
    }

    @DisplayName("카카오페이 tid 로 조회")
    @Test
    void findByPayInfo() {
        String tid = "T2909608776002405594";
        Optional<Purchase> purchase = orderRepository.findByPayInfo(tid);

        assertThat(purchase).isNotEqualTo(null);
    }

    @DisplayName("구매내역 조회")
    @Test
    void findByUser() {
        User user = userRepository.findById(1L).get();
        List<Purchase> list = orderRepository.findByUserOrderByRegDateDesc(user);
        assertTrue(list.size() > 0);
    }

    @DisplayName("단일 조회")
    @Test
    void findOrderById() {
        Optional<Purchase> purchase = orderRepository.findById(6L);
        assertThat(purchase.get()).isNotEqualTo(null);
    }
}