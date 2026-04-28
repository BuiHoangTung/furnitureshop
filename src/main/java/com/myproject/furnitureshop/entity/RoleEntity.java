package com.myproject.furnitureshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "id_role"),
            inverseJoinColumns = @JoinColumn(name = "id_permission")
    )
    private Set<PermissionEntity> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();

    public void addPermission(PermissionEntity permissionEntity) {
        this.permissions.add(permissionEntity);
        permissionEntity.getRoles().add(this);
    }

    public void removePermission(PermissionEntity permissionEntity) {
        this.permissions.remove(permissionEntity);
        permissionEntity.getRoles().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof RoleEntity that)) return false;
        return id != 0 && id == that.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
