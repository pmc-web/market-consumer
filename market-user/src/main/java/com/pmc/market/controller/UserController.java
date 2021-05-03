package com.pmc.market.controller;

import com.pmc.market.entity.CustomUserDetails;
import com.pmc.market.entity.User;
import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.model.dto.ResponseTokenDto;
import com.pmc.market.model.dto.UserCreateRequestDto;
import com.pmc.market.model.dto.UserStatusUpdateRequestDto;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.security.auth.TokenUtils;
import com.pmc.market.service.UserDetailsServiceImpl;
import com.pmc.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
        if (oAuth2User == null) throw new BusinessException("oauth error", ErrorCode.INTERNAL_SERVER_ERROR);
        return oAuth2User.toString();
    }

    @ApiImplicitParams({
            @ApiImplicitParam()
    })
    @ApiOperation(value = "회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        User user = userCreateRequestDto.toEntity(userCreateRequestDto);
        User createdUser = userService.signUp(user);
        String token = "Bearer " + TokenUtils.generateJwtToken(createdUser);
        return ResponseEntity.ok().body(ResponseMessage.success(ResponseTokenDto.builder().token(token).build()));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity signIn(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        User user = userCreateRequestDto.toEntity(userCreateRequestDto);

        ResponseTokenDto token = userService.signIn(user);
        return ResponseEntity.ok().body(ResponseMessage.success(token));
    }

    @ApiOperation(value = "유저 정보")
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(ResponseMessage.success(user));
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
