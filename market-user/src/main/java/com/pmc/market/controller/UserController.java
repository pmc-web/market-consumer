package com.pmc.market.controller;

import com.pmc.market.entity.Status;
import com.pmc.market.entity.User;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.UserCreateRequestDto;
import com.pmc.market.model.dto.UserPasswordRequestDto;
import com.pmc.market.model.dto.UserStatusUpdateRequestDto;
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

    final private UserService userService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        User user = userCreateRequestDto.toEntity(userCreateRequestDto);
        return ResponseEntity.ok().body(ResponseMessage.success(userService.signUp(user)));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity signIn(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        User user = userCreateRequestDto.toEntity(userCreateRequestDto);
        return ResponseEntity.ok().body(ResponseMessage.success(userService.signIn(user)));
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
    public ResponseEntity<?> list() {
        return ResponseEntity.ok().body(ResponseMessage.success(userService.getUserList()));
    }

    @ApiOperation(value = "유저 상태 업데이트")
    @PutMapping("/status")
    public ResponseMessage updateStatus(@RequestBody @Valid UserStatusUpdateRequestDto request) {
        return ResponseMessage.success(userService.updateUserStatus(request.getStatus(), request.getEmail()));
    }

    @ApiOperation(value = "메일 인증 확인", notes = "유저 회원 가입 후 메일로 인증 하는 서비스")
    @GetMapping("/sign-up-confirm")
    public ResponseMessage signUpConfirm(@RequestParam(value = "email") String email, @RequestParam(value = "status") Status status, @RequestParam(value = "auth") String auth) {
        return ResponseMessage.success(userService.signUpConfirm(status, email, auth));
    }

    @ApiOperation(value = "판매자 전환")
    @PutMapping("/{id}/role")
    public ResponseEntity changeUserRole(@ApiParam(value = "유저id") @PathVariable Long id) {
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
    public ResponseEntity<?> updateUserInfo(@PathVariable long id) {
        userService.updateUserInfo(id);
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @ApiOperation(value = "refresh 토큰 요청")
    @GetMapping("/{id}/token")
    public ResponseEntity<?> getRefreshToken(@PathVariable long id) {
        return ResponseEntity.ok(ResponseMessage.success(userService.getRefreshToken(id)));
    }
}
