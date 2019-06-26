package com.nixsolutions.litvinov.vitaliy.util.validation.dto;

import com.nixsolutions.litvinov.vitaliy.validate.EmailNotExist;
import com.nixsolutions.litvinov.vitaliy.validate.UserNotExist;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;


public class UserAddForm {
    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[\\w\\d]+$", message = "must be one word without space")
    @UserNotExist(message = "login already exist")
    private String login;
    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[\\w\\d]+$", message = "must be one word without space")
    private String password;
    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[\\w\\d]+$", message = "must be one word without space")
    private String repassword;
    @NotNull
    @Size(min = 2, max = 30)
    @Email
    @EmailNotExist(message = "email already exist")
    private String email;
    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[\\w\\d]+$", message = "must be one word without space")
    private String firstName;
    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[\\w\\d]+$", message = "must be one word without space")
    private String lastName;
    @NotNull
    @Past
    @DateTimeFormat (pattern = "yyyy-MM-dd" )
    private Date birthday;
    @NotNull
    private String role;

    public UserAddForm() {
        super();
    }

    public UserAddForm(String login, String password, String repassword,
            String email, String firstName, String lastName, Date birthday,
            String role) {
        super();
        this.login = login;
        this.password = password;
        this.repassword = repassword;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.role = role;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    @Override
    public String toString() {
        return "User   login=" + login + ", password=" + password + ", email="
                + email + ", firstName=" + firstName + ", lastName=" + lastName
                + ", birthday=" + birthday + ", role=" + role + "]";
    }

}
