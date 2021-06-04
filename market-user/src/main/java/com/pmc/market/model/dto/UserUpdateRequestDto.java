package com.pmc.market.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("유저 내용 변경")
public class UserUpdateRequestDto {
    private String address;
    private String name;
}
