package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.ShopOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopOptionRepository extends JpaRepository<ShopOptionEntity, Long> {
    boolean existsShopOptionEntityByName(String name);
    Optional<ShopOptionEntity> findShopOptionEntityById(long id);
}
