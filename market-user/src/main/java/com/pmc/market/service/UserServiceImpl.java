package com.pmc.market.service;

import com.pmc.market.entity.User;
import com.pmc.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    final UserRepository userRepository;

    final MailSendService mailSendService;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User signUp(User user) {
        User createdUser = userRepository.save(user);
        mailSendService.sendAuthMail(user.getEmail());
        return createdUser;
    }

}
