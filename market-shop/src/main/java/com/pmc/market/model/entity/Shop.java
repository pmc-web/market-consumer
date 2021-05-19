package com.pmc.market.model.entity;
import com.pmc.market.entity.User;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    @ManyToOne
    @JoinColumn(name= "category_id")
    private Category category;

    @ManyToOne // 원칙상으로는 불가하지만(1인당 마켓 1개 생성가능) 원활한 테스트를 위해 1당 여러개 마켓을 생성할 수 있도록 허용
    @JoinColumn(name= "user_id")
    private User user;

    private Integer deliveryCost; // deliveryCost 원 이상 무료배송

    @Lob
    private String qnaDescription;

    @Lob
    private String shipDescription;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    public void addFavorite(Favorite favorite){
        Collection<Favorite> likes = getFavorites();
        likes.add(favorite);
    }
    public void removeFavorite(Favorite favorite){
        Collection<Favorite> likes = getFavorites();
        likes.remove(favorite);
    }
}