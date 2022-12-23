package org.termi.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.Category;

public interface CategoryService extends BaseService<Category, Long> {
    Page<Category> getList(Pageable pageable, SearchQuery query);
}