package com.pmc.market.service;

import com.pmc.market.model.dto.ReviewRequestDto;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.model.vo.ReviewResponseVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {
    void makeReview(ReviewRequestDto reviewRequestDto, MultipartFile[] file);

    List<ReviewResponseVo> getProductReviews(long productId);

    List<ReviewResponseVo> getShopReviews(long shopId);

    ReviewResponseVo getReviewDetail(long reviewId);

    List<ReviewResponseVo> getMyReviews(User user);

    void updateReview(ReviewRequestDto reviewRequestDto, long reviewId);

    void deleteReview(long reviewId);
}
