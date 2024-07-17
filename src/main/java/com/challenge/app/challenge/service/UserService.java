package com.challenge.app.challenge.service;

import com.challenge.app.challenge.perseistence.entity.User;
import java.util.Optional;

public interface UserService {

    Optional<User> findOneByUsername(String username);

}
