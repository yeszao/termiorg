package org.termi.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.ProductSpecification;
import org.termi.common.repository.ProductSpecificationRepository;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service("AdminProductSpecificationService")
public class ProductSpecificationServiceImpl implements ProductSpecificationService {
    private final ProductSpecificationRepository repository;

    @Autowired
    public ProductSpecificationServiceImpl(ProductSpecificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductSpecificationRepository getRepository() {
        return repository;
    }

    @Override
    public Page<ProductSpecification> getList(Pageable pageable, SearchQuery query) {
        Specification<ProductSpecification> spec = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(query.q())) {
                Predicate predicateForName = cb.like(root.get("name"), "%" + query.q() + "%");
                predicates.add(predicateForName);
            }

            cq.where(predicates.toArray(new Predicate[0]));
            return null;
        };

        return repository.findAll(spec, pageable);
    }
}