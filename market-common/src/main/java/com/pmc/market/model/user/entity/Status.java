package com.pmc.market.model.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    WAIT("ROLE_WAIT", "대기 상태"),
    ACTIVE("ROLE_ACTIVE", "활성화 상태"),
    PAUSE("ROLE_PAUSE", "일시정지 상태"),
    STOP("ROLE_STOP", "정지 상태");

    private final String key;
    private final String title;

}
