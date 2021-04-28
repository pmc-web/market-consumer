package com.pmc.market.model.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
