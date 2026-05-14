package com.myproject.furnitureshop.mapper;

import com.myproject.furnitureshop.dto.request.ProductCreationRequest;
import com.myproject.furnitureshop.dto.response.ProductCreationResponse;
import com.myproject.furnitureshop.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toProductEntity(ProductCreationRequest productCreationRequest);
    ProductCreationResponse toProductCreationResponse(ProductEntity productEntity);
}
