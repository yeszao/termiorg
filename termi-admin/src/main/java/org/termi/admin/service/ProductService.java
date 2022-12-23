package org.termi.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.Product;

public interface ProductService extends BaseService<Product, Long> {
    Page<Product> getList(Pageable pageable, SearchQuery query);
}