package com.pmc.market.oauth;
import com.pmc.market.entity.Role;
import com.pmc.market.entity.Status;
import com.pmc.market.entity.User;
import com.pmc.market.error.exception.InvalidValueException;
import com.pmc.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private final UserRepository userRepository;

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
        String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId();
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String id = oauth2User.getName(); // 1712535327
        String name = oauth2User.getAttribute("name");

        Optional<User> optionalUser = userRepository.findByEmail(id);
        User user;
        if(optionalUser.isPresent()){
            user = optionalUser.get();
            log.info(" 로그인 ", user.getId());
        }else {
            user = User.builder()
                    .name(name)
                    .provider(providerType)
                    .authKey(accessToken.getTokenValue())
                    .email(id)
                    .status(Status.WAIT)
                    .regDate(LocalDateTime.now())
                    .role(Role.BUYER)
                    .build();
            log.info("회원가입 ");
        }
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