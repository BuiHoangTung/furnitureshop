package com.myproject.furnitureshop.enums;

import lombok.Getter;

@Getter
public enum BusinessDirectoryPath {
    CATEGORY_DIRECTORY_PATH("category")
    ;

    BusinessDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    private final String directoryPath;
}
