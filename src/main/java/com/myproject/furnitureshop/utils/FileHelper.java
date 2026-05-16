package com.myproject.furnitureshop.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

@Component
public class FileHelper {
    public String getFileExtension(String fileName) {
        if(fileName == null || fileName.isEmpty()) return "";

        return FilenameUtils.getExtension(fileName);
    }
}
