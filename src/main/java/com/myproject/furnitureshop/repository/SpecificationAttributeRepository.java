package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.SpecificationAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationAttributeRepository extends JpaRepository<SpecificationAttributeEntity, Long> {
}
