package com.pmc.market.service;

import com.pmc.market.entity.Status;
import com.pmc.market.entity.User;
import com.pmc.market.model.dto.TokenResponseDto;
import com.pmc.market.model.dto.UserInfoResponseDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(User user);
    UserInfoResponseDto signIn(User user);
    UserInfoResponseDto signUp(User user);
    User updateUserStatus(Status status, String userEmail);
    void updateUserAuth(String auth, String userEmail);
    User getUserByEmail(String userEmail);
    User getUserById(Long id);
    void deleteUser(Long id);
    List<User> getUserList();
    UserInfoResponseDto getSocialUser(Map<String, Object> user);
}
