package com.pmc.market.domain.shop.entity;

import com.pmc.market.domain.BaseTimeEntity;
import com.pmc.market.domain.order.entity.Order;
import com.pmc.market.domain.product.entity.Product;
import com.pmc.market.domain.user.entity.User;
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
public class Shop extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    private String name;

    private LocalDateTime period;

    private String fullDescription;

    private String shortDescription;

    private String businessNumber;

    private String businessName;

    @Column(nullable = false)
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

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<ShopNotice> shopNotices = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<ShopTag> shopTags = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<ShopImage> attachments = new ArrayList<>();

    // 연관 관계 메서드
    public void liked(Favorite favorite) {
        if (favorites == null) {
            favorites = new ArrayList<>();
        }
        favorites.add(favorite);
    }

    public void unLiked(Favorite favorite) {
        favorites.remove(favorite);
    }

    public void tagging(ShopTag shopTag) {
        shopTags.add(shopTag);
    }

    public void unTagging(ShopTag shopTag) {
        shopTags.remove(shopTag);
    }

    // 로직 관련
    public void updateCategory(Category category) {
        this.category = category;
    }

    public void addImages(List<ShopImage> attachments) {
        attachments.addAll(attachments);
    }


    public List<Favorite> getFavorites() {
        return favorites == null ? new ArrayList<>() : favorites;
    }

    public List<ShopNotice> getShopNotices() {
        return shopNotices == null ? new ArrayList<>() : shopNotices;
    }

    public List<ShopTag> getShopTags() {
        return shopTags == null ? new ArrayList<>() : shopTags;
    }

    public List<Product> getProducts() {
        return products == null ? new ArrayList<>() : products;
    }

    public List<Order> getOrders() {
        return orders == null ? new ArrayList<>() : orders;
    }
}