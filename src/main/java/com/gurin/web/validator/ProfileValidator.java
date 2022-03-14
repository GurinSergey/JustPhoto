package com.gurin.web.validator;

import com.gurin.web.customModel.ProfileForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by SGurin on 03.06.2016.
 */
@Component
public class ProfileValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isInstance(ProfileForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProfileForm profile = (ProfileForm) o;

        if (profile.getName().equals("")) {
            errors.rejectValue("name", "profile.name.empty");
        }

        if (profile.getEmail().equals("")) {
            errors.rejectValue("email", "profile.email.empty");
        }
    }
}
