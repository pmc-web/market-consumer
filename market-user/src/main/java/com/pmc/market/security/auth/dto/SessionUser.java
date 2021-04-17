package com.pmc.market.security.auth.dto;

import com.pmc.market.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String nickname;
    private String email;

    public SessionUser(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
