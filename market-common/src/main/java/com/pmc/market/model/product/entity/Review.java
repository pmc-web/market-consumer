package com.pmc.market.model.product.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column
    private String title;

    @Setter
    @Column
    private String content;

    @Column
    private LocalDateTime regDate;

    @Setter
    @Column
    private LocalDateTime updateDate;

//    @OneToOne(mappedBy = "review", fetch = FetchType.LAZY) // 양방향
//    private OrderProduct orderProduct;
}
