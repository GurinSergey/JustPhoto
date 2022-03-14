package com.gurin.web.validator;

import com.gurin.core.bo.UserService;
import com.gurin.core.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

/**
 * Created by SGurin on 04.04.2016.
 */
@Component
public class CustomValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isInstance(User.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        String email = user.getEmail();
        String name = user.getUsername();
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "user.email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.password.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "user.confirmPassword.empty");

        if (password.length() < 7) {
            errors.rejectValue("password", "user.password.check");
        }

        if (!password.equals(confirmPassword)) {
            errors.rejectValue("password", "user.password.missMatch");
        }

        if (userService.findUserByEmail(email) != null) {
            errors.rejectValue("password", "user.email.duplicate");
        }
    }
}
