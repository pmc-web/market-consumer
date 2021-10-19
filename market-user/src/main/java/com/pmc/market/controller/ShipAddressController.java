package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.ShipAddressRequestDto;
import com.pmc.market.service.ShipAddressService;
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
public class ShipAddressController {
    private final ShipAddressService shipAddressService;

    @ApiOperation(value = "배송지 추가")
    @PostMapping("/{userId}/address")
    public ResponseEntity<ResponseMessage> addShipAddress(@ApiParam("유저 id") @PathVariable("userId") long id, @RequestBody @Valid ShipAddressRequestDto request) {
        shipAddressService.addShipAddress(id, request);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation(value = "배송지 수정")
    @PutMapping("/{userId}/address/{addressId}")
    public ResponseEntity<ResponseMessage> updateShipAddress(@ApiParam("유저 id") @PathVariable("userId") long userId,
                                                             @ApiParam("주소 id") @PathVariable("addressId") long addressId,
                                                             @RequestBody @Valid ShipAddressRequestDto request) {
        shipAddressService.updateShipAddress(addressId, request);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation(value = "유저의 배송지 목록")
    @GetMapping("/{userId}/address")
    public ResponseEntity<ResponseMessage> getShipAddress(@ApiParam("유저 id") @PathVariable("userId") long id) {
        return ResponseEntity.ok(ResponseMessage.success(shipAddressService.getShipAddressList(id)));
    }

    @ApiOperation(value = "배송지 삭제")
    @DeleteMapping("/{userId}/address/{addressId}")
    public ResponseEntity<ResponseMessage> deleteShipAddress(@ApiParam("유저 id") @PathVariable("userId") long userId,
                                                             @ApiParam("주소 id") @PathVariable("addressId") long addressId) {
        shipAddressService.deleteShipAddress(userId, addressId);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation(value = "기본 배송지로 설정")
    @PatchMapping("/{userId}/address/{addressId}/default")
    public ResponseEntity<ResponseMessage> setDefaultAddress(@ApiParam("유저 id") @PathVariable("userId") long userId,
                                                             @ApiParam("주소 id") @PathVariable("addressId") long addressId) {
        boolean isSuccess = shipAddressService.setDefaultAddress(userId, addressId);
        if (isSuccess) return ResponseEntity.ok(ResponseMessage.success());
        return ResponseEntity.ok(ResponseMessage.fail("이미 기본 배송지 입니다."));
    }
}
