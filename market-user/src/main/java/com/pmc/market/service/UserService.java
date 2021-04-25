package com.pmc.market.service;

import com.pmc.market.entity.Status;
import com.pmc.market.entity.User;

public interface UserService {
    User createUser(User user);
    User signUp(User user);
    void updateUserStatus(Status status, String userEmail);
    void updateUserAuth(String auth, String userEmail);
    User selectUserByEmail(String userEmail);
}
