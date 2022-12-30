package org.termi.common.service;

import java.util.Map;

public interface SettingService {
    String get(String name);
    Map<String, String> getAll();
}