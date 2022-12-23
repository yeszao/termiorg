package org.termi.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.Attachment;

import java.util.Collection;
import java.util.List;

public interface AttachmentService extends BaseService<Attachment, Long> {
    List<Attachment> findAllByIdIn(Collection<Long> ids);
    Page<Attachment> getList(Pageable pageable, SearchQuery query);
}