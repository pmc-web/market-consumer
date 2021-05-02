package com.pmc.market.model.dto;

import com.pmc.market.entity.Role;
import com.pmc.market.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
public class UserInfoDto {
    private Long id;
    private String email;
    private String address;
    private String name;
    private Role role;
    private Status status;
    private LocalDateTime regDate;
}
