package com.pmc.market.model.dto;

import com.pmc.market.model.user.entity.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("유저 상태값 변경")
public class UserStatusUpdateRequestDto {
    @NotNull(message = "이메일을 입력하세요")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @ApiModelProperty(name = "email", value = "이메일")
    private String email;

    @ApiModelProperty(name = "status", value = "유저 상태 값(WAIT: 대기 상태, ACTIVE: 활성화 상태, ROLE_PAUSE: 일시정지 상태, ROLE_STOP: 정지 상태)")
    private Status status;

}
