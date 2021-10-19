package com.pmc.market.model.vo.kakao;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoPayReadyVo {
    private String tid;
    private String next_redirect_pc_url;
    private LocalDateTime created_at;
}
