package com.pmc.market.model.shop.entity;

import com.pmc.market.model.user.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime period;

    @NotNull
    private String fullDescription;

    private String shortDescription;

    @NotNull
    private LocalDateTime regDate;

    private String businessNumber;

    private String businessName;

    @NotNull
    private String owner;

    @NotNull
    private String telephone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY) // 원칙상으로는 불가하지만(1인당 마켓 1개 생성가능) 원활한 테스트를 위해 1당 여러개 마켓을 생성할 수 있도록 허용
    @JoinColumn(name = "user_id")
    private User user;

    private Integer deliveryCost; // deliveryCost 원 이상 무료배송

    @Lob
    private String qnaDescription;

    @Lob
    private String shipDescription;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<ShopNotice> shopNotices = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<ShopTag> shopTags = new ArrayList<>();

    public void addFavorite(final Favorite favorite) {
        this.favorites.add(favorite);
        favorite.setShop(this);
    }

    public void removeFavorite(final Favorite favorite) {
        this.favorites.remove(favorite);
        favorite.setShop(null);
    }

    public void removeFavoriteAll() {
//        this.favorites.forEach(f -> f.delete());
        this.favorites = new ArrayList<>();
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

}