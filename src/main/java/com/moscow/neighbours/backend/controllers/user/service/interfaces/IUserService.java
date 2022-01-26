package com.moscow.neighbours.backend.controllers.user.service.interfaces;

import com.moscow.neighbours.backend.controllers.user.dto.UserDto;

public interface IUserService {
    UserDto getUserInfo(String userId);
}
