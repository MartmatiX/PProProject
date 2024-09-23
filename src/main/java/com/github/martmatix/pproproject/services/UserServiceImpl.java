package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.DTOs.RegistrationFormUserDTO;
import com.github.martmatix.pproproject.database.entities.UserEntity;
import com.github.martmatix.pproproject.database.repositories.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService{

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean isValueEmpty(String... values) {
        for (String value : values) {
            if (value == null) {
                return true;
            }
            if (value.trim().isBlank() || value.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validatePassword(String password, String passwordRepeat) {
        return password.equals(passwordRepeat);
    }

    @Override
    public void convertUserToEntityAndSave(RegistrationFormUserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setName(userDTO.getFirstName());
        user.setSurname(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepository.save(user);
    }

}
