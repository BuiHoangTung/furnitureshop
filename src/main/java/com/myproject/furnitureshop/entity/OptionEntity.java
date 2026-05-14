package com.myproject.furnitureshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "options")
public class OptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_shop_option")
    private ShopOptionEntity shopOption;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private ProductEntity product;

    @OneToMany(mappedBy = "option")
    private List<OptionValueEntity> optionValues = new ArrayList<>();
}
