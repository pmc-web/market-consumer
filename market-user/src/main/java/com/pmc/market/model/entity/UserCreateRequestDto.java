package com.pmc.market.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequestDto {
    @NotNull(message="이메일을 입력하세요")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotNull(message="패스워드를 입력하세요")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "8-16자의 영문 대소문자, 숫자, 특수문자 혼합")
   private String password;

   public User toEntity(UserCreateRequestDto user) {
       return User.builder()
               .role(Role.BUYER)
               .email(user.email)
               .password(user.password)
               .regDate(LocalDateTime.now())
               .build();
   }
}
