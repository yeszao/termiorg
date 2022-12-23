package org.termi.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.StaticPage;

public interface StaticPageService extends BaseService<StaticPage, Long> {
    Page<StaticPage> getList(Pageable pageable, SearchQuery q);
}