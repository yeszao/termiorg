package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.termi.common.annotation.Input;
import org.termi.common.annotation.form.TextArea;
import org.termi.common.constant.PreGroup;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class SeoEntity extends BaseEntity {
    @NotNull
    @Input(order = 11)
    @Column(unique = true)
    private String slug;

    @NotNull
    @Input(group = PreGroup.SEO)
    private String seoTitle;

    @NotNull
    @Input(group = PreGroup.SEO)
    private String seoKeywords;

    @NotNull
    @Input(group = PreGroup.SEO)
    @TextArea
    private String seoDescription;
}