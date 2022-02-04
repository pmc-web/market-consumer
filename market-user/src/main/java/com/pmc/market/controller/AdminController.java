package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "유저 관리")
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final UserService userService;

    @ApiOperation(value = "유저 정보")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(ResponseMessage.success(userService.getUserById(id)));
    }

    @ApiOperation(value = "유저 리스트 조회")
    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok().body(ResponseMessage.success(userService.getUserList()));
    }

}
