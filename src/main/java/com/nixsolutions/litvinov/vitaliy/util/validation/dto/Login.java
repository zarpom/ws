package com.nixsolutions.litvinov.vitaliy.util.validation.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Login {
    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[\\w\\d]+$", message = "must be one word without space")
    private String login;
    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[\\w\\d]+$", message = "must be one word without space")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login [login=" + login + ", password=" + password + "]";
    }
}
