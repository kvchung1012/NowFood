package com.cntt2.nowfood.common;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/24/2021 4:43 PM
 */
public class Constants {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_SHOP = "SHOP";
    public static final String ROLE_CUSTOMER = "CUSTOMER";

    public static enum OrderStatus {
        PENDING,
        CONFIRMED,
        COOKING,
        SHIPPING,
        DELIVERED,
        CANCELED,
        RETURNED;
    }
    public static enum PaymentStatus {
        PAID,
        UNPAID,
        FAILED;
    }
    public static enum OrderType {
        DELIVERY,
        TAKE_AWAY;
    }
    public static enum PaymentMethod {
        CASH_ON_DELIVERY,
        ATM_PAYMENT,
        NOW_FOOD_PAYMENT;
    }
}
