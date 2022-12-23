package org.termi.common.dto;

import org.springframework.util.StringUtils;
import org.termi.common.annotation.Form;
import org.termi.common.annotation.Input;
import org.termi.common.annotation.form.Color;
import org.termi.common.annotation.form.Disabled;
import org.termi.common.annotation.form.Editor;
import org.termi.common.annotation.form.Hidden;
import org.termi.common.annotation.form.HtmlEditor;
import org.termi.common.annotation.form.Image;
import org.termi.common.annotation.form.Images;
import org.termi.common.annotation.form.NotForm;
import org.termi.common.annotation.form.Select;
import org.termi.common.annotation.form.TextArea;
import org.termi.common.constant.PreGroup;
import org.termi.common.enumeration.FormType;
import org.termi.common.util.ObjectUtil;
import org.termi.common.util.TypeUtil;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public record HtmlForm(
        long id,
        TreeMap<String, List<HtmlFormField>> groups
) {
    public HtmlForm() {
        this(0, new TreeMap<>());
    }

    public static <T> HtmlForm of(T t) {
        return of(0, t);
    }

    public static <T> HtmlForm of(long id, T t) {
        if (Objects.isNull(t) || !t.getClass().isAnnotationPresent(Form.class)) {
            return new HtmlForm();
        }

        List<HtmlFormField> formColumns = new ArrayList<>();
        List<Field> fields = ObjectUtil.getObjectFields(t.getClass());
        for (Field f : fields) {
            if (f.isAnnotationPresent(NotForm.class) || isJpaMappedField(f)) {
                continue;
            }

            if (!TypeUtil.isFormable(f.getType())) {
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
            if (formType.equals(FormType.SELECT)) {
                Select select = f.getAnnotation(Select.class);
                extra = Map.of(
                        "url", select.url(),
                        "multiple", select.multiple(),
                        "searchable", select.searchable()
                );
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

        return new HtmlForm(id, formColumns.stream().collect(Collectors.groupingBy(HtmlFormField::group, TreeMap::new, Collectors.toList())));
    }

    private static FormType getFormType(Field f) {
        // default is input label
        FormType formType = FormType.TEXT;

        // read from type
        if (TypeUtil.isBool(f.getType())) {
            formType = FormType.SWITCH;
        } else if (TypeUtil.isNumber(f.getType())) {
            formType = FormType.NUMBER;
        }

        // read from annotation
        if (f.isAnnotationPresent(Image.class)) {
            formType = FormType.IMAGE;
        } else if (f.isAnnotationPresent(Images.class)) {
            formType = FormType.IMAGES;
        } else if (f.isAnnotationPresent(Editor.class)) {
            formType = FormType.EDITOR;
        } else if (f.isAnnotationPresent(Select.class)) {
            formType = FormType.SELECT;
        } else if (f.isAnnotationPresent(Hidden.class)) {
            formType = FormType.HIDDEN;
        } else if (f.isAnnotationPresent(TextArea.class)) {
            formType = FormType.TEXTAREA;
        } else if (f.isAnnotationPresent(Color.class)) {
            formType = FormType.COLOR;
        }else if (f.isAnnotationPresent(HtmlEditor.class)) {
            formType = FormType.HTML_EDITOR;
        }

        return formType;
    }

    private static boolean isJpaMappedField(Field f) {
        return f.isAnnotationPresent(ManyToOne.class)
                || f.isAnnotationPresent(OneToMany.class)
                || f.isAnnotationPresent(OneToOne.class);
    }
}