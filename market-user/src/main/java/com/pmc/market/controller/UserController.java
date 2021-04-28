package com.pmc.market.controller;

import com.pmc.market.model.entity.User;
import com.pmc.market.model.entity.UserCreateRequestDto;
import com.pmc.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = "유저 관리")
@RequestMapping("/users")
@Slf4j
public class UserController {

    final UserService userService;

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
