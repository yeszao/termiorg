package org.termi.common.entity;

import javax.persistence.Lob;

public class ProductReview extends BaseEntity{
    private long productId;

    private int score; // maximum is 5
    @Lob
    private String content;
    private int leftModifyTimes;
}