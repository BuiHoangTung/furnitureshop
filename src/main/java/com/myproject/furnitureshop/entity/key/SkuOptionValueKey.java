package com.myproject.furnitureshop.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SkuOptionValueKey implements Serializable {
    @Column(name = "id_sku")
    private long idSku;

    @Column(name = "id_option_value")
    private long idOptionValue;
}
