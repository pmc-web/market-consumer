package com.pmc.market.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String social;

    @Column
    private String password;

    @Column
    private String address;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "status")
    private Status status;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime updateDate;

    @Builder
    public User(String nickname, String email, Role role, Status status){
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public User update(String nickname){
        this.nickname = nickname;
        return this;
    }
    public String getRoleKey(){
        return this.role.getKey();
    }
}
