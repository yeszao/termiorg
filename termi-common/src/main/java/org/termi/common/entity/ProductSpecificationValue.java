package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.termi.common.annotation.Form;
import org.termi.common.annotation.Input;
import org.termi.common.annotation.form.Editor;
import org.termi.common.annotation.form.Select;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import static org.termi.common.constant.AdminEndpoints.PRODUCT_SPECIFICATION_ALL_API;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Form
public class ProductSpecificationValue extends BaseEntity {
    @NotNull
    @Editor
    @Lob
    private String value;

    @ManyToOne
    private ProductSpecification productSpecification;

    @Transient
    @Input(label = "Specification")
    @Select(url = PRODUCT_SPECIFICATION_ALL_API)
    private Long productSpecificationId;
}