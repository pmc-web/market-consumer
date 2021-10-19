package com.pmc.market.service;

import com.pmc.market.UserApplication;
import com.pmc.market.model.dto.ShipAddressRequestDto;
import com.pmc.market.model.dto.ShipAddressResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShipAddressServiceTest {
    ShipAddressRequestDto requestDto = ShipAddressRequestDto.builder()
            .address("서울 광진구 화양동")
            .detail("302호")
            .zipCode("421323")
            .addressName("집")
            .build();
    @Autowired
    private ShipAddressService shipAddressService;

    @DisplayName("배송지 추가")
    @Test
    void addShipAddress() {
        long userId = 3L;
        shipAddressService.addShipAddress(userId, requestDto);

    }

    @DisplayName("배송지 수정")
    @Test
    void updateShipAddress() {
        shipAddressService.updateShipAddress(1L, requestDto);
    }

    @DisplayName("유저 배송지 목록")
    @Test
    void getShipAddressList() {
        List<ShipAddressResponseDto> result = shipAddressService.getShipAddressList(3L);
        assertTrue(result.size() > 0);
    }

    @DisplayName("배송지 삭제")
    @Test
    void deleteShipAddress() {
        long id = 24L;
        shipAddressService.deleteShipAddress(3L, id);
    }

    @DisplayName("기본 배송지")
    @Test
    void setDefaultAddress() {

        shipAddressService.setDefaultAddress(3L, 3L);
    }
}
