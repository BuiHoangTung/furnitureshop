package com.myproject.furnitureshop.service;

import com.myproject.furnitureshop.dto.response.FileStorageResponse;
import com.myproject.furnitureshop.enums.BusinessDirectoryPath;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    FileStorageResponse saveFile(MultipartFile file, BusinessDirectoryPath dir);
    Resource loadFile(String fileName);
    boolean deleteFile(String fileName);
}
