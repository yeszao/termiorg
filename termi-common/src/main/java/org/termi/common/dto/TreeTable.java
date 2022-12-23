package org.termi.common.dto;

import org.termi.common.annotation.TableElement;
import org.termi.common.annotation.form.Image;
import org.termi.common.enumeration.FormType;
import org.termi.common.util.ObjectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public record TreeTable(
        List<TableColumn> columns,
        List<? extends TreeBaseDto> rows) {

    public static TreeTable fromTree(List<? extends TreeBaseDto> rows,
                                     Class<? extends TreeBaseDto> dtoClazz) {
        List<TableColumn> columns = new ArrayList<>();
        List<Field> fields = ObjectUtil.getObjectFields(dtoClazz);
        for (Field f : fields) {
            TableColumn tableColumn;
            if (f.isAnnotationPresent(TableElement.class)) {
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

        return new TreeTable(columns, rows);
    }
}