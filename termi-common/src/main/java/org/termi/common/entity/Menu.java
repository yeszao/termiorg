package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.termi.common.annotation.Form;
import org.termi.common.annotation.Input;
import org.termi.common.annotation.form.Select;
import org.termi.common.constant.PreGroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.termi.common.constant.AdminEndpoints.ICON_ALL_API;
import static org.termi.common.constant.AdminEndpoints.MENU_ALL_API;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Form
public class Menu extends BaseEntity {
    @Input(label = "Parent", order = 0)
    @Select(url = MENU_ALL_API)
    private long parentId;

    @NotBlank
    @Input(grid = 5, order = 1)
    private String name;

    @Select(url = ICON_ALL_API, searchable = true)
    private String icon;

    private String url;

    @Input(label = "Other Attributes (e.g., <code>target=\"_blank\"</code>)", grid = 8)
    private String otherAttributes;

    @Input(grid = 2)
    private int sort;

    @Input(group = PreGroup.EXTRA, grid = 4)
    @NotNull
    @Column(columnDefinition="tinyint(1) default 1")
    
    private boolean enabled;
}