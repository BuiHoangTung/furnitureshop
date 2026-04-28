package com.myproject.furnitureshop.mapper;

import com.myproject.furnitureshop.dto.response.UserProfileResponse;
import com.myproject.furnitureshop.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserProfileResponse toUserProfileResponse(UserEntity userEntity);
}
