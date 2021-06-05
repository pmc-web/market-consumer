package com.pmc.market.model.dto;

import com.pmc.market.entity.Role;
import com.pmc.market.entity.Status;
import com.pmc.market.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResponseDto {
    private String email;
    private String provider;
    private String address;
    private String name;
    private Role role;
    private Status status;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private TokenDto token;

    public static UserInfoResponseDto of(User user, TokenDto token) {
        String email = user.getProvider().equals("KAKAO") ? "KAKAO" + user.hashCode() : user.getEmail();
        return UserInfoResponseDto.builder()
                .email(email)
                .provider(user.getProvider())
                .name(user.getName())
                .regDate(user.getRegDate())
                .role(user.getRole())
                .status(user.getStatus())
                .token(token)
                .build();
    }

    public static UserInfoResponseDto of(User user) {
        String email = user.getProvider().equals("KAKAO") ? "KAKAO" + user.hashCode() : user.getEmail();
        return UserInfoResponseDto.builder()
                .email(email)
                .provider(user.getProvider())
                .name(user.getName())
                .regDate(user.getRegDate())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }
}
