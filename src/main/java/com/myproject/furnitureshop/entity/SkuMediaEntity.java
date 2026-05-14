package com.myproject.furnitureshop.entity;

import com.myproject.furnitureshop.enums.MediaType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "sku_medias")
public class SkuMediaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private MediaType mediaType;

    @Column(name = "media_name")
    private String mediaName;

    @ManyToOne
    @JoinColumn(name = "id_sku")
    private SkuEntity sku;
}
