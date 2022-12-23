package org.termi.common.enumeration;

public enum ProductType {
    PHYSICAL_PRODUCT, //  need add receive address for shipment
    VIRTUAL_PRODUCT_TO_GENERATE_LICENSE_CODE, // need add email url
    VIRTUAL_PRODUCT_TO_DISPLAY_DOWNLOAD_URL, // need add email url
    VIRTUAL_PRODUCT_TO_GENERATE_LICENSE_FILE, // need add email url
    SERVICE_PROVIDED_REMOTELY,  // need add phone and email url
    SERVICE_PROVIDED_DOOR_TO_DOOR, // need add receive address for door-to-door
}