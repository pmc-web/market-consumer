package com.pmc.market.service;

import com.pmc.market.model.dto.TokenDto;
import com.pmc.market.model.dto.UserInfoResponseDto;
import com.pmc.market.model.dto.UserPasswordRequestDto;
import com.pmc.market.model.dto.UserUpdateRequestDto;
import com.pmc.market.model.user.entity.Status;
import com.pmc.market.model.user.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(User user);

    UserInfoResponseDto signIn(User user);

    UserInfoResponseDto signUp(User user);

    UserInfoResponseDto updateUserStatus(Status status, String userEmail);

    void updateUserAuth(String auth, String userEmail);

    UserInfoResponseDto getUserByEmail(String userEmail);

    UserInfoResponseDto getUserById(Long id);

    void deleteUser(Long id);

    List<UserInfoResponseDto> getUserList();

    UserInfoResponseDto getSocialUser(Map<String, Object> user);

    UserInfoResponseDto signUpConfirm(Status status, String email, String auth);

    void changeToSeller(Long id);

    void changePassword(UserPasswordRequestDto request);

    UserInfoResponseDto updateUserInfo(long id, UserUpdateRequestDto request);

    TokenDto getRefreshToken(long id, String refreshToken);

    boolean checkUserNickname(String nickname);
}
