package org.termi.admin.dto;

import lombok.Data;
import org.termi.common.annotation.TableElement;
import org.termi.common.annotation.form.Image;

@Data
public class ProductDto {
    @TableElement(label = "Id", grid = 1)
    private long id;

    private String pictures;

    @TableElement(label = "Picture")
    @Image
    private String firstPicture;

    @TableElement(label = "Name", grid = 4)
    private String name;
}