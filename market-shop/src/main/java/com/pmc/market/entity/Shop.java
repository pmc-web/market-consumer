package com.pmc.market.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDateTime period;

    @Column
    private String fullDescription;

    @Column
    private String shortDescription;

    @Column
    private LocalDateTime regDate;

    @Column
    private String businessNumber;

    @Column
    private String businessName;

    @Column
    private String owner;

    @Column
    private String telephone;

//    @ManyToOne
//    @JoinColumn(name= "category_id")
//    private Category category;

//    @ManyToOne
//    @JoinColumn(name= "user_id")
//    private User user;

}
