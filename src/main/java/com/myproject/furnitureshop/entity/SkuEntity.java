package com.myproject.furnitureshop.entity;

import com.myproject.furnitureshop.enums.SkuStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity(name = "skus")
public class SkuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "discount_price", precision = 15, scale = 2)
    private BigDecimal discountPrice;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "detail_description", nullable = false)
    private String detailDescription;

    @Column(name = "quantity", nullable = false)
    private int quantity = 0;

    @Column(name = "weight", nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(name = "width", nullable = false, precision = 10, scale = 2)
    private BigDecimal width;

    @Column(name = "height", nullable = false, precision = 10, scale = 2)
    private BigDecimal height;

    @Column(name = "depth", nullable = false, precision = 10, scale = 2)
    private BigDecimal depth;

    @Column(name = "dimension_image_name")
    private String dimensionImageName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SkuStatus status;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private ProductEntity product;
}
