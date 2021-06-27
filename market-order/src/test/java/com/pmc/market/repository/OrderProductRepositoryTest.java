package com.pmc.market.repository;

import com.pmc.market.OrderApplication;
import com.pmc.market.model.order.entity.OrderProduct;
import com.pmc.market.model.product.entity.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderProductRepositoryTest {

    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @DisplayName("orderProduct 상품 리뷰 외래키 업데이트")
    @Test
    void updateReview() {
        Review review = reviewRepository.findById(2L).get();
        orderProductRepository.updateReview(1, review);
    }

    @DisplayName("주문 상품들 리스트")
    @Test
    void getOrderProduct() {
        List<OrderProduct> list = orderProductRepository.findByProductId(1L);
        assertTrue(list.size() > 0);
        list.stream().filter(p -> p.getReview() != null)
                .forEach(p -> System.out.println(p.getReview().getTitle()));
    }

    @DisplayName("주문 상품 찾기")
    @Test
    void findByReviewId() {
        OrderProduct orderProduct = orderProductRepository.findByReviewId(2L);
        System.out.println(orderProduct.getId());
    }
}