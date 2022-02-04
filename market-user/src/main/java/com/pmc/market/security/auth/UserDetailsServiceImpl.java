package com.pmc.market.security.auth;

import com.pmc.market.error.exception.LoginFailException;
import com.pmc.market.error.exception.UserNotFoundException;
import com.pmc.market.model.user.entity.Status;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (Status.ACTIVE.equals(user.getStatus())) {
            return new CustomUserDetails(user,
                    Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())));
        }
        throw new LoginFailException(user.getStatus().getKey());
    }
}

