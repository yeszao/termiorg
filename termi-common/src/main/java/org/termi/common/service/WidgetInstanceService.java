package org.termi.common.service;

import org.termi.common.entity.WidgetInstance;

import java.util.Collection;
import java.util.List;

public interface WidgetInstanceService {
    List<WidgetInstance> getInstances(Collection<String> layoutEndpoints);
}