package org.termi.common.constant;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.termi.common.annotation.form.Color;
import org.termi.common.annotation.form.Editor;
import org.termi.common.annotation.form.File;
import org.termi.common.annotation.form.Hidden;
import org.termi.common.annotation.form.HtmlEditor;
import org.termi.common.annotation.form.Image;
import org.termi.common.annotation.form.Select;
import org.termi.common.annotation.form.TextArea;
import org.termi.common.enumeration.FormType;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

public interface FormTypeAnnotations {
    BiMap<FormType, Class<? extends Annotation>> FORM_TYPE_TO_ANNOTATION = HashBiMap.create(Map.of(
            FormType.IMAGE, Image.class,
            FormType.FILE, File.class,
            FormType.EDITOR, Editor.class,
            FormType.SELECT, Select.class,
            FormType.HIDDEN, Hidden.class,
            FormType.TEXTAREA, TextArea.class,
            FormType.COLOR, Color.class,
            FormType.HTML_EDITOR, HtmlEditor.class
    ));

    BiMap<Class<? extends Annotation>, FormType> ANNOTATION_TO_FORM_TYPE = FORM_TYPE_TO_ANNOTATION.inverse();
    Set<FormType> FORM_TYPES = ANNOTATION_TO_FORM_TYPE.values();
    Set<Class<? extends Annotation>> FORM_ANNOTATIONS = FORM_TYPE_TO_ANNOTATION.values();
}

