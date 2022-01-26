package com.moscow.neighbours.backend.controllers.user.service.impl;

import com.moscow.neighbours.backend.controllers.user.dto.UserDto;
import com.moscow.neighbours.backend.controllers.user.service.interfaces.IUserService;
import com.moscow.neighbours.backend.db.datasource.UserRepository;
import com.moscow.neighbours.backend.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUserInfo(String userId) {
        var dbUser = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);

        var user = UserDto.builder()
                .email(dbUser.getEmail())
                .name(dbUser.getName())
                .isVerified(dbUser.isVerified())
                .build();
        return user;
    }
}
