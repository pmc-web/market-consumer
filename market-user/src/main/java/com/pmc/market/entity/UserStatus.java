package com.pmc.market.entity;

import lombok.Getter;

@Getter
public enum UserStatus {
    WAIT(0, "대기 상태"),
    ACTIVE(1, "활성화 상태"),
    PAUSE(2, "일시정지 상태"),
    STOP(3, "정지 상태");

    private Integer id;
    private String title;

    UserStatus(Integer id, String title){
        this.id = id;
        this.title = title;
    }
}
