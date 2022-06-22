package com.codewithniteesh.blog.controllers;

import com.codewithniteesh.blog.payloads.JwtAuthRequest;
import com.codewithniteesh.blog.payloads.JwtAuthResponse;
import com.codewithniteesh.blog.payloads.UserDto;
import com.codewithniteesh.blog.security.CustomUserDetailsService;
import com.codewithniteesh.blog.security.JwtTokenHelper;
import com.codewithniteesh.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
        System.out.println("inside authController");
        this.authenticate(request.getUserName(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String token = jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private void authenticate(String username, String password) throws Exception{
        System.out.println("inside authenticate");
        System.out.println("username: "+username+" password: "+password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try{
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            System.out.println("Invalid credential !!!");
            throw new BadCredentialsException("Invalid Credential !!!");
        }
    }
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> SignUpNewUser(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.SignUpNewUser(userDto), HttpStatus.CREATED);
    }
}
