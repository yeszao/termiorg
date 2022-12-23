package org.termi.admin.dto;

import org.termi.common.annotation.TableElement;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StaticPageDto {
    @TableElement(label = "Id")
    private long id;

    @TableElement(label = "Name")
    private String name;

    @TableElement(label = "Create Time")
    private LocalDateTime updateAt;
}