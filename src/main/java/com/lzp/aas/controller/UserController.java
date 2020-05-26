package com.lzp.aas.controller;

import com.lzp.aas.controller.form.UserLoginForm;
import com.lzp.aas.controller.form.UserRegisterForm;
import com.lzp.aas.model.Session;
import com.lzp.aas.model.User;
import com.lzp.aas.service.user.UserRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRequestService requestService;

    @PostMapping("/users/login")
    public ResponseEntity<Session> login(
            @RequestBody UserLoginForm userLoginForm) {
        return new ResponseEntity<>(requestService.login(userLoginForm), HttpStatus.OK);
    }

    @PostMapping("/users/register")
    public ResponseEntity<User> register(
            @RequestBody UserRegisterForm userRegisterForm) {
        return new ResponseEntity<>(requestService.register(userRegisterForm), HttpStatus.OK);
    }
}
