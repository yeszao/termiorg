package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.termi.common.annotation.Form;
import org.termi.common.annotation.Input;
import org.termi.common.constant.PreGroup;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Form
public class ProductSpecification extends BaseEntity {
    @NotBlank
    private String name;

    private int sort;

    @OneToMany(mappedBy = "productSpecification")
    private List<ProductSpecificationValue> specificationValues;

    @Input(group = PreGroup.EXTRA, grid = 4)
    private boolean multipleSelect;
}