package com.pmc.market.repository;

import com.pmc.market.UserApplication;
import com.pmc.market.domain.user.entity.Role;
import com.pmc.market.domain.user.entity.ShipAddress;
import com.pmc.market.domain.user.entity.Status;
import com.pmc.market.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShipAddressRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShipAddressRepository shipAddressRepository;

    @PersistenceContext
    EntityManager em;

    private User testUser;

    private Long shipAddressId;

    @DisplayName("1.배송지 추가")
    @BeforeEach
    void setup() {
        testUser = User.builder()
                .nickname("test")
                .role(Role.BUYER)
                .provider("test")
                .email("test@test.com")
                .status(Status.ACTIVE)
                .password("****")
                .build();
        User savedUser = userRepository.save(testUser);

        ShipAddress shipAddress = ShipAddress.builder()
                .address("서울 광진구 자양동")
                .detail("00아파트 11호")
                .addressName("집")
                .zipCode("11123")
                .user(savedUser)
                .build();
        ShipAddress savedShipAddress = shipAddressRepository.save(shipAddress);
        shipAddressId = savedShipAddress.getId();
        assertEquals(shipAddress.getId(), shipAddressId);
    }

    @DisplayName("2. 1에서 등록한 배송지 수정")
    @Test
    void updateShipAddress() {
        ShipAddress shipAddress = shipAddressRepository.findById(shipAddressId).get();
        shipAddress.updateAddress("address update", "detail update", "zipcode update", shipAddress.getAddressName());
        ShipAddress updated = shipAddressRepository.findById(shipAddressId).get();
        assertThat(shipAddress.getAddress()).isEqualTo(updated.getAddress());
        assertThat(shipAddress.getDetail()).isEqualTo(updated.getDetail());
        assertThat(shipAddress.getZipCode()).isEqualTo(updated.getZipCode());
    }

    @DisplayName("3. 유저 id로 배송지 목록을 받아온다. 1에서 등록한 주소가 있으므로 0개 이상이다.")
    @Test
    void getShipAddressList() {
        List<ShipAddress> result = shipAddressRepository.findByUser(testUser.getId());
        assertTrue(result.size() > 0);
    }

    @DisplayName("배송지 삭제: 없는 id 여도 에러가 발생하지 않는다.")
    @Test
    void deleteShipAddress() {
        assertDoesNotThrow(() -> shipAddressRepository.deleteById(512345L));
    }

    @DisplayName("1에서 저장한 배송지를 기본 배송지로 설정한다.")
    @Test
    void setDefaultAddress() {
        shipAddressRepository.setDefault(shipAddressId, true);
        em.flush();
        em.clear();
        ShipAddress defaultAddress = shipAddressRepository.findById(shipAddressId).get();
        assertThat(defaultAddress.getIsDefault()).isTrue();
    }

}