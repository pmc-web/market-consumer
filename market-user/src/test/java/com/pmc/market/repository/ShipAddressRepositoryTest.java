package com.pmc.market.repository;

import com.pmc.market.UserApplication;
import com.pmc.market.entity.ShipAddress;
import com.pmc.market.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShipAddressRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShipAddressRepository shipAddressRepository;

    @DisplayName("배송지 추가")
    @Test
    void addShipAddress() {
        User user = userRepository.findByEmail("annna0449@naver.com").get();
        ShipAddress shipAddress = ShipAddress.builder()
                .address("서울 광진구 자양동")
                .detail("00아파트 11호")
                .addressName("집")
                .zipCode("11123")
                .user(user)
                .build();
        shipAddressRepository.save(shipAddress);
        assertEquals(shipAddress.getId(), shipAddressRepository.findById(shipAddress.getId()).get().getId());

    }

    @DisplayName("배송지 수정")
    @Test
    void updateShipAddress() {
        ShipAddress shipAddress = shipAddressRepository.findById(1L).get();
        shipAddress.updateAddress("address update", "detail update", "zipcode update", shipAddress.getAddressName());
        shipAddressRepository.save(shipAddress);

    }

    @DisplayName("유저 배송지 목록")
    @Test
    void getShipAddressList() {
        List<ShipAddress> result = shipAddressRepository.findByUser(3L);

        assertTrue(result.size() > 0);
    }

    @DisplayName("배송지 삭제")
    @Test
    void deleteShipAddress() {
        shipAddressRepository.deleteById(23L);
    }

    @DisplayName("기본 배송지")
    @Test
    void getDefaultAddress() {
        long userId = 3L;
        ShipAddress defaultAddress = shipAddressRepository.findUserDefaultAddress(userId);
        assertTrue(defaultAddress != null);
    }

    @DisplayName("기본 배송지")
    @Test
    void setDefaultAddress() {
        long id = 2L;
        shipAddressRepository.setDefault(id, false);
        ShipAddress shipAddress = shipAddressRepository.findById(id).get();
    }
}