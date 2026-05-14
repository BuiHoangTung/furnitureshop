package com.myproject.furnitureshop.entity;

import com.myproject.furnitureshop.enums.MediaType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "product_review_medias")
public class ProductReviewMediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false)
    private MediaType mediaType;

    @Column(name = "media_name", nullable = false)
    private String mediaName;

    @ManyToOne
    @JoinColumn(name = "id_product_review", nullable = false)
    private ProductReviewEntity productReview;
}
