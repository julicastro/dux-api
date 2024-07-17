package com.challenge.app.challenge.service.impl;

import com.challenge.app.challenge.perseistence.entity.User;
import com.challenge.app.challenge.perseistence.repository.UserRepository;
import com.challenge.app.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
