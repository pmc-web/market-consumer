package com.pmc.market.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private AuthProvider provider;

    @NotNull
//    @JsonIgnore TODO : search
    private String password;

    @NotNull
    private String address;

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @NotNull
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
