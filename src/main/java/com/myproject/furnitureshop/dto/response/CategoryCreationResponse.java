package com.myproject.furnitureshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryCreationResponse {
    private long id;
    private String name;
    private String level;
    private String bannerImageName;
    private String thumbnailImageName;
    private String parentName;
}
