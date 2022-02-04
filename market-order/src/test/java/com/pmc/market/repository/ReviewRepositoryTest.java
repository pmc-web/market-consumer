package com.pmc.market.repository;

import com.pmc.market.OrderApplication;
import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.domain.product.entity.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @DisplayName("리뷰 상세")
    @Test
    void get() {
        Review review = reviewRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("해당 리뷰가 없습니다."));
        assertThat(review.getId()).isEqualTo(2L);
    }

    @DisplayName("리뷰삭제")
    @Test
    void delete() {
        reviewRepository.deleteById(3L);
    }
}