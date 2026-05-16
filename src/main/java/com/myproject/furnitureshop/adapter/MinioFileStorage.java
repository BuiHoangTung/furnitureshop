package com.myproject.furnitureshop.adapter;

import com.myproject.furnitureshop.config.MinioConfigProperties;
import com.myproject.furnitureshop.dto.response.FileStorageResponse;
import com.myproject.furnitureshop.enums.BusinessDirectoryPath;
import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.utils.FileHelper;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class MinioFileStorage implements Storage {
    private final FileHelper fileHelper;
    private final MinioClient minioClient;
    private final MinioConfigProperties minioConfigProperties;

    public MinioFileStorage(FileHelper fileHelper,
                            MinioClient minioClient,
                            MinioConfigProperties minioConfigProperties) {
        this.fileHelper = fileHelper;
        this.minioClient = minioClient;
        this.minioConfigProperties = minioConfigProperties;
    }

    @Override
    public FileStorageResponse saveFile(MultipartFile file, BusinessDirectoryPath dir) {
        String ext = this.fileHelper.getFileExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + "." + ext;
        String objectKey = dir.getDirectoryPath() + "/" + fileName;

        try(InputStream inputStream = file.getInputStream()) {
             minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(this.minioConfigProperties.bucketName())
                            .object(objectKey)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return FileStorageResponse.builder()
                    .objectKey(objectKey)
                    .url(this.minioConfigProperties.endpoint() + "/" + objectKey)
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .build();
        } catch (Exception e) {
            log.error("Error message: {}", e.getMessage());
            throw new AppException(ErrorCode.FILE_STORAGE_ERROR);
        }
    }

    @Override
    public Resource loadFile(String objectKey) {
        return null;
    }

    @Override
    public boolean deleteFile(String objectKey) {
        return false;
    }
}
