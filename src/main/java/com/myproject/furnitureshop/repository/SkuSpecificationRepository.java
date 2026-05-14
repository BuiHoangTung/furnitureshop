package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.SkuSpecificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuSpecificationRepository extends JpaRepository<SkuSpecificationEntity, Long> {
}
