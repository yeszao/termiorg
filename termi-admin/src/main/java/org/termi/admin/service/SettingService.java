package org.termi.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.Setting;

public interface SettingService extends BaseService<Setting, Long> {
    Page<Setting> getList(Pageable pageable, SearchQuery query);
}