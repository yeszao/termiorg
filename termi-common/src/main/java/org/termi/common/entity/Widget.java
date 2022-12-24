package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Widget extends BaseEntity {
    private String name;
    private String description;
    private String previewImageUrl;
    private String rendererClassName;
    private boolean enabled;

    @OneToMany(mappedBy = "widget", fetch = FetchType.LAZY)
    private List<WidgetInstance> widgetInstances;
}
