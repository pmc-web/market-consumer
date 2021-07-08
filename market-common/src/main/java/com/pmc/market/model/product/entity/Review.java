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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review", cascade = CascadeType.ALL)
    private List<Attachment> attachments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToOne(mappedBy = "review", fetch = FetchType.LAZY) // 양방향
//    private OrderProduct orderProduct;
}
