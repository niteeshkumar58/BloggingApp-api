package com.codewithniteesh.blog.security;

import com.codewithniteesh.blog.entities.User;
import com.codewithniteesh.blog.exceptions.ResourceNotFoundException;
import com.codewithniteesh.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load user from database by username
        // considering email as username
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Email Id", username));// need to handle this exception
        return user;
    }
}
