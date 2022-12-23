package org.termi.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.Menu;

public interface MenuService extends BaseService<Menu, Long> {
    Page<Menu> getList(Pageable pageable, SearchQuery query);
}