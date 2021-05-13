package com.pmc.market.security.auth;

import com.pmc.market.entity.Status;
import com.pmc.market.entity.User;
import com.pmc.market.error.exception.LoginFailException;
import com.pmc.market.error.exception.UserNotFoundException;
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
        List<GrantedAuthority> authorities = new ArrayList<>();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (Status.STOP.equals(user.getStatus())) throw new LoginFailException(Status.STOP.getKey());
        else if (Status.WAIT.equals(user.getStatus())) throw new LoginFailException(Status.WAIT.getKey());
        else if (Status.PAUSE.equals(user.getStatus())) throw new LoginFailException(Status.PAUSE.getKey());

        return new CustomUserDetails(user, Collections.singleton(new SimpleGrantedAuthority("USER")));
    }
}

