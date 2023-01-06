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

    private boolean topEnabled = true;
    private boolean leftEnabled = true;
    private boolean centerEnabled = true;
    private boolean rightEnabled = true;
    private boolean bottomEnabled = true;

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
