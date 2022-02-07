package com.pmc.market.repository;

import com.pmc.market.domain.user.entity.Role;
import com.pmc.market.domain.user.entity.Status;
import com.pmc.market.domain.user.entity.User;

class UserTest {
    static User createUser() {
        return User.builder()
                .nickname("test")
                .role(Role.BUYER)
                .provider("test")
                .email("test@test.com")
                .status(Status.ACTIVE)
                .password("****")
                .build();
    }

    public static User createUser(int i) {
        return User.builder()
                .nickname("test"+i)
                .role(Role.BUYER)
                .provider("test")
                .email(i+"test@test.com")
                .status(Status.ACTIVE)
                .password("****")
                .build();
    }
}