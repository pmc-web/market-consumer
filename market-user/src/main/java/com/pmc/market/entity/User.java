package com.pmc.market.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = {"id"})// dirty check 방지
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column
    private String provider;

    @Column
    private String password;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String picture;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime updateDate;

    @Column
    private String authKey;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSearch> searches = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<ShipAddress> shipAddresses = new ArrayList<>();

//    @OneToMany(fetch = FetchType.LAZY) // TODO: shop entity를 가져올 수 없는데 어떡하지... 
//    private List<Claims> claims = new ArrayList<>();

//    @OneToMany
//    private List<Favorite> favoriteShop = new ArrayList<>();

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
