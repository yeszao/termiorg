package org.termi.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.termi.common.repository.WidgetInstanceRepository;

@Service("AdminWidgetInstanceService")
public class WidgetInstanceServiceImpl implements WidgetInstanceService {
    private final WidgetInstanceRepository repository;

    @Autowired
    public WidgetInstanceServiceImpl(WidgetInstanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public WidgetInstanceRepository getRepository() {
        return repository;
    }
}