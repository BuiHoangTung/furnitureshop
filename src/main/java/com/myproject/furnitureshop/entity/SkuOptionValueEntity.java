package com.myproject.furnitureshop.entity;

import com.myproject.furnitureshop.entity.key.SkuOptionValueKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "sku_option_values")
public class SkuOptionValueEntity {
    @EmbeddedId
    private SkuOptionValueKey id;

    @ManyToOne
    @JoinColumn(name = "id_sku", updatable = false, insertable = false)
    private SkuEntity sku;

    @ManyToOne
    @JoinColumn(name = "id_option_value", updatable = false, insertable = false)
    private OptionValueEntity optionValue;
}
