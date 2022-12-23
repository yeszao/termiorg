package org.termi.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.termi.common.entity.Product;
import org.termi.common.query.ProductQuery;

public interface ProductService {
    Page<Product> getList(Pageable pageable, ProductQuery query);
}