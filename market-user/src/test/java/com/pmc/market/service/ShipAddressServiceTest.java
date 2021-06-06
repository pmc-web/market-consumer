package com.pmc.market.service;

import com.pmc.market.UserApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShipAddressServiceTest {
    @Autowired
    private ShipAddressService shipAddressService;

    @DisplayName("배송지 추가")
    @Test
    void addShipAddress() {
        // 실행 후 user 에 shipList 에 있는지 확인
    }
}
