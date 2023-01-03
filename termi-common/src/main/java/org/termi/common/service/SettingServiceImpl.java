package org.termi.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.termi.common.entity.Setting;
import org.termi.common.exception.NotFoundException;
import org.termi.common.repository.SettingRepository;

import java.util.Map;

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
        return Map.of(
                "siteName", "Termi",
                "siteSlogan", "All is ready for the best"
        );
    }
}