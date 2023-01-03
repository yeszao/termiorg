package org.termi.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.termi.common.entity.WidgetInstance;
import org.termi.common.function.TriConsumer;
import org.termi.common.widget.WidgetPosition;
import org.termi.common.widget.WidgetRender;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service("CommonLayoutService")
public class LayoutServiceImpl implements LayoutService {

    @Autowired
    private ApplicationContext context;

    @Override
    public <T> Map<WidgetPosition, T> group(List<WidgetInstance> instances,
                                            Supplier<T> constructor,
                                            TriConsumer<T, WidgetRender, WidgetInstance> consumer) {
        Map<WidgetPosition, List<WidgetInstance>> widgetGroup = instances.stream()
                .sorted(Comparator.comparingInt(WidgetInstance::getSort))
                .collect(Collectors.groupingBy(WidgetInstance::getPosition));

        Map<WidgetPosition, T> grouped = new HashMap<>();
        for (WidgetPosition p : WidgetPosition.values()) {
            grouped.put(p, constructor.get());
        }

        for (Map.Entry<WidgetPosition, List<WidgetInstance>> map : widgetGroup.entrySet()) {
            for (WidgetInstance widgetInstance : widgetGroup.get(map.getKey())) {
                String beanName = widgetInstance.getWidget().getRendererClassName();
                WidgetRender renderer;
                try {
                    renderer = context.getBean(beanName, WidgetRender.class);
                } catch (NoSuchBeanDefinitionException e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                    continue;
                }

                T t = grouped.get(map.getKey());
                consumer.accept(t, renderer, widgetInstance);
            }
        }

        return grouped;
    }

}