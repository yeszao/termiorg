package org.termi.common.dto;

import org.termi.common.annotation.TableElement;
import org.termi.common.entity.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MenuDto extends TreeBaseDto {
    @TableElement(label = "Name")
    private String name;

    @TableElement(label = "Url")
    private String url;

    @TableElement(label = "Enabled")
    private boolean enabled;

    public static List<MenuDto> fromEntities(List<Menu> categories) {
        return categories.stream()
                .map(c -> {
                    MenuDto dto = new MenuDto();
                    BeanUtils.copyProperties(c, dto);
                    return dto;
                })
                .toList();
    }
}