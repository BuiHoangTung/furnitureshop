package com.myproject.furnitureshop;

import com.myproject.furnitureshop.config.MinioConfigProperties;
import com.myproject.furnitureshop.config.RbacCacheConfigProperties;
import com.myproject.furnitureshop.notification.config.NotificationMQConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties(
        {
                NotificationMQConfigProperties.class,
                RbacCacheConfigProperties.class,
                MinioConfigProperties.class
        }
)
public class FurnitureshopApplication {
	public static void main(String[] args) {
		SpringApplication.run(FurnitureshopApplication.class, args);
	}
}
