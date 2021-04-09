package com.pmc.market.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column
    private String address;

    @Column
    private Integer payment;

    @Column
    private Integer amount;

    @Column
    private Integer totalPrice;

//    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus status;

}
