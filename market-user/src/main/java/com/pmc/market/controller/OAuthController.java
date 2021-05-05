package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.UserInfoResponseDto;
import com.pmc.market.security.oauth.SocialLoginType;
import com.pmc.market.service.OAuthService;
import com.pmc.market.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class OAuthController {
    private final OAuthService oauthService;
    private final UserService userService;

    /**
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     *
     * @param socialLoginType (KAKAO)
     */
    @ApiOperation(value = "소셜 로그인, 회원가입")
    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(
            @ApiParam(value = "소셜 로그인 타입 - kakao ")
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        oauthService.request(socialLoginType);
    }

    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     *
     * @param socialLoginType (KAKAO)
     * @param code            API Server 로부터 넘어는 code
     */
    @ApiOperation(value = "소셜 로그인, 회원가입시 자동 리다이렉트 되는 컨트롤러")
    @GetMapping(value = "/{socialLoginType}/callback")
    public ResponseEntity<?> callback(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestParam(name = "code") String code) {
        return ResponseEntity.ok().body(ResponseMessage.success(
                userService.getSocialUser(oauthService.requestAccessToken(socialLoginType, code))));
    }
}
