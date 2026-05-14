package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.dto.request.ShopOptionCreationRequest;
import com.myproject.furnitureshop.dto.request.ShopOptionUpdateRequest;
import com.myproject.furnitureshop.dto.response.ShopOptionCreationResponse;
import com.myproject.furnitureshop.dto.response.ShopOptionDetailResponse;
import com.myproject.furnitureshop.dto.response.ShopOptionResponse;
import com.myproject.furnitureshop.dto.response.ShopOptionUpdateResponse;
import com.myproject.furnitureshop.entity.ShopOptionEntity;
import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.mapper.OptionMapper;
import com.myproject.furnitureshop.repository.ShopOptionRepository;
import com.myproject.furnitureshop.service.ShopOptionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopOptionServiceImp implements ShopOptionService {
    private final ShopOptionRepository shopOptionRepository;
    private final OptionMapper optionMapper;

    public ShopOptionServiceImp(ShopOptionRepository shopOptionRepository,
                                OptionMapper optionMapper) {
        this.shopOptionRepository = shopOptionRepository;
        this.optionMapper = optionMapper;
    }

    @PreAuthorize("hasAuthority('OPTION_CREATE')")
    @Override
    public ShopOptionCreationResponse createOption(ShopOptionCreationRequest request) {
        if(this.shopOptionRepository.existsShopOptionEntityByName(request.name())) {
            throw new AppException(ErrorCode.OPT_ALREADY_EXISTS);
        }

        ShopOptionEntity shopOptionEntity =
                this.shopOptionRepository.save(this.optionMapper.toShopOptionEntity(request));

        return this.optionMapper.toOptionCreationResponse(shopOptionEntity);
    }

    @PreAuthorize("hasAuthority('OPTION_READ')")
    @Override
    public List<ShopOptionResponse> getShopOptions() {
        return this.shopOptionRepository.findAll()
                .stream().map(this.optionMapper::toShopOptionResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('OPTION_READ')")
    @Override
    public ShopOptionDetailResponse getShopOptionDetail(long id) {
        ShopOptionEntity optionEntity = this.shopOptionRepository.findShopOptionEntityById(id)
                .orElseThrow(() -> new AppException(ErrorCode.OPT_NOT_FOUND));

        return ShopOptionDetailResponse.builder()
                .id(optionEntity.getId())
                .name(optionEntity.getName())
                .createdAt(optionEntity.getCreatedAt())
                .updatedAt(optionEntity.getUpdatedAt())
                .isActive(optionEntity.getDeletedAt() == null)
                .build();
    }

    @PreAuthorize("hasAuthority('OPTION_UPDATE')")
    @Override
    public ShopOptionUpdateResponse updateOption(long id, ShopOptionUpdateRequest request) {
        ShopOptionEntity shopOptionEntity = this.shopOptionRepository.findShopOptionEntityById(id)
                .orElseThrow(() -> new AppException(ErrorCode.OPT_NOT_FOUND));

        shopOptionEntity.setName(request.name());
        this.shopOptionRepository.save(shopOptionEntity);

        return this.optionMapper.toOptionUpdateResponse(shopOptionEntity);
    }

    @Override
    public void deleteOption(long id) { // -> Relate to product, skus, ...
    }
}
