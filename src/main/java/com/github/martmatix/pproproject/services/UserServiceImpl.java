package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.database.repositories.UserRepository;
import jakarta.annotation.Resource;

public class UserServiceImpl implements UserService{

    @Resource
    private UserRepository userRepository;


}
