package com.pmc.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmc.market.OrderApplication;
import com.pmc.market.model.dto.ReviewRequestDto;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.model.vo.ReviewResponseVo;
import com.pmc.market.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {OrderApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ReviewControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReviewService reviewService;

    @WithMockUser
    @DisplayName("리뷰쓰기")
    @Test
    void writeReview() throws Exception {

        ReviewRequestDto request = ReviewRequestDto.builder()
                .title("상품리뷰입니다.")
                .content("50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상")
                .rating(5)
                .orderProductId(1L)
                .build();
        doNothing().when(reviewService).makeReview(request);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/reviews")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("리뷰수정")
    @Test
    void updateReview() throws Exception {
        ReviewRequestDto request = ReviewRequestDto.builder()
                .title("상품리뷰수정입니다.")
                .content("50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상")
                .rating(5)
                .orderProductId(1L)
                .build();
        doNothing().when(reviewService).makeReview(request);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/reviews/{reviewId}", 1)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("내가 쓴 리뷰 보기")
    @Test
    void getMyReviews() throws Exception {
        List<ReviewResponseVo> list = new ArrayList<>();
        when(reviewService.getMyReviews(User.builder().build())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/reviews/my")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("상품 리뷰 보기")
    @Test
    void getProductReviews() throws Exception {
        List<ReviewResponseVo> list = new ArrayList<>();
        when(reviewService.getProductReviews(1L)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/reviews/product/{productId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("마켓 리뷰 보기")
    @Test
    void getShopReviews() throws Exception {
        List<ReviewResponseVo> list = new ArrayList<>();
        when(reviewService.getShopReviews(1L)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/reviews/shop/{shopId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("리뷰상세보기")
    @Test
    void getReviewDetail() throws Exception {
        when(reviewService.getReviewDetail(1L)).thenReturn(ReviewResponseVo.builder().build());

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/reviews/{reviewId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("리뷰 삭제")
    @Test
    void deleteReview() throws Exception {
        doNothing().when(reviewService).deleteReview(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/reviews/{reviewId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}