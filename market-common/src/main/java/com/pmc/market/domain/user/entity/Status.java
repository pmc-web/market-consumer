package com.pmc.market.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    WAIT("ROLE_WAIT", "계정 대기 상태"),
    ACTIVE("ROLE_ACTIVE", "계정 활성화 상태"),
    PAUSE("ROLE_PAUSE", "계정 일시정지 상태"),
    STOP("ROLE_STOP", "계정 정지 상태");

    private final String key;
    private final String title;

}
