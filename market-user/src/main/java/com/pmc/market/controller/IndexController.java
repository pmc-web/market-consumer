package com.pmc.market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("my")
    public String getMyAuthenticationFromSession(@AuthenticationPrincipal OAuth2User oAuth2User) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return oAuth2User.toString();
    }
}
