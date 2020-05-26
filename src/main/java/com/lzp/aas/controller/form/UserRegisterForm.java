package com.lzp.aas.controller.form;

import lombok.Data;

@Data
public class UserRegisterForm {
    private String username;

    private String fullName;

    private String password;

    private String passwordConfirmation;

}
