package com.codewithniteesh.blog.controllers;

import com.codewithniteesh.blog.payloads.ApiResponse;
import com.codewithniteesh.blog.payloads.UserDto;
import com.codewithniteesh.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUserDto = userService.saveUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable int userId){
        UserDto fetchedUserDto = userService.getUserById(userId);
        return new ResponseEntity<>(fetchedUserDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> listOfUserDto = userService.getAllUsers();
        return new ResponseEntity<>(listOfUserDto, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId){
        UserDto updatedUserDto = this.userService.updateUser(userDto,userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") int userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User with userId "+userId+" has been deleted successfully", true), HttpStatus.OK);
        //return new ResponseEntity<>(Map.of("messege", "User with userId "+userId+" has been deleted successfully"), HttpStatus.OK);
    }
}
