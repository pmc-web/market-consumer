package com.pmc.market.model.dto;

import com.pmc.market.domain.product.entity.Product;
import com.pmc.market.domain.product.entity.ProductQnA;
import com.pmc.market.domain.product.entity.QnAType;
import com.pmc.market.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ApiModel("상품 문의 할 때 필요한 정보")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QnARequestDto {
    @ApiModelProperty(name = "productId", value = "상품 id")
    @NotNull(message = "ProductId를 입력해야합니다.")
    private Long productId;

    @ApiModelProperty(value = "문의글 제목")
    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @ApiModelProperty(value = "문의글 내용")
    @NotBlank(message = "내용을 입력해주세요")
    @Size(min = 10, max = 200, message = "10자 이상 200글자 이하로 작성해 주세요.")
    private String content;

    @ApiModelProperty(value = "배송문의, 상품문의, 기타문의 중 선택")
    private QnAType qnaType;

    @ApiModelProperty(value = "문의글 이미지")
    private String image;

    public ProductQnA toEntity(QnARequestDto requestDto, Product product, User user) {
        return ProductQnA.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .regDate(LocalDateTime.now())
                .qnAType(requestDto.qnaType)
                .product(product)
                .shop(product.getShop())
                .user(user)
                .build();
    }

    public void updateQnA(ProductQnA qnA) {
        qnA.setTitle(this.title);
        qnA.setContent(this.content);
        qnA.setUpdateDate(LocalDateTime.now());
    }
}
