package com.pmc.market.model.dto;

import com.pmc.market.model.product.entity.Review;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ApiModel("상품 리뷰 할 때 필요한 정보")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReviewRequestDto {
    @NotNull(message = "orderProductId를 입력해야합니다.")
    private Long orderProductId;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    @Size(min = 10, max = 200, message = "10자 이상 200글자 이하로 후기를 작성해 주세요.")
    private String content;

    @NotNull(message = "평점을 입력해주세요")
    private Integer rating;

    private String image;

    public Review toEntity(ReviewRequestDto requestDto) {
        return Review.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .regDate(LocalDateTime.now())
                .build();
    }

    public void updateReview(Review review) {
        review.setTitle(this.title);
        review.setContent(this.content);
        review.setUpdateDate(LocalDateTime.now());
    }
}
