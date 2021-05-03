package com.pmc.market.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseTokenDto {
    private String token;
}
