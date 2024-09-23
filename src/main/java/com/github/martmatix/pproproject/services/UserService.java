package com.github.martmatix.pproproject.services;

import com.github.martmatix.pproproject.DTOs.RegistrationFormUserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean isValueEmpty(String... values);

    boolean validatePassword(String password, String passwordRepeat);

    boolean checkUserExistence(String username);

    void convertUserToEntityAndSave(RegistrationFormUserDTO userDTO);

}
