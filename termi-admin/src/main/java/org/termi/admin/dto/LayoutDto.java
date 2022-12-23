package org.termi.admin.dto;

import lombok.Data;
import org.termi.common.annotation.TableElement;

import java.time.LocalDateTime;

@Data
public class LayoutDto {
    @TableElement(label = "Id")
    private long id;

    @TableElement(label = "Endpoint")
    private String endpoint;

    @TableElement(label = "Create Time")
    private LocalDateTime updateAt;
}