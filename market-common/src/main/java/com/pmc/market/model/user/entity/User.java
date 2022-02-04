package com.pmc.market.model.user.entity;


import com.pmc.market.model.BaseTimeEntity;
import com.pmc.market.model.order.entity.Order;
import com.pmc.market.model.product.entity.FavoriteProduct;
import com.pmc.market.model.product.entity.ProductQnA;
import com.pmc.market.model.product.entity.Review;
import com.pmc.market.model.shop.entity.Favorite;
import com.pmc.market.model.shop.entity.Shop;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = {"id", "email", "nickname"})// dirty check 방지
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    private String provider;

    @NotNull
    private String password;

    @NotNull
    private String nickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private String phoneNumber;

    private String authKey;

    @Column(columnDefinition = "배송지")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL) // ShipAddress.user
    private List<ShipAddress> shipAddresses = new ArrayList<>(); // mappedBy: 연관관계의 주인 = shipAddress

    @Column(columnDefinition = "구매 이력")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Column(columnDefinition = "마켓 좋아요")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorite> favoriteShops = new ArrayList<>();

    @Column(columnDefinition = "상품 좋아요")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<FavoriteProduct> favoriteProducts = new ArrayList<>();

    @Column(columnDefinition = "운영하는 마켓")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Shop> shops = new ArrayList<>(); // 원칙상으로는 one to one 개발환경 - one to many

    @Column(columnDefinition = "상품 문의글")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProductQnA> qnAS = new ArrayList<>();

    @Column(columnDefinition = "상품 리뷰")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @Column(columnDefinition = "쇼핑몰별 장바구니")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void updateUserInfo(String nickname, String phoneNumber) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public boolean isSameAuthKey(String authKey) {
        return this.getAuthKey().equals(authKey);
    }

    // 연관 관계 메서드
    public void like(Favorite favorite) {
        favoriteShops.add(favorite);
    }

    public void unLike(Favorite favorite) {
        favoriteShops.remove(favorite);
    }

    public void likeProduct(FavoriteProduct favoriteProduct) {
        favoriteProducts.add(favoriteProduct);
    }

    public void unLikeProduct(FavoriteProduct favoriteProduct) {
        favoriteProducts.remove(favoriteProduct);
    }
}
