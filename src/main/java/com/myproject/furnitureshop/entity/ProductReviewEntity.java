package com.myproject.furnitureshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity(name = "product_reviews")
public class ProductReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "rate", precision = 2, scale = 1, nullable = false)
    private BigDecimal rate;

    @Column(name = "comment")
    private String comment;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;
}
