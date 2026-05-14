package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.SkuOptionValueEntity;
import com.myproject.furnitureshop.entity.key.SkuOptionValueKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuOptionValueRepository extends JpaRepository<SkuOptionValueEntity, SkuOptionValueKey> {
}
