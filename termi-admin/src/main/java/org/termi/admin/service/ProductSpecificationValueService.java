package org.termi.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.termi.admin.query.ChildQuery;
import org.termi.common.entity.ProductSpecificationValue;

public interface ProductSpecificationValueService extends BaseService<ProductSpecificationValue, Long> {
    Page<ProductSpecificationValue> getList(Pageable pageable, ChildQuery q);
}