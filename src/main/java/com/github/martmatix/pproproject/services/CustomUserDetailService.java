package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userService.getUser(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User: " + username + " not found!");
        }

        UserEntity user = userOptional.get();
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, new ArrayList<>());
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
