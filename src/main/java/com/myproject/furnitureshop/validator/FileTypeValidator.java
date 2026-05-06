package com.myproject.furnitureshop.validator;

import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class FileTypeValidator implements ConstraintValidator<FileTypeConstraint, MultipartFile> {
    private final FileValidator fileValidator;
    List<String> allowedType;

    public FileTypeValidator(FileValidator fileValidator) {
        this.fileValidator = fileValidator;
    }

    @Override
    public void initialize(FileTypeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.allowedType = constraintAnnotation.allowedTypes().getMimeType();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile == null || multipartFile.isEmpty()) return true;

        try {
            fileValidator.validate(multipartFile, this.allowedType);
//            System.out.println(this.allowedType);
            return true;
        } catch (AppException | IOException e) {

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("INVALID_FILE_TYPE")
                    .addConstraintViolation();

            return false;
        }
    }
}
