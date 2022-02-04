package com.pmc.market.model.product.entity;

import com.pmc.market.model.BaseTimeEntity;
import com.pmc.market.model.shop.entity.Shop;
import com.pmc.market.model.user.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductQnA extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_qna_id")
    private long id;

    @Setter
    @NotNull
    private String title;

    @Enumerated(EnumType.STRING)
    private QnAType qnAType;

    @Setter
    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
