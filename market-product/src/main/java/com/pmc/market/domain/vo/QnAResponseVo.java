package com.pmc.market.model.vo;

import com.pmc.market.domain.product.entity.ProductQnA;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QnAResponseVo {
    private Long qnaId;
    private String title;
    private String content;
    private String image;
    private String author;

    public static QnAResponseVo from(ProductQnA productQnA) {
        return QnAResponseVo.builder()
                .qnaId(productQnA.getId())
                .title(productQnA.getTitle())
                .content(productQnA.getContent())
                .author(productQnA.getUser().getNickname())
                .build();
    }
}
