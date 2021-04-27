package com.pmc.market.oauth;
import com.pmc.market.entity.Role;
import com.pmc.market.entity.User;
import com.pmc.market.error.exception.InvalidValueException;
import com.pmc.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by momentjin@gmail.com on 2019-12-11
 * Github : http://github.com/momentjin
 */

@Service
public class MyOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
        String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId();
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String id = oauth2User.getName();
        String name = oauth2User.getAttribute("name");

        User user = User.builder()
//                .id(Long.parseLong(id))
                .name(name)
                .prividerName(providerType)
                .authKey(accessToken.getTokenValue())
                .email(id)
                .regDate(LocalDateTime.now())
                .role(Role.BUYER)
                .build();
//                new Member(id, name, providerType, accessToken.getTokenValue());
        userRepository.save(user);
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String s, String s1) {
//        throw new NotImplementedException();
        throw new InvalidValueException(s);
    }

    @Override
    public void removeAuthorizedClient(String s, String s1) {
//        throw new NotImplementedException();
    }

}