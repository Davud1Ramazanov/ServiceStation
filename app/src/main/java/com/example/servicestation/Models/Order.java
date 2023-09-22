package com.example.servicestation.Models;

public class Order {
    public int orderId;
    public int categoryId;
    public String userName;

    public int getCategoryId() {
        return categoryId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getUserName() {
        return userName;
    }
}
