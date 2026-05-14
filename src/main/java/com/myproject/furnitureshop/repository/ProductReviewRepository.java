package com.myproject.furnitureshop.repository;

import com.myproject.furnitureshop.entity.ProductReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReviewEntity, Long> {
}
