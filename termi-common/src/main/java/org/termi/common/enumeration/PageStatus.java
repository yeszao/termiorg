package org.termi.common.enumeration;

public enum PageStatus {
    DRAFT("Draft"),
    PUBLISHED("Published"),
    DISABLED("Disabled"),
    DELETED("Deleted");

    final String text;

    PageStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}