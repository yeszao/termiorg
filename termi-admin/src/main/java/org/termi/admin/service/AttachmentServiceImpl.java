package org.termi.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.Attachment;
import org.termi.common.repository.AttachmentRepository;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("AdminAttachmentService")
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository repository;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public AttachmentRepository getRepository() {
        return repository;
    }

    @Override
    public List<Attachment> findAllByIdIn(Collection<Long> ids) {
        return repository.findAllByIdIn(ids);
    }

    @Override
    public Page<Attachment> getList(Pageable pageable, SearchQuery query) {
        Specification<Attachment> spec = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(query.q())) {
                predicates.add(cb.like(root.get("name"), '%' + query.q() + '%'));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            return null;
        };

        return repository.findAll(spec, pageable);
    }
}