package com.myproject.furnitureshop.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryNode {
    private long id;
    private String name;
    private String level;
    private String banner;
    private String thumbnail;
    private String status;
    private List<CategoryNode> children = new ArrayList<>();
}
