package com.pmc.market.model.product.entity;

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
public class ProductQnA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @NotNull
    private String title;

    @NotNull
    private LocalDateTime regDate;

    @Setter
    private LocalDateTime updateDate;

    @Enumerated(EnumType.STRING)
    private QnAType qnAType;

    @Setter
    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productQnA", cascade = CascadeType.ALL)
//    private List<Attachment> attachments = new ArrayList<>();
}
