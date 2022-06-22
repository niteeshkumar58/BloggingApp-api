package com.codewithniteesh.blog.services.Impl;

import com.codewithniteesh.blog.entities.Role;
import com.codewithniteesh.blog.entities.User;
import com.codewithniteesh.blog.exceptions.DuplicateEntryException;
import com.codewithniteesh.blog.exceptions.ResourceNotFoundException;
import com.codewithniteesh.blog.payloads.UserDto;
import com.codewithniteesh.blog.repositories.RoleRepo;
import com.codewithniteesh.blog.repositories.UserRepo;
import com.codewithniteesh.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.codewithniteesh.blog.config.ApiConstants.NORMAL_USER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDto saveUser(UserDto userDto) {
        //checking for duplicate entry of email id
        User existingUser = userRepo.findByEmail(userDto.getEmail()).orElse(null);
        if(existingUser!=null){
            throw new DuplicateEntryException("Email", userDto.getEmail());
        }
        User user = userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, int userId) {
        User user = userRepo.findById(userId)
                    .orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        User updatedUser = this.userRepo.save(user);
        return userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(int userId) {
        return userToUserDto(userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId)));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = this.userRepo.findAll();
        List<UserDto> userDtoList = userList
                .stream()
                .map(user->this.userToUserDto(user))
                .collect(Collectors.toList());

        return userDtoList;
    }

    @Override
    public void deleteUser(int userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }


    @Override
    public UserDto SignUpNewUser(UserDto userDto) {
        //checking for duplicate entry of email id
        User existingUser = userRepo.findByEmail(userDto.getEmail()).orElse(null);
        if(existingUser!=null){
            throw new DuplicateEntryException("Email", userDto.getEmail());
        }

        User user = modelMapper.map(userDto, User.class);
        Role role = roleRepo.findById(NORMAL_USER).orElseThrow(()->new ResourceNotFoundException("Role", "RoleName", "NORMAL_USER"));

        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepo.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    private User userDtoToUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }

    private UserDto userToUserDto(User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
