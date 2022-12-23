package org.termi.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.Layout;
import org.termi.common.repository.LayoutRepository;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service("AdminLayoutService")
public class LayoutServiceImpl implements LayoutService {
    private final LayoutRepository repository;

    @Autowired
    public LayoutServiceImpl(LayoutRepository repository) {
        this.repository = repository;
    }

    @Override
    public LayoutRepository getRepository() {
        return repository;
    }

    @Override
    public Page<Layout> getList(Pageable pageable, SearchQuery query) {
        Specification<Layout> spec = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(query.q())) {
                predicates.add(cb.like(root.get("endpoint"), '%' + query.q() + '%'));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            return null;
        };

        return repository.findAll(spec, pageable);
    }
}