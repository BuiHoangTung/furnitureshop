package com.myproject.furnitureshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryInfoUpdateResponse {
    private long id;
    private String name;
    private String level;
    private String bannerImageName;
    private String thumbnailImageName;
    private String parentName;
}
