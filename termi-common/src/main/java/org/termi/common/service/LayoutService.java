package org.termi.common.service;

import org.termi.common.entity.WidgetInstance;
import org.termi.common.function.TriConsumer;
import org.termi.common.widget.WidgetPosition;
import org.termi.common.widget.WidgetRender;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface LayoutService {
    <T> Map<WidgetPosition, T> group(List<WidgetInstance> instances,
                                     Supplier<T> constructor,
                                     TriConsumer<T, WidgetRender, WidgetInstance> consumer);
}