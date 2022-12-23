package org.termi.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.termi.common.entity.WidgetInstance;

public interface WidgetInstanceRepository extends JpaRepository<WidgetInstance, Long>, JpaSpecificationExecutor<WidgetInstance> {
}