package com.pmc.market.entity;

import lombok.AllArgsConstructor;
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

    @Column
    private UserRole userRole;

    @Column
    private UserStatus userStatus;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime updateDate;
}
