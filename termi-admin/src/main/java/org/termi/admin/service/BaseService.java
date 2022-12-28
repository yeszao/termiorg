package org.termi.admin.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.termi.common.util.BeanUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface BaseService<T, ID> {
    JpaRepository<T, ID> getRepository();

    default Optional<T> findById(ID id) {
        if (Objects.isNull(id)) {
            return Optional.empty();
        }
        return getRepository().findById(id);
    }

    default List<T> findAll() {
        return getRepository().findAll();
    }

    @Transactional
    default T save(T entity) {
        return getRepository().save(entity);
    }

    @Transactional
    default T update(T oldEntity, T entity) {
        BeanUtils.copyProperties(entity, oldEntity, BeanUtil.getNullPropertyNames(entity));
        return save(oldEntity);
    }

    @Transactional
    default void delete(ID id) {
        getRepository().deleteById(id);
    }
}