package com.myproject.furnitureshop.mapper;

import com.myproject.furnitureshop.dto.response.CategoryNode;
import com.myproject.furnitureshop.entity.CategoryEntity;
import com.myproject.furnitureshop.enums.CategoryLevel;
import com.myproject.furnitureshop.enums.CategoryStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "bannerImageName", target = "banner")
    @Mapping(source = "thumbnailImageName", target = "thumbnail")
    @Mapping(target = "children", ignore = true)
    CategoryNode toCategoryNode(CategoryEntity categoryEntity);

    default String map(CategoryLevel categoryLevel) {
        return categoryLevel.name();
    }

    default String map(CategoryStatus categoryStatus) {
        return categoryStatus.name();
    }
}
