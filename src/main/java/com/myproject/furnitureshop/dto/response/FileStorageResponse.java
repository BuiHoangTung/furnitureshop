package com.myproject.furnitureshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileStorageResponse {
    private Long id;
    private String objectKey;
    private String url;
    private long size;
    private String contentType;
}
