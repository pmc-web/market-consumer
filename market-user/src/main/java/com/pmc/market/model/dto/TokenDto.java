package com.pmc.market.model.dto;

import com.pmc.market.security.auth.AuthConstants;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@ApiModel("토큰 정보")
public class TokenDto {
    private String accessToken;
    private String refreshToken;

    public static TokenDto of(String accessToken, String refreshToken) {
        return TokenDto.builder()
                .accessToken(AuthConstants.TOKEN_TYPE + " " + accessToken)
                .refreshToken(AuthConstants.TOKEN_TYPE + " " + refreshToken)
                .build();
    }
}
