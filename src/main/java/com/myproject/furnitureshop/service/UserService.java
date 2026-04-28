package com.myproject.furnitureshop.service;

import com.myproject.furnitureshop.dto.request.AssignRoleRequest;
import com.myproject.furnitureshop.dto.response.AssignRoleResponse;
import com.myproject.furnitureshop.dto.response.UserProfileResponse;
import com.myproject.furnitureshop.entity.UserEntity;

public interface UserService {
    boolean isUserExistedByEmail(String email);
    UserEntity saveUserEntityToDB(UserEntity userEntity);
    UserEntity findUserByEmail(String email);
    UserEntity findUserById(long userId);
    AssignRoleResponse toggleAssignRole(long id, AssignRoleRequest request);
    UserProfileResponse getUserProfile();
}
