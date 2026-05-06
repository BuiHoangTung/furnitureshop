package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.service.VirusScanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.capybara.clamav.ClamavClient;
import xyz.capybara.clamav.commands.scan.result.ScanResult;

import java.io.InputStream;

@Slf4j
@Service
public class VirusScanServiceImp implements VirusScanService {
    private final ClamavClient clamavClient;

    public VirusScanServiceImp(ClamavClient clamavClient) {
        this.clamavClient = clamavClient;
    }

    @Override
    public void scan(InputStream inputStream) {
        ScanResult result = this.clamavClient.scan(inputStream);

        if (result instanceof ScanResult.VirusFound) {
            log.warn("Security Alert: Virus detected in uploaded file.");
            throw new AppException(ErrorCode.FILE_VIRUS_DETECTED);
        }
    }
}
