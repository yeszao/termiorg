package org.termi.admin.dto;

import org.termi.common.annotation.TableElement;
import lombok.Data;

@Data
public class ProductSpecificationValueDto {
    @TableElement(label = "Id")
    private long id;

    @TableElement(label = "Value")
    private String value;
}