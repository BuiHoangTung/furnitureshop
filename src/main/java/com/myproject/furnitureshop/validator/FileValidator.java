package com.myproject.furnitureshop.validator;

import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class FileValidator {
    private final Tika tika = new Tika();

    public void validate(MultipartFile multipartFile, List<String> allowedType) throws IOException {
        String detected = tika.detect(multipartFile.getInputStream());

        if(!allowedType.contains(detected)) {
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        }
    }
}
