package com.pmc.market.service;

import com.pmc.market.error.exception.KakaoException;
import com.pmc.market.security.oauth.SocialLoginType;
import com.pmc.market.security.oauth.SocialOAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final List<SocialOAuth> socialOauthList;
    private final HttpServletResponse response;

    public void request(SocialLoginType socialLoginType) {
        SocialOAuth socialOAuth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOAuth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new KakaoException("카카오 계정 조회중 redirect 오류가 발생했습니다.");
        }
    }

    public Map<String, Object> requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialOAuth socialOAuth = this.findSocialOauthByType(socialLoginType);
        return socialOAuth.requestAccessToken(code);
    }

    private SocialOAuth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new KakaoException("알 수 없는 SocialLoginType 입니다."));
    }
}
