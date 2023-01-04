package com.example.wagbaserverside;

import androidx.annotation.NonNull;



public class OrderItemModel {


    private String orderItemID;
    private String orderItemName;
    private String orderItemCount;
    private String orderItemTotalPrice; // Count * Price of single item

    public OrderItemModel() {
    }

    public OrderItemModel(@NonNull String orderItemID, String orderItemName, String orderItemCount, String orderItemTotalPrice) {

        this.orderItemID = orderItemID;
        this.orderItemName = orderItemName;
        this.orderItemCount = orderItemCount;
        this.orderItemTotalPrice = orderItemTotalPrice;
    }

    @NonNull
    public String getOrderItemID() {
        return orderItemID;
    }

    public void setOrderItemID(@NonNull String orderItemID) {
        this.orderItemID = orderItemID;
    }

    public String getOrderItemName() {
        return orderItemName;
    }

    public void setOrderItemName(String orderItemName) {
        this.orderItemName = orderItemName;
    }

    public String getOrderItemCount() {
        return orderItemCount;
    }

    public void setOrderItemCount(String orderItemCount) {
        this.orderItemCount = orderItemCount;
    }

    public String getOrderItemTotalPrice() {
        return orderItemTotalPrice;
    }

    public void setOrderItemTotalPrice(String orderItemTotalPrice) {
        this.orderItemTotalPrice = orderItemTotalPrice;
    }
}
