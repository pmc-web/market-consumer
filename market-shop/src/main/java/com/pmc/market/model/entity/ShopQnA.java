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
public class ShopQnA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime regDate;

    @NotNull
    private String comment;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
