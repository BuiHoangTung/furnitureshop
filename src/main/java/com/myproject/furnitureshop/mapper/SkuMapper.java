package com.myproject.furnitureshop.mapper;

import com.myproject.furnitureshop.dto.request.SkuCreationRequest;
import com.myproject.furnitureshop.dto.response.SkuCreationResponse;
import com.myproject.furnitureshop.entity.SkuEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkuMapper {
    SkuEntity toSkuEntity(SkuCreationRequest skuCreationRequest);
    SkuCreationResponse toSkuCreationResponse(SkuEntity skuEntity);
}
