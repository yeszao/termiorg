package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Layout extends BaseEntity {
    @Column(unique = true)
    private String endpoint;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean topEnabled;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean leftEnabled;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean centerEnabled;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean rightEnabled;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean bottomEnabled;

    @OneToMany(mappedBy = "layout", fetch = FetchType.LAZY)
    private List<WidgetInstance> widgetInstances;

    public static Layout fromEndpoint(String endpoint) {
        Layout layout = new Layout();
        layout.setEndpoint(endpoint);
        layout.setAddBy(0);
        layout.setEditBy(0);

        return layout;
    }
}
