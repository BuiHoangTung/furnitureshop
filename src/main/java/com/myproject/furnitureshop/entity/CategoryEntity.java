package com.myproject.furnitureshop.entity;

import com.myproject.furnitureshop.enums.CategoryLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private CategoryLevel level;

    @Column(name = "banner_image_name")
    private String bannerImageName;

    @Column(name = "thumbnail_image_name")
    private String thumbnailImageName;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<CategoryEntity> children;
}
