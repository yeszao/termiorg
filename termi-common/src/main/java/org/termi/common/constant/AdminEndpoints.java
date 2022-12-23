package org.termi.common.constant;

import org.termi.common.util.ClassUtil;

import java.util.Map;

public class AdminEndpoints {
    private AdminEndpoints() {
    }

    public static final String HOME_URL = "/admin";

    // Page
    public static final String PAGE_STATUS_API = "/admin/api/page-status";
    public static final String PAGE_PARENT_URL = "";
    public static final String PAGE_BASE_URL = "/admin/pages";
    public static final String PAGE_ADD_URL = "/admin/pages/add";
    public static final String PAGE_EDIT_URL = "/admin/pages/edit";
    public static final String PAGE_DELETE_URL = "/admin/pages/delete";

    // Category
    public static final String CATEGORY_ALL_API = "/admin/api/categories";
    public static final String CATEGORY_BASE_URL = "/admin/categories";
    public static final String CATEGORY_PARENT_URL = "";
    public static final String CATEGORY_ADD_URL = "/admin/categories/add";
    public static final String CATEGORY_EDIT_URL = "/admin/categories/edit";
    public static final String CATEGORY_DELETE_URL = "/admin/categories/delete";

    // Product Specification
    public static final String PRODUCT_SPECIFICATION_ALL_API = "/admin/api/product-specifications";
    public static final String PRODUCT_SPECIFICATION_BASE_URL = "/admin/product-specifications";
    public static final String PRODUCT_SPECIFICATION_PARENT_URL = "";
    public static final String PRODUCT_SPECIFICATION_ADD_URL = "/admin/product-specifications/add";
    public static final String PRODUCT_SPECIFICATION_EDIT_URL = "/admin/product-specifications/edit";
    public static final String PRODUCT_SPECIFICATION_DELETE_URL = "/admin/product-specifications/delete";

    // Product Specification value
    public static final String PRODUCT_SPECIFICATION_VALUE_BASE_URL = "/admin/product-specification-values";
    public static final String PRODUCT_SPECIFICATION_VALUE_PARENT_URL = PRODUCT_SPECIFICATION_BASE_URL;
    public static final String PRODUCT_SPECIFICATION_VALUE_ADD_URL = "/admin/product-specifications-values/add";
    public static final String PRODUCT_SPECIFICATION_VALUE_EDIT_URL = "/admin/product-specifications-values/edit";
    public static final String PRODUCT_SPECIFICATION_VALUE_DELETE_URL = "/admin/product-specifications-values/delete";

    // Product
    public static final String PRODUCT_STATUS_API = "/admin/api/product-status";
    public static final String PRODUCT_BASE_URL = "/admin/products";
    public static final String PRODUCT_PARENT_URL = "";
    public static final String PRODUCT_ADD_URL = "/admin/products/add";
    public static final String PRODUCT_EDIT_URL = "/admin/products/edit";
    public static final String PRODUCT_DELETE_URL = "/admin/products/delete";

    // Attachment
    public static final String ATTACHMENT_UPLOAD_API = "/admin/api/upload";
    public static final String ATTACHMENT_PARENT_URL = "";
    public static final String ATTACHMENT_BASE_URL = "/admin/attachments";
    public static final String ATTACHMENT_EDIT_URL = "/admin/attachments/edit";
    public static final String ATTACHMENT_DELETE_URL = "/admin/attachments/delete";

    // Menu
    public static final String MENU_ALL_API = "/admin/api/menus";
    public static final String MENU_BASE_URL = "/admin/menus";
    public static final String MENU_PARENT_URL = "";
    public static final String MENU_ADD_URL = "/admin/menus/add";
    public static final String MENU_EDIT_URL = "/admin/menus/edit";
    public static final String MENU_DELETE_URL = "/admin/menus/delete";


    // Layout
    public static final String LAYOUT_ALL_API = "/admin/api/layouts";
    public static final String LAYOUT_BASE_URL = "/admin/layouts";
    public static final String LAYOUT_PARENT_URL = "";
    public static final String LAYOUT_ADD_URL = "/admin/layouts/add";
    public static final String LAYOUT_EDIT_URL = "/admin/layouts/edit";
    public static final String LAYOUT_DELETE_URL = "/admin/layouts/delete";

    // Icon
    public static final String ICON_ALL_API = "/admin/api/icons";

    public static final Map<String, String> ALL = ClassUtil.toMap(AdminEndpoints.class);
    public static final Map<String, String> HOME = ClassUtil.toMap(AdminEndpoints.class, "HOME_");
    public static final Map<String, String> PAGE = ClassUtil.toMap(AdminEndpoints.class, "PAGE_");
    public static final Map<String, String> CATEGORY = ClassUtil.toMap(AdminEndpoints.class, "CATEGORY_");
    public static final Map<String, String> PRODUCT_SPECIFICATION_VALUE = ClassUtil.toMap(AdminEndpoints.class, "PRODUCT_SPECIFICATION_VALUE_");
    public static final Map<String, String> PRODUCT_SPECIFICATION = ClassUtil.toMap(AdminEndpoints.class, "PRODUCT_SPECIFICATION_");
    public static final Map<String, String> PRODUCT = ClassUtil.toMap(AdminEndpoints.class, "PRODUCT_");
    public static final Map<String, String> ATTACHMENT = ClassUtil.toMap(AdminEndpoints.class, "ATTACHMENT_");
    public static final Map<String, String> MENU = ClassUtil.toMap(AdminEndpoints.class, "MENU_");
    public static final Map<String, String> LAYOUT = ClassUtil.toMap(AdminEndpoints.class, "LAYOUT_");
    public static final Map<String, String> ICON = ClassUtil.toMap(AdminEndpoints.class, "ICON_");
}