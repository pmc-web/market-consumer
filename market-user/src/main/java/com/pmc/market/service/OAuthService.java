package com.pmc.market.service;

import com.pmc.market.security.oauth.SocialLoginType;
import com.pmc.market.security.oauth.SocialOAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
            e.printStackTrace();
        }

    }

    public String requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialOAuth socialOAuth = this.findSocialOauthByType(socialLoginType);
        return socialOAuth.requestAccessToken(code);
    }

    private SocialOAuth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
}
