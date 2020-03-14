package com.io.bookstores.model.addAddressResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAdminOrderListDataModel {


    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("totalPrice")
    @Expose
    private Integer totalPrice;
    @SerializedName("itemPrice")
    @Expose
    private Integer itemPrice;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("storeId")
    @Expose
    private Integer storeId;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("delivery")
    @Expose
    private Object delivery;
    @SerializedName("orderItems")
    @Expose
    private List<OrderItem> orderItems = null;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Object getDelivery() {
        return delivery;
    }

    public void setDelivery(Object delivery) {
        this.delivery = delivery;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
