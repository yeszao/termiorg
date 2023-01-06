package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.termi.common.annotation.Form;
import org.termi.common.annotation.Input;
import org.termi.common.annotation.form.Image;
import org.termi.common.annotation.form.Select;
import org.termi.common.constant.PreGroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.termi.common.constant.AdminEndpoints.CATEGORY_ALL_API;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Form
public class Category extends SeoEntity {
    @Input(label = "Parent")
    @Select(url = CATEGORY_ALL_API)
    private long parentId;

    @NotNull
    private String name;

    @Image
    private String image;

    @Input(grid = 4)
    private int sort;

    private int productAmount;

    @Input(group = PreGroup.EXTRA, grid = 4)
    @NotNull
    @Column(columnDefinition="tinyint(1) default 1")
    private boolean enabled;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;
}