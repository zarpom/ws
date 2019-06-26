package com.nixsolutions.litvinov.vitaliy.validate.validator;

import com.nixsolutions.litvinov.vitaliy.dao.UserDao;
import com.nixsolutions.litvinov.vitaliy.validate.EmailNotExist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailNotExistValidator
        implements ConstraintValidator<EmailNotExist, String> {
    @Autowired
    UserDao userDao;

    @Override
    public boolean isValid(String email,
            ConstraintValidatorContext constraintValidatorContext) {
        return userDao.findByEmail(email) == null;
    }

    @Override
    public void initialize(EmailNotExist constraintAnnotation) {

    }

}