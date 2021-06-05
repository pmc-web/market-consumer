package com.pmc.market.model.entity;

import com.pmc.market.entity.User;
import com.pmc.market.model.dto.ShopRequestDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public void update(ShopRequestDto shopRequestDto) {
        this.name = shopRequestDto.getName();
        this.period = this.regDate.plusYears(shopRequestDto.getPeriod());
        this.fullDescription = shopRequestDto.getFullDescription();
        this.shortDescription = shopRequestDto.getShortDescription();
        this.businessNumber = shopRequestDto.getBusinessNumber();
        this.businessName = shopRequestDto.getBusinessName();
        this.owner = shopRequestDto.getOwner();
        this.telephone = shopRequestDto.getTelephone();
        this.deliveryCost = shopRequestDto.getDeliveryCost();
        this.qnaDescription = shopRequestDto.getQnaDescription();
        this.shipDescription = shopRequestDto.getShipDescription();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

}