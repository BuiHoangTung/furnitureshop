package com.myproject.furnitureshop.dto.request;

import com.myproject.furnitureshop.enums.ContentType;
import com.myproject.furnitureshop.validator.FileNotNullConstraint;
import com.myproject.furnitureshop.validator.FileSizeConstraint;
import com.myproject.furnitureshop.validator.FileTypeConstraint;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record CategoryFileUploadRequest(
        @NotNull(message = "CAT_IDENTIFIER_REQUIRED")
        Long categoryId,

        @FileNotNullConstraint
        @FileSizeConstraint(max = 50 * 1024 * 1024)
        @FileTypeConstraint(allowedTypes = ContentType.IMAGE)
        MultipartFile banner,

        @FileNotNullConstraint
        @FileSizeConstraint(max = 50 * 1024 * 1024)
        @FileTypeConstraint(allowedTypes = ContentType.IMAGE)
        MultipartFile thumbnail
) {
}
