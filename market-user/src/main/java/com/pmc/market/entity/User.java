package com.pmc.market.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter // todo : setter X
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull
    private String email;

//    @NotNull
    private String prividerName;

//    @NotNull
    private String password;

    private String address;

//    @NotNull
    private String name;

//    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private String picture;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime updateDate;

    @Column
    private String authKey;

//    @Builder
    public User(String name, String email, Role role, Status status, String picture) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.picture = picture;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
