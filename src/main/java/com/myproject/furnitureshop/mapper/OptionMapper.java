package com.myproject.furnitureshop.mapper;

import com.myproject.furnitureshop.dto.request.ShopOptionCreationRequest;
import com.myproject.furnitureshop.dto.response.ShopOptionCreationResponse;
import com.myproject.furnitureshop.dto.response.ShopOptionResponse;
import com.myproject.furnitureshop.dto.response.ShopOptionUpdateResponse;
import com.myproject.furnitureshop.entity.ShopOptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    ShopOptionEntity toShopOptionEntity(ShopOptionCreationRequest optionCreationRequest);
    ShopOptionCreationResponse toOptionCreationResponse(ShopOptionEntity shopOptionEntity);
    ShopOptionUpdateResponse toOptionUpdateResponse(ShopOptionEntity shopOptionEntity);
    ShopOptionResponse toShopOptionResponse(ShopOptionEntity shopOptionEntity);
}
