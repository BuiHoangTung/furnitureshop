package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.SkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuRepository extends JpaRepository<SkuEntity, Long> {
}
