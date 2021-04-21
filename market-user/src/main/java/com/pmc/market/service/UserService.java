package com.pmc.market.service;

import com.pmc.market.entity.User;

public interface UserService {
    User createUser(User user);
    User signUp(User user);
}
