package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.termi.common.widget.WidgetPosition;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class WidgetInstance extends BaseEntity {
    @ManyToOne
    private Layout layout;

    @Transient
    private long layoutId;

    @ManyToOne
    private Widget widget;

    @Transient
    private long widgetId;

    private int sort;

    @Lob
    private String configuration;

    @Enumerated(EnumType.STRING)
    private WidgetPosition position;

    public long getLayoutId() {
        return this.layout.getId();
    }

    public long getWidgetId() {
        return this.widget.getId();
    }
}
