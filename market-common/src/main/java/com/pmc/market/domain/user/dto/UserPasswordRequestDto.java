package com.pmc.market.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
@ApiModel("패스워드 변경")
public class UserPasswordRequestDto {

    @NotNull(message = "유저 Id를 입력하세요")
    @ApiModelProperty(name = "userId", value = "유저 Id (DB의 index)")
    private Long userId;

    @NotNull(message = "새로운 패스워드를 입력하세요")
    @ApiModelProperty(name = "password", value = "새로운 패스워드")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "8-16자의 영문 대소문자, 숫자, 특수문자 혼합")
    private String newPassword;
}
