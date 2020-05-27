package com.lzp.aas.service.user;

import com.lzp.aas.controller.form.UserRegisterForm;
import com.lzp.aas.exception.AppException;
import com.lzp.aas.exception.HttpAppError;
import com.lzp.aas.model.User;
import com.lzp.aas.model.enums.UserRole;
import com.lzp.aas.repository.UserRepository;
import com.lzp.aas.utils.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminRequestService {

    private final UserRepository userRepository;

    private final UserManagementService userManagementService;

    public void changeRoleFor(Long forWhoChangingRole, UserRole newUserRole) {
        RequestUtil.checkAdminsRules();
        Optional<User> changeRoleForUserOptional = userRepository.findById(forWhoChangingRole);
        if (changeRoleForUserOptional.isEmpty()) throw new AppException(HttpAppError.NOT_FOUND);
        else {
            userManagementService.updateUserRole(changeRoleForUserOptional.get(), newUserRole);
        }
    }


}
