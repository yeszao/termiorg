package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.termi.common.annotation.Form;
import org.termi.common.annotation.Input;
import org.termi.common.annotation.form.TextArea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Form
public class Setting extends BaseEntity {
    @NotBlank
    @Column(unique = true)
    @Pattern(regexp = "\\w+")
    @Input(order = 0)
    private String name;

    @Input(order = 1)
    @TextArea
    private String value;

    @Input(order = 5)
    private String description;

    @Input(order = 21)
    private boolean deletable;
}