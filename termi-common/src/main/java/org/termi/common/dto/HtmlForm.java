package org.termi.common.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.termi.common.annotation.Form;
import org.termi.common.annotation.Input;
import org.termi.common.annotation.form.Disabled;
import org.termi.common.annotation.form.File;
import org.termi.common.annotation.form.Image;
import org.termi.common.annotation.form.NotForm;
import org.termi.common.annotation.form.Select;
import org.termi.common.constant.PreGroup;
import org.termi.common.enumeration.FormType;
import org.termi.common.util.ObjectUtil;
import org.termi.common.util.TypeUtil;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.termi.common.constant.FormTypeAnnotations.FORM_ANNOTATIONS;
import static org.termi.common.constant.FormTypeAnnotations.ANNOTATION_TO_FORM_TYPE;

@Slf4j
public record HtmlForm(
        TreeMap<String, List<HtmlFormField>> groups
) {
    public HtmlForm() {
        this(new TreeMap<>());
    }

    public static <T> HtmlForm of(T t) {
        if (Objects.isNull(t) || !t.getClass().isAnnotationPresent(Form.class)) {
            return new HtmlForm();
        }

        List<HtmlFormField> formColumns = new ArrayList<>();
        List<Field> fields = ObjectUtil.getObjectFields(t.getClass());
        for (Field f : fields) {
            if (f.isAnnotationPresent(NotForm.class) || isJpaMappedField(f)) {
                continue;
            }

            f.setAccessible(true);

            String label = "";
            boolean required = false;
            String group = PreGroup.EMPTY;
            int order = 0;
            byte grid = 12;
            if (f.isAnnotationPresent(Input.class)) {
                Input input = f.getAnnotation(Input.class);
                label = input.label();
                group = input.group();
                required =input.required();
                order = input.order();
                grid = input.grid();
            }

            if (label.isEmpty()) {
                label = StringUtils.capitalize(f.getName());
            }

            FormType formType = getFormType(f);
            Object extra = null;
            switch (formType) {
                case SELECT -> extra = ObjectUtil.annotationToMap(f, Select.class);
                case IMAGE -> extra = ObjectUtil.annotationToMap(f, Image.class);
                case FILE -> extra = ObjectUtil.annotationToMap(f, File.class);
            }

            try {
                HtmlFormField htmlFormField = new HtmlFormField(
                        f.getName(),
                        label,
                        required,
                        f.get(t),
                        formType,
                        group,
                        order,
                        f.isAnnotationPresent(Disabled.class),
                        grid,
                        extra);
                formColumns.add(htmlFormField);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        formColumns.sort(Comparator.comparingInt(HtmlFormField::order));

        return new HtmlForm(formColumns.stream().collect(Collectors.groupingBy(HtmlFormField::group, TreeMap::new, Collectors.toList())));
    }

    private static FormType getFormType(Field f) {
        // default is input label
        FormType formType = FormType.TEXT;

        // read annotation classes
        Set<Class<? extends Annotation>> allAnnotationClazz =
                Arrays.stream(f.getDeclaredAnnotations())
                .map(Annotation::annotationType)
                .collect(Collectors.toSet());

        allAnnotationClazz.retainAll(FORM_ANNOTATIONS);
        if (allAnnotationClazz.iterator().hasNext()) {
            formType = ANNOTATION_TO_FORM_TYPE.get(allAnnotationClazz.iterator().next());
        }else if (TypeUtil.isBool(f.getType())) {
            formType = FormType.SWITCH;
        } else if (TypeUtil.isNumber(f.getType())) {
            formType = FormType.NUMBER;
        }

        return formType;
    }

    private static boolean isJpaMappedField(Field f) {
        return f.isAnnotationPresent(ManyToOne.class)
                || f.isAnnotationPresent(OneToMany.class)
                || f.isAnnotationPresent(OneToOne.class);
    }
}