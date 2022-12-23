package org.termi.common.dto;

import org.termi.common.annotation.TableElement;
import org.termi.common.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDto extends TreeBaseDto {
    @TableElement(label="Products")
    private int productAmount;

    @TableElement(label="Slug")
    private String slug;

    @TableElement(label = "Enabled")
    private boolean enabled;

    public static List<CategoryDto> fromEntities(List<Category> categories) {
        return categories.stream()
                .map(c -> {
                    CategoryDto dto = new CategoryDto();
                    BeanUtils.copyProperties(c, dto);
                    return dto;
                })
                .toList();
    }
}