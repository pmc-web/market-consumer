package com.pmc.market.service;

import com.pmc.market.UserApplication;
import com.pmc.market.entity.Role;
import com.pmc.market.entity.User;
import com.pmc.market.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @WithMockUser
    @DisplayName("판매자로 변경 (임시)")
    @Test
    void changeToSeller() {
        User user = userRepository.findById(1L).get();
        userService.changeToSeller(user.getId());
        assertTrue(user.getRole().equals(Role.SELLER));
    }
}