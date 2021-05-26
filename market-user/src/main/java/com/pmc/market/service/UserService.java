package com.pmc.market.service;

import com.pmc.market.entity.Status;
import com.pmc.market.entity.User;
import com.pmc.market.model.dto.UserInfoResponseDto;
import com.pmc.market.model.dto.UserPasswordRequestDto;

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

    boolean isUserAuth(String email, String auth);

    User signUpConfirm(Status status, String email, String auth);

    void changeToSeller(Long id);

    void changePassword(UserPasswordRequestDto request);
}
