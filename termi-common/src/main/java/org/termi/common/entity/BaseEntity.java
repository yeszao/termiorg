package org.termi.common.entity;

import lombok.Data;
import org.termi.common.annotation.form.Hidden;
import org.termi.common.annotation.form.NotForm;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Hidden
    private long id;

    @NotNull
    @NotForm
    private long addBy;

    @NotNull
    @NotForm
    private long editBy;

    @NotForm
    private LocalDateTime updateAt;

    @NotForm
    private LocalDateTime createAt;

    @PrePersist
    protected void onCreate() {
        updateAt = createAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        createAt = LocalDateTime.now();
    }
}