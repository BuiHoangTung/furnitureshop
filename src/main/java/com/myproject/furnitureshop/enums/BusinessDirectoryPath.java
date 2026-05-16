package com.myproject.furnitureshop.enums;

import lombok.Getter;

@Getter
public enum BusinessDirectoryPath {
    CATEGORY_DIRECTORY_PATH("category"),
    SKU_DIRECTORY_PATH("sku")
    ;

    BusinessDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    private final String directoryPath;
}
