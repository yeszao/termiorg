package org.termi.common.repository;

import org.termi.common.entity.StaticPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StaticPageRepository extends JpaRepository<StaticPage, Long>, JpaSpecificationExecutor<StaticPage> {
}