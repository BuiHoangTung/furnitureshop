package com.myproject.furnitureshop.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryFileUploadResponse {
    private long id;
    private String name;
    private String level;
    private String banner;
    private String thumbnail;
}
