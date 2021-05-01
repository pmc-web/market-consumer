package com.pmc.market.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer amount;

    private String description;

}
