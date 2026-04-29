package com.myproject.furnitureshop.service.imp;

import com.myproject.furnitureshop.dto.request.AssignRoleRequest;
import com.myproject.furnitureshop.dto.response.AssignRoleResponse;
import com.myproject.furnitureshop.dto.response.UserProfileResponse;
import com.myproject.furnitureshop.entity.RoleEntity;
import com.myproject.furnitureshop.entity.UserEntity;
import com.myproject.furnitureshop.exception.AppException;
import com.myproject.furnitureshop.exception.ErrorCode;
import com.myproject.furnitureshop.mapper.AssignRoleMapper;
import com.myproject.furnitureshop.mapper.UserMapper;
import com.myproject.furnitureshop.repository.UserRepository;
import com.myproject.furnitureshop.service.RoleService;
import com.myproject.furnitureshop.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AssignRoleMapper assignRoleMapper;
    private final UserMapper userMapper;

    public UserServiceImp(UserRepository userRepository,
                          RoleService roleService,
                          AssignRoleMapper assignRoleMapper,
                          UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.assignRoleMapper = assignRoleMapper;
        this.userMapper = userMapper;
    }

    @Override
    public boolean isUserExistedByEmail(String email) {
        return this.userRepository.existsUserByEmail(email);
    }

    @Override
    public UserEntity saveUserEntityToDB(UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return this.userRepository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHENTICATION_FAILED));
    }

    @Override
    public UserEntity findUserById(long userId) {
        return  this.userRepository.findUserEntitiesById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @Override
    @Transactional
    public AssignRoleResponse toggleAssignRole(long id, AssignRoleRequest request) {
        UserEntity userEntity = this.findUserById(id);

        Set<RoleEntity> newRoleEntitySet = request.roles().stream()
                .map(roleService::findRoleByRoleName)
                .collect(Collectors.toSet());

        Set<RoleEntity> currentRoleEntitySet = new HashSet<>(userEntity.getRoles());
        for(RoleEntity r : currentRoleEntitySet) {
            if(!newRoleEntitySet.contains(r)) {
                userEntity.removeRole(r);
            }
        }

        for(RoleEntity r : newRoleEntitySet) {
            if(!userEntity.getRoles().contains(r)) {
                userEntity.addRole(r);
            }
        }

        return this.assignRoleMapper.toAssignRoleResponse(userEntity);
    }

    @Override
    public UserProfileResponse getUserProfile() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        if(authentication == null) {
            throw new AppException(ErrorCode.AUTHENTICATION_REQUIRED);
        }

        String email = authentication.getName();

        UserEntity userEntity = this.findUserByEmail(email);

        return userMapper.toUserProfileResponse(userEntity);
    }
}
