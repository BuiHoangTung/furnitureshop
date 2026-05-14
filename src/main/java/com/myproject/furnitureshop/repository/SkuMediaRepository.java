package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.SkuMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuMediaRepository extends JpaRepository<SkuMediaEntity, Long> {
}
