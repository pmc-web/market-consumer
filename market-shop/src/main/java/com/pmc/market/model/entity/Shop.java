package com.pmc.market.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @OneToOne
    @JoinColumn(name= "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    private Integer deliveryCost; // deliveryCost 원 이상 무료배송

    @Lob
    private String qnaDescription;

    @Lob
    private String shipDescription;
}
