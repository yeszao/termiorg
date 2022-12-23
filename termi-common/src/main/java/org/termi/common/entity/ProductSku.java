package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSku extends BaseEntity {
    private String skuCode;

    @ManyToOne
    private Product product;

    private BigDecimal marketPrice;
    private BigDecimal sellingPrice;
    private int remaining;
}