package org.termi.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.termi.common.entity.Widget;

public interface WidgetRepository extends JpaRepository<Widget, Long>, JpaSpecificationExecutor<Widget> {
}