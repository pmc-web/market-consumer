package com.pmc.market.model.vo;

import com.pmc.market.model.product.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseVo {
    private Long reviewId;
    private String title;
    private String content;
    private String image;
    private String author;

    public static ReviewResponseVo of(Review review) {
        return ReviewResponseVo.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .author(review.getUser().getNickname())
                .build();
    }
}
