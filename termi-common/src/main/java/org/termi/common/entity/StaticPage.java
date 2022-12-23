package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.termi.common.annotation.Form;
import org.termi.common.annotation.Input;
import org.termi.common.annotation.form.Editor;
import org.termi.common.annotation.form.Select;
import org.termi.common.constant.AdminEndpoints;
import org.termi.common.constant.PreGroup;
import org.termi.common.enumeration.PageStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Form
public class StaticPage extends SeoEntity {
    @NotBlank
    private String name;

    @Lob
    @Input(order = 20)
    @Editor
    private String content;

    @Input(group = PreGroup.EXTRA, grid = 6)
    @Select(url = AdminEndpoints.PAGE_STATUS_API)
    @Enumerated(EnumType.STRING)
    private PageStatus status;
}