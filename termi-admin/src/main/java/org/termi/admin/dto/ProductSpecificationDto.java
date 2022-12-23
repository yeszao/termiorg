package org.termi.admin.dto;

import lombok.Data;
import org.termi.common.annotation.TableElement;

import static org.termi.common.constant.AdminEndpoints.PRODUCT_SPECIFICATION_VALUE_BASE_URL;

@Data
public class ProductSpecificationDto {
    @TableElement(label = "Id")
    private long id;

    @TableElement(label = "Name", linkUrl = PRODUCT_SPECIFICATION_VALUE_BASE_URL, linkParamKey = "parentId")
    private String name;

    @TableElement(label = "Multiple Select")
    private boolean multipleSelect;
}