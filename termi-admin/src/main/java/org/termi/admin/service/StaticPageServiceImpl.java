package org.termi.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.StaticPage;
import org.termi.common.repository.StaticPageRepository;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service("AdminStaticPageService")
public class StaticPageServiceImpl implements StaticPageService {
    private final StaticPageRepository repository;

    @Autowired
    public StaticPageServiceImpl(StaticPageRepository repository) {
        this.repository = repository;
    }

    @Override
    public StaticPageRepository getRepository() {
        return repository;
    }

    @Override
    public Page<StaticPage> getList(Pageable pageable, SearchQuery query) {
        Specification<StaticPage> spec = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(query.q())) {
                Predicate predicateForName = cb.like(root.get("name"), "%" + query.q() + "%");
                Predicate predicateForContent = cb.like(root.get("content"), "%" + query.q() + "%");
                predicates.add(cb.or(predicateForName, predicateForContent));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            return null;
        };

        return repository.findAll(spec, pageable);
    }
}