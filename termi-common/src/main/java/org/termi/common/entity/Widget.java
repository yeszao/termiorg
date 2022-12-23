package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString
public class Widget extends BaseEntity {
    private String name;
    private String description;
    private String previewImageUrl;
    private String rendererClassName;
    private boolean enabled;

    @OneToMany(mappedBy = "widget")
    private List<WidgetInstance> widgetInstances;
}
