package org.termi.common.dto;

import org.termi.common.annotation.TableElement;
import org.termi.common.annotation.form.Image;
import org.termi.common.enumeration.FormType;
import org.termi.common.util.ObjectUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public record PageableTable<E>(
        List<TableColumn> columns,
        List<E> rows,
        int page,
        int size,
        int numberOfElements,
        long totalElements,
        long totalPages,
        boolean first,
        boolean last) {

    public static <T, R> PageableTable<R> fromPage(Page<T> page, Class<R> dtoClazz) {
        List<TableColumn> columns = new ArrayList<>();
        List<R> rows = new ArrayList<>();

        List<Field> fields = ObjectUtil.getObjectFields(dtoClazz);
        for (Field f: fields) {
            TableColumn tableColumn;
            if(f.isAnnotationPresent(TableElement.class)) {
                TableElement a = f.getAnnotation(TableElement.class);
                tableColumn = new TableColumn(f.getName(),
                        a.label(),
                        f.isAnnotationPresent(Image.class) ? FormType.IMAGE : FormType.TEXT,
                        a.linkUrl(),
                        a.linkParamKey(),
                        a.indent(),
                        a.grid());
                columns.add(tableColumn);
            }
        }

        for (T t: page.getContent()) {
            R dto;
            try {
                dto = dtoClazz.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException("Can not convert properties, please try again");
            }

            BeanUtils.copyProperties(t, dto);
            rows.add(dto);
        }

        return new PageableTable<>(columns,
                rows,
                page.getNumber() + 1,
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }
}