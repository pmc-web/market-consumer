package com.pmc.market.service;

import com.pmc.market.entity.Role;
import com.pmc.market.entity.Status;
import com.pmc.market.entity.User;
import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.error.exception.MarketUnivException;
import com.pmc.market.error.exception.UserNotFoundException;
import com.pmc.market.model.dto.TokenDto;
import com.pmc.market.model.dto.UserInfoResponseDto;
import com.pmc.market.model.dto.UserPasswordRequestDto;
import com.pmc.market.repository.UserRepository;
import com.pmc.market.security.auth.JwtTokenProvider;
import com.pmc.market.security.auth.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MailSendService mailSendService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisUtil redisUtil;

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
        jwtTokenProvider.getAuthenticationLogin(user.getEmail()); // 이메일로 인증 정보 조회
        String accessToken = jwtTokenProvider.generateJwtAccessToken(findUser);
        String refreshToken = jwtTokenProvider.generateJwtRefreshToken(findUser); // refresh token
        redisUtil.setDataExpire(refreshToken, findUser.getEmail(), jwtTokenProvider.REFRESH_TOKEN_VALID_TIME);
        return UserInfoResponseDto.of(findUser, TokenDto.of(accessToken, refreshToken));
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
        updateUserAuth(auth, user.getEmail());
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
    public List<UserInfoResponseDto> getUserList() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserInfoResponseDto::of).collect(Collectors.toList());
    }

    @Override
    public UserInfoResponseDto getSocialUser(Map<String, Object> user) {
        Optional<User> findUser = userRepository.findByEmail(String.valueOf(user.get("userId")));
        if (!findUser.isPresent()) return createSocialUser(user);
        String accessToken = jwtTokenProvider.generateJwtAccessToken(findUser.get());
        String refreshToken = jwtTokenProvider.generateJwtRefreshToken(findUser.get());
        redisUtil.setDataExpire(refreshToken, findUser.get().getEmail(), 60 * 30L);
        return UserInfoResponseDto.of(findUser.get(), TokenDto.of(accessToken, refreshToken));
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
        String accessToken = jwtTokenProvider.generateJwtAccessToken(createUser);
        String refreshToken = jwtTokenProvider.generateJwtRefreshToken(createUser);
        redisUtil.setDataExpire(refreshToken, createUser.getEmail(), 60 * 30L);
        return UserInfoResponseDto.of(createUser, TokenDto.of(accessToken, refreshToken));
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

    @Override
    public void changeToSeller(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        if (user.getRole().equals(Role.SELLER))
            throw new BusinessException("이미 판매자입니다.", ErrorCode.INVALID_INPUT_VALUE);
        user.setRole(Role.SELLER);
        userRepository.save(user);
    }

    @Override
    public void changePassword(UserPasswordRequestDto request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException());
        UUID uuid = UUID.randomUUID();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        redisUtil.setDataExpire(uuid.toString(), user.getEmail(), 60 * 30L);
    }

    @Override
    public UserInfoResponseDto updateUserInfo(long id) {
        try {

        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public String getRefreshToken(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return jwtTokenProvider.generateJwtRefreshToken(user);
    }
}
