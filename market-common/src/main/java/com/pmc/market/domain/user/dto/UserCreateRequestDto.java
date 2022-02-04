package com.pmc.market.domain.user.dto;

import com.pmc.market.domain.user.entity.Role;
import com.pmc.market.domain.user.entity.Status;
import com.pmc.market.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("회원 가입 정보")
public class UserCreateRequestDto {
    @NotNull(message = "이메일을 입력하세요")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotNull(message = "패스워드를 입력하세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "8-16자의 영문 대소문자, 숫자, 특수문자 혼합")
    private String password;

    public User toEntity(UserCreateRequestDto user) {
        return User.builder()
                .nickname(user.toString().substring(46)) // TODO : 초기 name 설정
                .status(Status.WAIT)
                .provider("default")
                .role(Role.BUYER)
                .email(user.email)
                .password(user.password)
                .build();
    }
}
