package com.lzp.aas.service.user;


import com.lzp.aas.controller.form.UserRegisterForm;
import com.lzp.aas.model.User;
import com.lzp.aas.model.enums.UserRole;
import com.lzp.aas.repository.UserRepository;
import com.lzp.aas.service.session.SessionManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;

    private final SessionManagementService sessionManagementService;

    public User createUser(UserRegisterForm userRegisterForm) {
        User user = new User();
        user.setUsername(userRegisterForm.getUsername());
        user.setPasswordDigest(BCrypt.hashpw(userRegisterForm.getPassword(), BCrypt.gensalt()));
        user.setFullName(userRegisterForm.getFullName());
        user.setUserRole(UserRole.employee);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public void updateUserRole(User user, UserRole newRole) {
        user.setUserRole(newRole);
        userRepository.save(user);
        sessionManagementService.updateUsersSession(user);
    }
}
