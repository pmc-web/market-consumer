package com.pmc.market.entity;

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

    @NotNull
    private String shortDescription;

    @NotNull
    private LocalDateTime regDate;

    @NotNull
    private String businessNumber;

    @NotNull
    private String businessName;

    @NotNull
    private String owner;

    @NotNull
    private String telephone;

//    @ManyToOne
//    @JoinColumn(name= "category_id")
//    private Category category;

//    @ManyToOne
//    @JoinColumn(name= "user_id")
//    private User user;

}
