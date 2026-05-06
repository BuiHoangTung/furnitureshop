package com.myproject.furnitureshop.validator;

import org.springframework.http.MediaType;

import java.util.List;

public class MimeTypeConstants {
    private MimeTypeConstants() {}

    /* IMAGE */
    public static final String IMAGE_JPEG = MediaType.IMAGE_JPEG_VALUE;
    public static final String IMAGE_PNG = MediaType.IMAGE_PNG_VALUE;

    public static final List<String> ALLOWED_IMAGE_TYPES = List.of(
            IMAGE_JPEG,
            IMAGE_PNG
    );
}
