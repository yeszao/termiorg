package org.termi.common.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.termi.common.entity.Setting;
import org.termi.common.exception.NotFoundException;
import org.termi.common.repository.SettingRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Service("CommonSettingService")
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingRepository repository;

    @Override
    public String get(String name) {
        return repository.findFirstByName(name).map(Setting::getValue)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Map<String, String> getAll() {
        Map<String, String> map = repository.findAll().stream()
                .collect(Collectors.toMap(Setting::getName, Setting::getValue));

        map.put("uploadBaseUrl", StringUtils.stripEnd(map.get("uploadBaseUrl"), "/") + "/");

        return map;
    }
}