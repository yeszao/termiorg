package org.termi.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.termi.common.entity.Layout;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LayoutRepository extends JpaRepository<Layout, Long>, JpaSpecificationExecutor<Layout> {
    Optional<Layout> findByEndpoint(String endpoint);

    List<Layout> findAllByEndpointIn(Set<String> endpoints);

    @Query("select endpoint from Layout")
    Set<String> findAllEndpoints();
}