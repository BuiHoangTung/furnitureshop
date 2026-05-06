package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findCategoryEntityById(long id);
    Optional<CategoryEntity> findCategoryEntityByName(String name);
    boolean existsCategoryEntityByName(String name);
}
