package org.termi.common.entity;

import org.termi.common.enumeration.OrderStatus;

import java.util.List;

public class Order extends BaseEntity {
    private List<OrderedProduct> products;
    private OrderStatus orderStatus;
}