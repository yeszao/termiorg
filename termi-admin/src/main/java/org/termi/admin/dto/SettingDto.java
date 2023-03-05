package org.termi.admin.dto;

import lombok.Data;
import org.termi.common.annotation.TableElement;

@Data
public class SettingDto {
    @TableElement(label = "Id", grid = 1)
    private long id;

    @TableElement(label = "Name", grid = 4)
    private String name;

    @TableElement(label = "Value", grid = 4)
    private String value;
}