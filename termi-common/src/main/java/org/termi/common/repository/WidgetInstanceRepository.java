package org.termi.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.termi.common.entity.Layout;
import org.termi.common.entity.WidgetInstance;

import java.util.Collection;
import java.util.List;

public interface WidgetInstanceRepository extends JpaRepository<WidgetInstance, Long>, JpaSpecificationExecutor<WidgetInstance> {
    List<WidgetInstance> findAllByLayoutIn(Collection<Layout> layout);
}