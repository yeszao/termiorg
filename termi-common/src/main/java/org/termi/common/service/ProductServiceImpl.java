package org.termi.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.termi.common.entity.Product;
import org.termi.common.query.ProductQuery;
import org.termi.common.repository.ProductRepository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service("CommonProductService")
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Product> getList(Pageable pageable, ProductQuery query) {
        Specification<Product> spec = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!query.categoryId().equals(0L)) {
                predicates.add(cb.equal(root.get("category"), query.categoryId()));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            return null;
        };

        return repository.findAll(spec, pageable);
    }
}