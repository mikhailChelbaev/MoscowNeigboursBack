package com.moscow.neighbours.backend.controllers.auth.service.interfaces;

import com.moscow.neighbours.backend.controllers.auth.dto.NewUserDto;
import com.moscow.neighbours.backend.controllers.auth.dto.RoleDto;

public interface IUserService {

    NewUserDto saveUser(NewUserDto user);

    RoleDto saveRole(RoleDto role);

    void addRoleToUser(String username, String roleName);

}
