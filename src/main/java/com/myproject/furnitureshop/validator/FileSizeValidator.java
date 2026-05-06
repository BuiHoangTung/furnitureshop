package com.myproject.furnitureshop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSizeConstraint, MultipartFile> {
    private long maxSize;

    @Override
    public void initialize(FileSizeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.maxSize = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile == null || multipartFile.isEmpty()) return true;

        if(maxSize == 0) return false;

        return multipartFile.getSize() <= this.maxSize;
    }
}
