package org.termi.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.termi.common.entity.Setting;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, Long>, JpaSpecificationExecutor<Setting> {
    Optional<Setting> findFirstByName(String name);
}