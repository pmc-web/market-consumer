package com.pmc.market.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageHeader {
    private boolean result;
    private String resultCode;
    private int status;
    private String message;
}
