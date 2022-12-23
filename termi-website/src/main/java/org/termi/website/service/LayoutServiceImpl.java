package org.termi.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.termi.common.entity.Layout;
import org.termi.common.repository.LayoutRepository;

import java.util.Set;

@Service
public class LayoutServiceImpl implements LayoutService {
    private final LayoutRepository repository;

    @Autowired
    public LayoutServiceImpl(LayoutRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Set<String> insertEndpoints(Set<String> endpoints) {
        Set<String> existingEndpoints = repository.findAllEndpoints();
        endpoints.removeAll(existingEndpoints);

        if (!endpoints.isEmpty()) {
            repository.saveAll(endpoints.stream().map(Layout::fromEndpoint).toList());
        }

        return endpoints;
    }
}