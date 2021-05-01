package com.pmc.market.controller;

import com.pmc.market.entity.Status;
import com.pmc.market.entity.User;
import com.pmc.market.entity.UserCreateRequestDto;
import com.pmc.market.entity.UserStatusUpdateRequestDto;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.repository.UserRepository;
import com.pmc.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    public ResponseMessage signUp(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        User user = userCreateRequestDto.toEntity(userCreateRequestDto);
        User createdUser = userService.signUp(user);
        return ResponseMessage.created(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseMessage get(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseMessage.success(user);
    }

    @ApiOperation(value = "유저 삭제")
    @DeleteMapping("/{id}")
    public ResponseMessage delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseMessage.success();
    }

    @ApiOperation(value = "유저 리스트 조회")
    @GetMapping
    public ResponseMessage list() {
        return ResponseMessage.success(userService.getUserList());
    }

    @ApiOperation(value = "유저 상태 업데이트")
    @PutMapping("/status")
    public ResponseMessage updateStatus(@RequestBody @Valid UserStatusUpdateRequestDto request) {
        return ResponseMessage.success(userService.updateUserStatus(request.getStatus(), request.getEmail()));
    }
}
