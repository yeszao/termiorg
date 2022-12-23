package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.termi.common.widget.WidgetPosition;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class WidgetInstance extends BaseEntity {
    @ManyToOne
    private Layout layout;

    @ManyToOne
    private Widget widget;

    private int sort;

    @Lob
    private String configuration;

    @Enumerated(EnumType.STRING)
    private WidgetPosition position;
}
