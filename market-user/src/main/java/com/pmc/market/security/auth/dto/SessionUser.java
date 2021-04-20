package com.pmc.market.security.auth.dto;

import com.pmc.market.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    /*
        SessionUser에는 인증된 사용자 정보만 필요함
        @Entity User 클래스 SessionUser로 사용하지 않는 이유
        엔티티 클래스는 직렬화 클래스를 넣지 않는 것이 좋다
        why? 엔티티 클래스는 언제 다른 엔티티와의 관계가 맺어질지 모르기 때문이다.
     */

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
