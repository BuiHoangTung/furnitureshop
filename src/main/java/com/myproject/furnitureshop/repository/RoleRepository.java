package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findRoleEntityByName(String name);
    Optional<RoleEntity> findRoleEntityById(long id);
    boolean existsRoleEntitiesByName(String name);
    int deleteRoleEntitiesById(long id);

    @Query("""
            SELECT DISTINCT p.name
            FROM roles r
            JOIN r.permissions p
            WHERE r.name = :roleName
           """)
    Set<String> findPermissionNamesByRoleName(@Param("roleName") String name);
}
