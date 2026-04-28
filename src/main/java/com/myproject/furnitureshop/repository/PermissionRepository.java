package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    boolean existsPermissionEntitiesByName(String permissionName);
    Optional<PermissionEntity> findPermissionEntityByName(String permissionName);
    Optional<PermissionEntity> findPermissionEntitiesById(long id);
}
