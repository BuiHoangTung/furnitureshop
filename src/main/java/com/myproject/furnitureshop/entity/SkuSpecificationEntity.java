package com.myproject.furnitureshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "sku_specifications")
public class SkuSpecificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "id_sku")
    private SkuEntity sku;

    @ManyToOne
    @JoinColumn(name = "id_specification_attributes")
    private SpecificationAttributeEntity specificationAttribute;
}
