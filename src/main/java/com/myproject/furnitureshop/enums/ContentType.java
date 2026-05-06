package com.myproject.furnitureshop.enums;

import com.myproject.furnitureshop.validator.MimeTypeConstants;
import lombok.Getter;

import java.util.List;

@Getter
public enum ContentType {
    IMAGE(MimeTypeConstants.ALLOWED_IMAGE_TYPES)
    ;

    ContentType(List<String> mimeType) {
        this.mimeType = mimeType;
    }

    private final List<String> mimeType;
}
