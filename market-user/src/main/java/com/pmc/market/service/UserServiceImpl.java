package com.pmc.market.service;

import com.pmc.market.entity.Role;
import com.pmc.market.entity.Status;
import com.pmc.market.entity.User;
import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.error.exception.MarketUnivException;
import com.pmc.market.error.exception.UserNotFoundException;
import com.pmc.market.model.dto.UserInfoResponseDto;
import com.pmc.market.repository.UserRepository;
import com.pmc.market.security.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MailSendService mailSendService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserInfoResponseDto signIn(User user) {
        User findUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException(user.getEmail()));
        if (!passwordEncoder.matches(user.getPassword(), findUser.getPassword())) {
            throw new BadCredentialsException(findUser.getEmail() + "의 비밀번호가 올바르지 않습니다.");
        }
        jwtTokenProvider.getAuthenticationLogin(user.getEmail());
        return UserInfoResponseDto.of(findUser, jwtTokenProvider.generateJwtToken(findUser));
    }

    @Override
    @Transactional
    public UserInfoResponseDto signUp(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new MarketUnivException("동일한 이메일의 계정이 존재합니다.", ErrorCode.INVALID_INPUT_VALUE);
        user.setStatus(Status.WAIT);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        String auth = mailSendService.sendAuthMail(user.getEmail());
        updateUserAuth(auth, user.getEmail()); // TODO : 회원가입시 토큰이 필요할까 ?
        return UserInfoResponseDto.of(createdUser, null);
    }

    @Override
    @Transactional
    public User updateUserStatus(Status status, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
        user.setStatus(status);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserAuth(String auth, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
        user.setAuthKey(auth);
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException(userEmail));
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUserList() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public UserInfoResponseDto getSocialUser(Map<String, Object> user) {
        Optional<User> findUser = userRepository.findByEmail(String.valueOf(user.get("userId")));
        if (!findUser.isPresent()) return createSocialUser(user);
        String token = jwtTokenProvider.generateJwtToken(findUser.get());
        return UserInfoResponseDto.of(findUser.get(), token);
    }

    private UserInfoResponseDto createSocialUser(Map<String, Object> user) {
        // 소셜 계정 회원가입
        User createUser = User.builder()
                .email(String.valueOf(user.get("userId")))
                .status(Status.ACTIVE)
                .provider("KAKAO")
                .role(Role.BUYER)
                .name(String.valueOf(user.get("userId")))
                .regDate(LocalDateTime.now())
                .authKey(String.valueOf(user.get("access_token")))
                .build();
        userRepository.save(createUser);
        String token = jwtTokenProvider.generateJwtToken(createUser);

        return UserInfoResponseDto.of(createUser, token);
    }

    @Override
    public boolean isUserAuth(String email, String auth) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.INVALID_INPUT_VALUE));
        if (!Objects.isNull(auth) && auth.equals(user.getAuthKey())) return true;
        return false;
    }

    @Override
    public User signUpConfirm(Status status, String email, String auth) {
        if (!isUserAuth(email, auth)) throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        return updateUserStatus(status, email);
    }

}
