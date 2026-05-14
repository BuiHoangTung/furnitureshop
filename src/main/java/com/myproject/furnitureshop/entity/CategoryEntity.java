package com.myproject.furnitureshop.entity;

import com.myproject.furnitureshop.enums.CategoryLevel;
import com.myproject.furnitureshop.enums.CategoryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private CategoryLevel level;

    @Column(name = "banner_image_name")
    private String bannerImageName;

    @Column(name = "thumbnail_image_name")
    private String thumbnailImageName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CategoryStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<CategoryEntity> children = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ProductEntity> products = new ArrayList<>();
}
