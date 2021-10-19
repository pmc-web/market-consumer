package com.pmc.market.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("유저 내용 변경 - 닉네임, 연락처 수정")
public class UserUpdateRequestDto {
    private String nickname;
    private String phoneNumber;
}
