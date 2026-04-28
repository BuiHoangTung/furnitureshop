package com.myproject.furnitureshop.mapper;

import com.myproject.furnitureshop.dto.request.RoleCreationRequest;
import com.myproject.furnitureshop.dto.response.RoleResponse;
import com.myproject.furnitureshop.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    RoleEntity toRoleEntity(RoleCreationRequest roleCreationRequest);
    RoleResponse toRoleResponse(RoleEntity roleEntity);
}
