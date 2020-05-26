package com.lzp.aas.service.user;

import com.lzp.aas.controller.form.UserLoginForm;
import com.lzp.aas.controller.form.UserRegisterForm;
import com.lzp.aas.exception.AppException;
import com.lzp.aas.exception.HttpAppError;
import com.lzp.aas.model.Session;
import com.lzp.aas.model.User;
import com.lzp.aas.repository.UserRepository;
import com.lzp.aas.service.session.SessionManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRequestService {

    private final UserRepository userRepository;

    private final SessionManagementService sessionManagementService;

    private final UserManagementService userManagementService;

    public Session login(UserLoginForm userLoginForm) {
        Optional<User> userOptional = userRepository.findUserByUsername(userLoginForm.getUsername());
        if (userOptional.isEmpty()) throw new AppException(HttpAppError.INVALID_EMAIL_OR_PASSWORD);
        else {
            if (!BCrypt.checkpw(userLoginForm.getPassword(), userOptional.get().getPasswordDigest()))
                throw new AppException(HttpAppError.INVALID_EMAIL_OR_PASSWORD);
            else {
                return sessionManagementService.createSessionForUser(userOptional.get());
            }
        }
    }

    public User register(UserRegisterForm userRegisterForm) {
        if (!userRegisterForm.getPassword().equals(userRegisterForm.getPasswordConfirmation())) throw new AppException(HttpAppError.BAD_REQUEST);
        return userManagementService.createUser(userRegisterForm);
    }

}
