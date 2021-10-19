package com.pmc.market.service;

import com.pmc.market.OrderApplication;
import com.pmc.market.model.dto.ReviewRequestDto;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.model.vo.ReviewResponseVo;
import com.pmc.market.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewService reviewService;

    MockMultipartFile[] files = {
            new MockMultipartFile("image", "image.jpg", MediaType.MULTIPART_FORM_DATA_VALUE, "image.png".getBytes())};


    ReviewRequestDto request = ReviewRequestDto.builder()
            .title("리뷰입니다.")
            .content("리뷰내용입니다.내용입니다.")
            .rating(5)
            .orderProductId(1L)
            .build();

    @DisplayName("리뷰쓰기")
    @Test
    void makeReview() {
        reviewService.makeReview(request, files);
    }

    @Test
    void updateReview() {
        ReviewRequestDto update = ReviewRequestDto.builder()
                .title("리뷰수정입니다.")
                .content("리뷰내용입니다.내용입니다.")
                .rating(3)
                .orderProductId(1L)
                .build();
        reviewService.updateReview(update, 1L);
    }

    @DisplayName("productId 별 리뷰")
    @Test
    void getProductReviews() {
        Long productId = 1L;
        List<ReviewResponseVo> list = reviewService.getProductReviews(productId);
        assertThat(list.size() > 0);
    }

    @DisplayName("shopId 별 리뷰")
    @Test
    void getShopReviews() {
        List<ReviewResponseVo> list = reviewService.getShopReviews(1L);
        assertThat(list.size() > 0);
    }

    @DisplayName("리뷰 상세")
    @Test
    void getReviewDetail() {
        ReviewResponseVo responseVo = reviewService.getReviewDetail(1L);
        assertThat(responseVo.getReviewId()).isEqualTo(1L);
    }

    @DisplayName("내 리뷰")
    @Test
    void getMyReviews() {
        User user = userRepository.findById(1L).get();

        List<ReviewResponseVo> list = reviewService.getMyReviews(user);
        assertThat(list.size() > 0);
    }

    @DisplayName("리뷰삭제")
    @Test
    void deleteReview() {
        reviewService.deleteReview(2L);
    }
}