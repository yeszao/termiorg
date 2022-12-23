package org.termi.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.Layout;

public interface LayoutService extends BaseService<Layout, Long> {
    Page<Layout> getList(Pageable pageable, SearchQuery q);
}