package com.pmc.market.model.dto;

import com.pmc.market.entity.Status;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserStatusUpdateRequestDto {
    @NotNull(message="이메일을 입력하세요")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @ApiParam(name = "email", value ="이메일")
    private String email;

    @ApiParam(name = "status", value ="유저 상태 값(WAIT: 대기 상태, ACTIVE: 활성화 상태, ROLE_PAUSE: 일시정지 상태, ROLE_STOP: 정지 상태)")
    private Status status;

}
