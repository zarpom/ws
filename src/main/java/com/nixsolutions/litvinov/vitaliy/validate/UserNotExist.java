package com.nixsolutions.litvinov.vitaliy.validate;

import com.nixsolutions.litvinov.vitaliy.validate.validator.UserNotExistValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNotExistValidator.class)
public @interface UserNotExist {
    String message() default "{user exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}