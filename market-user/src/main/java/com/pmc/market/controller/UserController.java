package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.UserCreateRequestDto;
import com.pmc.market.model.dto.UserPasswordRequestDto;
import com.pmc.market.model.dto.UserStatusUpdateRequestDto;
import com.pmc.market.model.dto.UserUpdateRequestDto;
import com.pmc.market.domain.user.entity.Status;
import com.pmc.market.domain.user.entity.User;
import com.pmc.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = "유저 관리")
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        User user = userCreateRequestDto.toEntity(userCreateRequestDto);
        return ResponseEntity.ok().body(ResponseMessage.success(userService.signUp(user)));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        User user = userCreateRequestDto.toEntity(userCreateRequestDto);
        return ResponseEntity.ok().body(ResponseMessage.success(userService.signIn(user)));
    }

    @ApiOperation(value = "유저 상태 업데이트")
    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestBody @Valid UserStatusUpdateRequestDto request) {
        return ResponseEntity.ok(ResponseMessage.success(userService.updateUserStatus(request.getStatus(), request.getEmail())));
    }

    @ApiOperation(value = "메일 인증 확인", notes = "유저 회원 가입 후 메일로 인증 하는 서비스")
    @GetMapping("/sign-up-confirm")
    public ResponseEntity<?> signUpConfirm(@RequestParam(value = "email") String email, @RequestParam(value = "status") Status status, @RequestParam(value = "auth") String auth) {
        return ResponseEntity.ok(ResponseMessage.success(userService.signUpConfirm(status, email, auth)));
    }

    @ApiOperation(value = "판매자 전환")
    @PutMapping("/{id}/role")
    public ResponseEntity<?> changeUserRole(@ApiParam(value = "유저id") @PathVariable Long id) {
        userService.changeToSeller(id);
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @ApiOperation(value = "비밀번호 변경")
    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody UserPasswordRequestDto request) {
        userService.changePassword(request);
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @ApiOperation(value = "유저 정보 변경")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserInfo(@ApiParam(value = "유저id") @PathVariable long id, @RequestBody UserUpdateRequestDto request) {
        userService.updateUserInfo(id, request);
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @ApiOperation(value = "access 토큰 요청")
    @PostMapping("/{id}/refreshToken")
    public ResponseEntity<?> getRefreshToken(@ApiParam(value = "유저id") @PathVariable long id, @RequestBody String refreshToken) {
        return ResponseEntity.ok(ResponseMessage.success(userService.getRefreshToken(id, refreshToken)));
    }

    @ApiOperation(value = "닉네임 중복체크")
    @GetMapping("/nickname")
    public ResponseEntity<?> checkUserNickname(@ApiParam(value = "유저 닉네임") @RequestParam(value = "nickname") String nickname) {
        userService.checkUserNickname(nickname);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation(value = "유저 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ResponseMessage.success());
    }
}
