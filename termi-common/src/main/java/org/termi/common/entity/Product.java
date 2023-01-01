package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.termi.common.annotation.Form;
import org.termi.common.annotation.Input;
import org.termi.common.annotation.form.Editor;
import org.termi.common.annotation.form.Image;
import org.termi.common.annotation.form.NotForm;
import org.termi.common.annotation.form.Select;
import org.termi.common.constant.PreGroup;
import org.termi.common.enumeration.ProductStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.termi.common.constant.AdminEndpoints.CATEGORY_ALL_API;
import static org.termi.common.constant.AdminEndpoints.PRODUCT_STATUS_API;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Form
public class Product extends SeoEntity {
    @NotBlank
    @Size(min = 5, max = 200)
    private String name;

    @ManyToOne
    private Category category;

    @Transient
    @Input(label = "Category", required = true, order = 0)
    @Select(url = CATEGORY_ALL_API, searchable = true)
    private Long categoryId;

    @Column(unique = true)
    @Input(order = 15)
    private String code;

    @Input(label = "Pictures", order = 17)
    @Image(maxFiles = 7)
    @Lob
    private String pictures;

    @Lob
    @Input(order = 19)
    @Editor
    private String content;

    @OneToMany(mappedBy = "product")
    private List<ProductSku> productSkus;

    @Input(group = PreGroup.EXTRA)
    @Select(url = PRODUCT_STATUS_API, searchable = false)
    private ProductStatus status;

    @Transient
    @NotForm
    private String firstPicture;

    public String getFirstPicture() {
        String[] pictures = Optional.ofNullable(this.getPictures())
                .map(y -> this.getPictures().split(","))
                .orElse(new String[]{});

        if (pictures.length > 0) {
            return pictures[0];
        }

        return "";
    }

    @PostLoad
    public void onCategoryId() {
        this.categoryId = Objects.isNull(this.category) ? 0L : this.category.getId();
    }
}