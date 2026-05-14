package com.myproject.furnitureshop.service;

import com.myproject.furnitureshop.dto.request.ShopOptionCreationRequest;
import com.myproject.furnitureshop.dto.request.ShopOptionUpdateRequest;
import com.myproject.furnitureshop.dto.response.ShopOptionCreationResponse;
import com.myproject.furnitureshop.dto.response.ShopOptionDetailResponse;
import com.myproject.furnitureshop.dto.response.ShopOptionResponse;
import com.myproject.furnitureshop.dto.response.ShopOptionUpdateResponse;

import java.util.List;

public interface ShopOptionService {
    ShopOptionCreationResponse createOption(ShopOptionCreationRequest request);
    List<ShopOptionResponse> getShopOptions();
    ShopOptionDetailResponse getShopOptionDetail(long id);
    ShopOptionUpdateResponse updateOption(long id, ShopOptionUpdateRequest request);
    void deleteOption(long id);
}
