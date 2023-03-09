package org.termi.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.termi.admin.query.SearchQuery;
import org.termi.common.entity.Setting;
import org.termi.common.exception.NotFoundException;
import org.termi.common.repository.SettingRepository;
import org.termi.common.util.JsonUtil;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service("AdminSettingService")
public class SettingServiceImpl implements SettingService {
    private final SettingRepository repository;

    @Autowired
    public SettingServiceImpl(SettingRepository repository) {
        this.repository = repository;
    }

    @Override
    public SettingRepository getRepository() {
        return repository;
    }

    @Override
    public Page<Setting> getList(Pageable pageable, SearchQuery query) {
        Specification<Setting> spec = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(query.q())) {
                predicates.add(cb.like(root.get("name"), '%' + query.q() + '%'));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            return null;
        };

        return repository.findAll(spec, pageable);
    }

    @Override
    public <T> T getJson(String name, Class<T> clazz) {
        Setting setting = repository.findFirstByName(name).orElseThrow(NotFoundException::new);
        if (ObjectUtils.isEmpty(setting.getValue())) {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Can not create new instance for json value");
            }
        }

        return JsonUtil.parse(setting.getValue(), clazz);
    }

    @Override
    public <T> void setJson(String name, T object) {
        Setting setting = repository.findFirstByName(name).orElseThrow(NotFoundException::new);
        setting.setValue(JsonUtil.objectToString(object));
        repository.save(setting);
    }
}