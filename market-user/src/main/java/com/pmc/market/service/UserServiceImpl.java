package com.pmc.market.service;

import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.ErrorCode;
import com.pmc.market.error.exception.MarketUnivException;
import com.pmc.market.error.exception.UserNotFoundException;
import com.pmc.market.model.dto.TokenDto;
import com.pmc.market.model.dto.UserInfoResponseDto;
import com.pmc.market.model.dto.UserPasswordRequestDto;
import com.pmc.market.model.dto.UserUpdateRequestDto;
import com.pmc.market.model.user.entity.Role;
import com.pmc.market.model.user.entity.Status;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.repository.UserRepository;
import com.pmc.market.security.auth.CustomUserDetails;
import com.pmc.market.security.auth.JwtTokenProvider;
import com.pmc.market.security.auth.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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
        Authentication auth = jwtTokenProvider.getAuthenticationLogin(user.getEmail()); // 이메일로 인증 정보 조회

        User loginUser = ((CustomUserDetails) auth.getPrincipal()).getUser();
        if (!passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            throw new BadCredentialsException(loginUser.getEmail() + "의 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtTokenProvider.generateJwtAccessToken(loginUser);
        String refreshToken = jwtTokenProvider.generateJwtRefreshToken(loginUser); // refresh token
        redisUtil.setDataExpire(refreshToken, loginUser.getEmail(), jwtTokenProvider.REFRESH_TOKEN_VALID_TIME);
        return UserInfoResponseDto.of(loginUser, TokenDto.of(accessToken, refreshToken));
    }

    @Transactional
    @Override
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

    @Transactional
    @Override
    public UserInfoResponseDto updateUserStatus(Status status, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
        user.setStatus(status);
        return UserInfoResponseDto.of(user);
    }

    @Override
    @Transactional
    public void updateUserAuth(String auth, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
        user.setAuthKey(auth);
        userRepository.save(user);
    }

    @Override
    public UserInfoResponseDto getUserByEmail(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException(userEmail));
        return UserInfoResponseDto.of(user);
    }

    @Override
    public UserInfoResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return UserInfoResponseDto.of(user);
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
                .nickname(String.valueOf(user.get("userId")))
                .regDate(LocalDateTime.now())
                .authKey(String.valueOf(user.get("access_token")))
                .build();
        userRepository.save(createUser);
        String accessToken = jwtTokenProvider.generateJwtAccessToken(createUser);
        String refreshToken = jwtTokenProvider.generateJwtRefreshToken(createUser);
        redisUtil.setDataExpire(refreshToken, createUser.getEmail(), 60 * 30L);
        return UserInfoResponseDto.of(createUser, TokenDto.of(accessToken, refreshToken));
    }

    @Transactional
    @Override
    public UserInfoResponseDto signUpConfirm(Status status, String email, String auth) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.INVALID_INPUT_VALUE));
        if (!user.isSameAuthKey(auth)) throw new BusinessException("인증키를 확인해 주세요", ErrorCode.INVALID_INPUT_VALUE);
        user.setStatus(status);
        return UserInfoResponseDto.of(user);
    }

    @Override
    public void changeToSeller(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (user.getRole().equals(Role.SELLER))
            throw new BusinessException("이미 판매자입니다.", ErrorCode.INVALID_INPUT_VALUE);
        user.setRole(Role.SELLER);
        userRepository.save(user);
    }

    @Override
    public void changePassword(UserPasswordRequestDto request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        UUID uuid = UUID.randomUUID();
        userRepository.updatePassword(passwordEncoder.encode(request.getNewPassword()), request.getUserId());
        redisUtil.setDataExpire(uuid.toString(), user.getEmail(), 60 * 30L); // refresh token 변경
    }

    @Transactional
    @Override
    public UserInfoResponseDto updateUserInfo(long id, UserUpdateRequestDto request) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.updateUserInfo(request.getNickname(), request.getPhoneNumber());
        return UserInfoResponseDto.of(user);
    }

    @Override
    public TokenDto getRefreshToken(long id, String refreshToken) {
        if (!jwtTokenProvider.isValidToken(refreshToken))
            throw new BusinessException("refresh token 이 올바르지 않습니다.", ErrorCode.UNAUTHORIZED);
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return TokenDto.of(jwtTokenProvider.generateJwtAccessToken(user), jwtTokenProvider.generateJwtRefreshToken(user));
    }

    @Override
    public boolean checkUserNickname(String nickname) {
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new BusinessException("중복된 닉네임 입니다.", ErrorCode.DUPLICATE_ENTITY);
        }
        return true;
    }
}
