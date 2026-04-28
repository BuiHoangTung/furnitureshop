package com.myproject.furnitureshop.mapper;

import com.myproject.furnitureshop.dto.response.AssignRoleResponse;
import com.myproject.furnitureshop.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssignRoleMapper {
    AssignRoleResponse toAssignRoleResponse(UserEntity userEntity);
}
