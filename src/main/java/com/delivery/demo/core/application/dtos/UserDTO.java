package com.delivery.demo.core.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    @NotBlank(message="name is required")
    private String name;

    @NotBlank(message="email is required")
    private String email;

    @Email(message="dont is a email valid")
    @NotBlank(message="password is required")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
