package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.OptionValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionValueRepository extends JpaRepository<OptionValueEntity, Long> {
}
