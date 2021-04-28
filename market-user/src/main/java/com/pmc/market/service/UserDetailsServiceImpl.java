package com.pmc.market.service;

import com.pmc.market.model.entity.Status;
import com.pmc.market.model.entity.User;
import com.pmc.market.error.LoginFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        User user = userService.selectUserByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("유저가 존재하지 않습니다.");
        if (Status.STOP.equals(user.getStatus())) throw new LoginFailException(Status.STOP.getKey());
        else if (Status.WAIT.equals(user.getStatus())) throw new LoginFailException(Status.WAIT.getKey());
        else if (Status.PAUSE.equals(user.getStatus())) throw new LoginFailException(Status.PAUSE.getKey());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
