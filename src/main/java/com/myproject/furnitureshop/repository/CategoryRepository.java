package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.CategoryEntity;
import com.myproject.furnitureshop.enums.CategoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findCategoryEntityById(long id);
    Optional<CategoryEntity> findCategoryEntityByName(String name);
    boolean existsCategoryEntityByName(String name);

    @Query(value = """
                    WITH RECURSIVE cte (id, name, level, banner_image_name, thumbnail_image_name, parent_id, status, created_at, updated_at)
                    AS (
                        SELECT id, name, level, banner_image_name, thumbnail_image_name, parent_id, status, created_at, updated_at
                        FROM categories
                        WHERE id = :id
                
                        UNION ALL

                        SELECT c.id, c.name, c.level, c.banner_image_name, c.thumbnail_image_name, c.parent_id, c.status, c.created_at, c.updated_at
                        FROM categories c
                        INNER JOIN cte ON c.parent_id = cte.id
                    )
                    SELECT id, name, level, banner_image_name, thumbnail_image_name, parent_id, status, created_at, updated_at
                    FROM cte
                    WHERE cte.id <> :id
                    """, nativeQuery = true)
    List<CategoryEntity> findAllChildrenCategoryEntity(@Param(value = "id") long id);

    List<CategoryEntity> findCategoryEntitiesByLevel(CategoryLevel level);
}
