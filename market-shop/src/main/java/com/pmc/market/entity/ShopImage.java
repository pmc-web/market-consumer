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
public class ShopImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private ImageType type;

//    @ManyToOne
//    @JoinColumn(name= "shop_id")
//    private Shop shop;

//    @ManyToOne
//    @JoinColumn(name= "attachment_id")
//    private Attachment attachment;
}
