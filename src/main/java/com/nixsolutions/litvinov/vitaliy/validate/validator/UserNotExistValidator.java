package com.nixsolutions.litvinov.vitaliy.validate.validator;

import com.nixsolutions.litvinov.vitaliy.dao.UserDao;
import com.nixsolutions.litvinov.vitaliy.validate.UserNotExist;

import javax.validation.ConstraintValidator;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
@Component
public class UserNotExistValidator
        implements ConstraintValidator<UserNotExist, String> {
    @Autowired
    UserDao userDao;

    @Override
    public boolean isValid(String login,
            ConstraintValidatorContext constraintValidatorContext) { 
        return userDao.findByLogin(login) == null;
    }

    @Override
    public void initialize(UserNotExist constraintAnnotation) {

    }

}