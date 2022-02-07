package com.pmc.market.model.dto;

import com.pmc.market.domain.product.entity.Review;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel("상품 리뷰 할 때 필요한 정보")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReviewRequestDto {

    @ApiModelProperty(value = "orderProduct Id")
    @NotNull(message = "orderProductId를 입력해야합니다.")
    private Long orderProductId;


    @ApiModelProperty(value = "리뷰 제목")
    @NotBlank(message = "제목을 입력해주세요")
    private String title;


    @ApiModelProperty(value = "리뷰 내용")
    @NotBlank(message = "내용을 입력해주세요")
    @Size(min = 10, max = 200, message = "10자 이상 200글자 이하로 후기를 작성해 주세요.")
    private String content;


    @ApiModelProperty(value = "리뷰 평점 5점만점")
    @NotNull(message = "평점을 입력해주세요")
    private Integer rating;

    public Review toEntity(ReviewRequestDto requestDto) {
        return Review.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
    }

    public void updateReview(Review review) {
//        review.setTitle(this.title);
//        review.setContent(this.content);
//        review.setUpdateDate(LocalDateTime.now());
    }
}
