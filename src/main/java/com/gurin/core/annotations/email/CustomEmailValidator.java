package com.gurin.core.annotations.email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SGurin on 04.04.2016.
 */
public class CustomEmailValidator implements ConstraintValidator<CustomEmail, String> {
    @Override
    public void initialize(CustomEmail customAnnotation) {
    }

    @Override
    public boolean isValid(String emailValue, ConstraintValidatorContext constraintValidatorContext) {
        String regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
                + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
                + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(emailValue);

        return m.matches();
    }
}
