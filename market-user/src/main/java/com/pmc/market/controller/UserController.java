package com.pmc.market.controller;

import com.pmc.market.entity.User;
import com.pmc.market.entity.UserCreateRequestDto;
import com.pmc.market.repository.UserRepository;
import com.pmc.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = "유저 관리")
@RequestMapping("/users")
@Slf4j
public class UserController {

    final private UserService userService;

    @ApiOperation(value = "oauth 로그인 사용자 정보")
    @GetMapping("my")
    public String getMyAuthenticationFromSession(@AuthenticationPrincipal OAuth2User oAuth2User) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return oAuth2User.toString();
    }

    @ApiOperation(value = "회원가입")
    @PostMapping("/sign-up")
    public void signUp(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        User user = userCreateRequestDto.toEntity(userCreateRequestDto);
        userService.signUp(user);
    }

    @ApiOperation(value = "유저 생성")
    @PostMapping
    public void createUser(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        User user = userCreateRequestDto.toEntity(userCreateRequestDto);
        userService.createUser(user);
    }

}
