package org.termi.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.termi.admin.query.ChildQuery;
import org.termi.common.entity.ProductSpecificationValue;
import org.termi.common.repository.ProductSpecificationValueRepository;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service("ProductSpecificationValueService")
public class ProductSpecificationValueServiceImpl implements ProductSpecificationValueService {
    private final ProductSpecificationValueRepository repository;

    @Autowired
    public ProductSpecificationValueServiceImpl(ProductSpecificationValueRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductSpecificationValueRepository getRepository() {
        return repository;
    }

    @Override
    public Page<ProductSpecificationValue> getList(Pageable pageable, ChildQuery query) {
        Specification<ProductSpecificationValue> spec = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!ObjectUtils.isEmpty(query.parentId())) {
                Predicate predicateForParentId = cb.equal(root.get("productSpecification"), query.parentId());
                predicates.add(predicateForParentId);
            }

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