package org.termi.common.repository;

import org.termi.common.entity.ProductSpecificationValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductSpecificationValueRepository extends JpaRepository<ProductSpecificationValue, Long>, JpaSpecificationExecutor<ProductSpecificationValue> {
}