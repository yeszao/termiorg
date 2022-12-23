package org.termi.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.termi.common.entity.Layout;
import org.termi.common.entity.WidgetInstance;
import org.termi.common.repository.LayoutRepository;
import org.termi.common.repository.WidgetInstanceRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service("CommonWidgetInstanceService")
public class WidgetInstanceServiceImpl implements WidgetInstanceService {
    @Autowired
    private WidgetInstanceRepository repository;

    @Autowired
    private LayoutRepository layoutRepository;

    @Override
    public List<WidgetInstance> getInstances(Collection<String> layoutEndpoints) {
        List<WidgetInstance> instances = new ArrayList<>();
        if (!layoutEndpoints.isEmpty()) {
            List<Layout> layouts = layoutRepository.findAllByEndpointIn(Set.copyOf(layoutEndpoints));
            layouts.stream()
                    .filter(x -> !x.getWidgetInstances().isEmpty())
                    .forEach(x -> instances.addAll(x.getWidgetInstances()));
        }

        return instances;
    }
}