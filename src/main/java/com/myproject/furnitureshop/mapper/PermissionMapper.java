package com.myproject.furnitureshop.mapper;

import com.myproject.furnitureshop.dto.request.PermissionCreationRequest;
import com.myproject.furnitureshop.dto.response.PermissionResponse;
import com.myproject.furnitureshop.entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionEntity toPermissionEntity(PermissionCreationRequest permissionCreationRequest);
    PermissionResponse toPermissionResponse(PermissionEntity permissionEntity);
}
