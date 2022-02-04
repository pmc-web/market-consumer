package com.pmc.market.domain.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tag_name", unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag", cascade = CascadeType.ALL)
    private List<ShopTag> shopTags = new ArrayList<>();

    public void tagging(ShopTag shopTag){
        shopTags.add(shopTag);
    }

    public void unTagging(ShopTag shopTag){
        shopTags.remove(shopTag);
    }
}