package org.termi.admin.dto;

import lombok.Data;
import org.termi.common.annotation.TableElement;
import org.termi.common.annotation.form.Image;

@Data
public class AttachmentDto {
    @TableElement(label = "Id", grid = 1)
    private long id;

    @TableElement(label = "Preview")
    @Image
    private String uri;

    @TableElement(label = "Name", grid = 5)
    private String name;
}