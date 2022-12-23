package org.termi.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.termi.common.annotation.Form;
import org.termi.common.annotation.form.Disabled;
import org.termi.common.annotation.form.Image;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Form
public class Attachment extends BaseEntity {
    private String name;

    @Image
    private String uri;

    @Disabled
    private Integer width;

    @Disabled
    private Integer height;

    @Disabled
    private Long size;

    @Disabled
    private String mime;

    @Disabled
    private String extension;
}