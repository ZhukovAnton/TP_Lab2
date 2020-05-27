package com.lzp.aas.controller;

import com.lzp.aas.config.Constants;
import com.lzp.aas.controller.form.UserLoginForm;
import com.lzp.aas.controller.form.UserRegisterForm;
import com.lzp.aas.model.Session;
import com.lzp.aas.model.User;
import com.lzp.aas.model.enums.UserRole;
import com.lzp.aas.service.user.AdminRequestService;
import com.lzp.aas.service.user.UserRequestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRequestService userRequestService;

    private final AdminRequestService adminRequestService;

    @PostMapping("/users/login")
    public ResponseEntity<Session> login(
            @RequestBody UserLoginForm userLoginForm) {
        return new ResponseEntity<>(userRequestService.login(userLoginForm), HttpStatus.OK);
    }

    @PostMapping("/users/register")
    public ResponseEntity<User> register(
            @RequestBody UserRegisterForm userRegisterForm) {
        return new ResponseEntity<>(userRequestService.register(userRegisterForm), HttpStatus.OK);
    }

    @PatchMapping("/admin/change_role")
    @ApiOperation(value = "", authorizations = {@Authorization(Constants.JWT_AUTH)})
    public ResponseEntity<Void> changeUserRole(
            @RequestHeader("authorization") String authorization,
            @RequestParam("change_for") Long forWhoChangingRoleId,
            @RequestParam("new_role") UserRole newRole) {
        adminRequestService.changeRoleFor(forWhoChangingRoleId, newRole);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
