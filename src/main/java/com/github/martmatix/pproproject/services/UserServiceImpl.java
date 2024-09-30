package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.DTOs.RegistrationFormUserDTO;
import com.github.martmatix.pproproject.custom_authorities.Role;
import com.github.martmatix.pproproject.database.entities.UserEntity;
import com.github.martmatix.pproproject.database.repositories.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
    public boolean checkUserExistence(String username) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        return byUsername.isPresent();
    }

    @Override
    public void convertUserToEntityAndSave(RegistrationFormUserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setName(userDTO.getFirstName());
        user.setSurname(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setEnabled(false);
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAllBy();
    }

    @Override
    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public void delete(UserEntity user) {
        userRepository.delete(user);
    }

}
