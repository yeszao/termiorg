package org.termi.website.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.termi.website.service.LayoutService;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private LayoutService layoutService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<RequestMappingInfo, HandlerMethod> map = applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();

        // Get all get mapping patterns, and insert to layouts
        Set<String> patterns = new HashSet<>();
        map.forEach((k, v) -> {
            if (k.getMethodsCondition().getMethods().contains(RequestMethod.GET)) {
                Optional.ofNullable(k.getPathPatternsCondition())
                        .map(p -> patterns.addAll(p.getPatternValues()));

            }
        });
        Set<String> inserted = layoutService.insertEndpoints(patterns);
        log.info("Added new endpoint layout(s): {} ", inserted);
    }
}
