package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.DTOs.RegistrationFormUserDTO;
import com.github.martmatix.pproproject.database.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean isValueEmpty(String... values);

    boolean validatePassword(String password, String passwordRepeat);

    boolean checkUserExistence(String username);

    void convertUserToEntityAndSave(RegistrationFormUserDTO userDTO);

    Optional<UserEntity> getUser(String username);

    List<UserEntity> getAllUsers();

    void saveUser(UserEntity user);

    void delete(UserEntity user);

    List<UserEntity> getAllActiveUsers(boolean enabled);

}
