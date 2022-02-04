//package com.pmc.market.service;
//
//import com.pmc.market.UserApplication;
//import com.pmc.market.model.dto.UserInfoResponseDto;
//import com.pmc.market.model.dto.UserPasswordRequestDto;
//import com.pmc.market.domain.user.entity.Role;
//import com.pmc.market.domain.user.entity.Status;
//import com.pmc.market.domain.user.entity.User;
//import com.pmc.market.repository.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = {UserApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//class UserServiceTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @DisplayName("판매자로 변경 (임시)")
//    @Test
//    void changeToSeller() {
//        User user = userRepository.findById(1L).get();
//        userService.changeToSeller(user.getId());
//        assertTrue(user.getRole().equals(Role.SELLER));
//    }
//
//    @DisplayName("비밀번호 변경 - 0604 패스")
//    @Test
//    void changePassword() {
//        String newPassword = "password123!";
//        userService.changePassword(UserPasswordRequestDto.builder()
//                .newPassword(newPassword).userId(1L).build());
//    }
//
//    @DisplayName("유저 상태 업데이트 ")
//    @Test
//    void updateUserStatus() {
//        Status status = Status.ACTIVE;
////        User user = userService.updateUserStatus2(status, "ansongi527@gmail.com");
//        UserInfoResponseDto dto = userService.updateUserStatus(status, "ansongi527@gmail.com");
//        assertTrue(dto.getStatus().equals(status));
//    }
//}