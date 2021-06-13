package com.pmc.market.model.user.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    private long count;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "search", cascade = CascadeType.ALL)
    private List<UserSearch> searches = new ArrayList<>();

    public void updateSearch() {
        this.updateDate = LocalDateTime.now();
        this.count++;
    }
}
