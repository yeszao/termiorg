package org.termi.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.ProductSpecification;

public interface ProductSpecificationService extends BaseService<ProductSpecification, Long> {
    Page<ProductSpecification> getList(Pageable pageable, SearchQuery q);
}