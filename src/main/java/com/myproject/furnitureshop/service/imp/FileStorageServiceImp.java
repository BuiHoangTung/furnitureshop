package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.dto.response.FileStorageResponse;
import com.myproject.furnitureshop.enums.BusinessDirectoryPath;
import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.service.FileStorageService;
import com.myproject.furnitureshop.service.VirusScanService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageServiceImp implements FileStorageService {
    @Value("${app.base-url}")
    private String appBaseUrl;

    @Value("${file.root-path}")
    private String root;

    private final VirusScanService virusScanService;

    public FileStorageServiceImp(VirusScanService virusScanService) {
        this.virusScanService = virusScanService;
    }

    private String getFileExtension(String fileName) {
        if(fileName == null || fileName.isEmpty()) return "";

        return FilenameUtils.getExtension(fileName);
    }

    @Override
    public FileStorageResponse saveFile(MultipartFile file, BusinessDirectoryPath dir) {
//        try {
//            this.virusScanService.scan(file.getInputStream());
//        } catch(IOException e) {
//            throw new AppException(ErrorCode.FILE_READ_ERROR);
//        }

        String directoryPath = dir.getDirectoryPath();
        Path rootBase = Paths.get(root).toAbsolutePath().normalize();
        Path rootPath = rootBase.resolve(directoryPath).normalize();

        if (!rootPath.startsWith(rootBase)) {
            throw new AppException(ErrorCode.INVALID_FILE_PATH);
        }

        try {
            Files.createDirectories(rootPath);

            String ext = getFileExtension(file.getOriginalFilename());
            String newFileName = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);

            Path target = rootPath.resolve(newFileName);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, target);
            }

//            String url = this.appBaseUrl + "/file/" + directoryPath + "/" + newFileName;

            String url = directoryPath + "/" + newFileName;

            return FileStorageResponse.builder()
                    .url(url)
                    .size(file.getSize())
                    .contentType(file.getContentType())
                    .build();
        } catch(IOException e) {
            throw new AppException(ErrorCode.FILE_STORAGE_ERROR);
        }
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path rootPath = Paths.get(this.root);
            Path file = rootPath.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new AppException(ErrorCode.FILE_NOT_FOUND);
            }
        } catch(MalformedURLException e) {
            throw new AppException(ErrorCode.INVALID_URL_FILE);
        }
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            Path rootPath = Paths.get(this.root);
            Path file = rootPath.resolve(fileName);
            return Files.deleteIfExists(file);
        } catch (AccessDeniedException e) {
            throw new AppException(ErrorCode.FILE_ACCESS_DENIED);
        } catch (InvalidPathException e) {
            throw new AppException(ErrorCode.INVALID_FILE_PATH);
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_DELETE_FAILED);
        }
    }
}
