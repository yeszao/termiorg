package org.termi.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.Setting;
import org.termi.common.repository.SettingRepository;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service("AdminSettingService")
public class SettingServiceImpl implements SettingService {
    private final SettingRepository repository;

    @Autowired
    public SettingServiceImpl(SettingRepository repository) {
        this.repository = repository;
    }

    @Override
    public SettingRepository getRepository() {
        return repository;
    }

    @Override
    public Page<Setting> getList(Pageable pageable, SearchQuery query) {
        Specification<Setting> spec = (root, cq, cb) -> {
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