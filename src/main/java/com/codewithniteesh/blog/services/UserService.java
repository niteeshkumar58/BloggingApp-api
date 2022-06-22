package com.codewithniteesh.blog.services;

import com.codewithniteesh.blog.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto saveUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, int userId);
    UserDto getUserById(int userId);
    List<UserDto> getAllUsers();
    void deleteUser(int userId);
    // for first time user
    UserDto SignUpNewUser(UserDto userDto);
}
