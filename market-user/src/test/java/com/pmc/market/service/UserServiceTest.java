package com.pmc.market.service;

import com.pmc.market.UserApplication;
import com.pmc.market.model.dto.UserPasswordRequestDto;
import com.pmc.market.model.user.entity.Role;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @DisplayName("판매자로 변경 (임시)")
    @Test
    void changeToSeller() {
        User user = userRepository.findById(1L).get();
        userService.changeToSeller(user.getId());
        assertTrue(user.getRole().equals(Role.SELLER));
    }

    @DisplayName("비밀번호 변경 - 0604 패스")
    @Test
    void changePassword() {
        String newPassword = "password123!";
        userService.changePassword(UserPasswordRequestDto.builder()
                .newPassword(newPassword).userId(1L).build());
    }

    @Test
    void updateUserInfo() {
    }
}