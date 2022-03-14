package com.gurin.web.validator;

import com.gurin.web.customModel.FileUploadForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by SGurin on 12.04.2016.
 */
@Component
public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isInstance(FileUploadForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        FileUploadForm file = (FileUploadForm) o;
        if (file.getFile().getSize() == 0) {
            errors.rejectValue("file", "file.notChose");
        }

        if (file.getFile().getSize() >= 500000) {
            errors.rejectValue("file", "file.exceedsLimit");
        }
    }
}
