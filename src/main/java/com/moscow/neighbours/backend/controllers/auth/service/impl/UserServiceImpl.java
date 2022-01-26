package com.moscow.neighbours.backend.controllers.auth.service.impl;

import com.moscow.neighbours.backend.controllers.auth.dto.NewUserDto;
import com.moscow.neighbours.backend.controllers.auth.dto.RoleDto;
import com.moscow.neighbours.backend.controllers.auth.service.interfaces.IUserService;
import com.moscow.neighbours.backend.db.datasource.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;

    @Override
    public NewUserDto saveUser(NewUserDto user) {
        return null;
    }

    @Override
    public RoleDto saveRole(RoleDto role) {
        return null;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

    }

}
