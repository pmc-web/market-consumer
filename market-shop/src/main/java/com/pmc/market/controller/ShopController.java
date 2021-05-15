package com.pmc.market.controller;

import com.pmc.market.entity.User;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.ShopInput;
import com.pmc.market.security.auth.CustomUserDetails;
import com.pmc.market.service.ShopService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Api(value = "Shop Controller", tags = "Shop 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;

    @ApiOperation("전체 마켓 리스트")
    @GetMapping("")
    public ResponseEntity<?> getAllShops() {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findAll()));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "쇼핑몰 이름", required = true, dataType = "string"),
            @ApiImplicitParam(name = "period", value = "쇼핑몰 기간(년 단위)", required = true, dataType = "int"),
            @ApiImplicitParam(name = "fullDescription", value = "상세한 마켓 소개", required = true, dataType = "string"),
            @ApiImplicitParam(name = "shortDescription", value = "짧은 마켓 소개", required = true, dataType = "string"),
            @ApiImplicitParam(name = "businessNumber", value = "사업자 번호", required = true, dataType = "string"),
            @ApiImplicitParam(name = "businessName", value = "사업자이름", required = true, dataType = "string"),
            @ApiImplicitParam(name = "owner", value = "별도 사업자명", required = true, dataType = "string"),
            @ApiImplicitParam(name = "telephone", value = "마켓 관계자 연락처", required = true, dataType = "string"),
            @ApiImplicitParam(name = "owner", value = "마켓 생성하는 유저의 이메일", required = true, dataType = "string")
    })
    @ApiOperation("가게 등록하기")
    @PostMapping
    public ResponseEntity<?> makeShop(@RequestBody @Valid ShopInput shopInput) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        shopService.makeShop(shopInput, user);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("인기순 마켓 count 개 조회")
    @GetMapping("/favorite")
    public ResponseEntity<?> getFavoriteShops(@ApiParam(value = "조회할 갯수") @RequestParam int count) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findFavorite(count)));
    }

}
