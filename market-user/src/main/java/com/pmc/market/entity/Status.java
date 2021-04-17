package com.pmc.market.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    WAIT("ROLE_WAIT", "대기 상태"),
    ACTIVE("ROLE_ACTIVE", "활성화 상태"),
    PAUSE("ROLE_PAUSE", "일시정지 상태"),
    STOP("ROLE_STOP", "정지 상태");

    private final String key;
    private final String title;

}
