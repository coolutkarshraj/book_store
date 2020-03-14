package com.io.bookstores.model.orderModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderStatusChangeDataModel {
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
    @SerializedName("deliveryId")
    @Expose
    private Integer deliveryId;
    @SerializedName("adminId")
    @Expose
    private Integer adminId;
    @SerializedName("adminPrice")
    @Expose
    private Integer adminPrice;
    @SerializedName("storePrice")
    @Expose
    private Integer storePrice;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;

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

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getAdminPrice() {
        return adminPrice;
    }

    public void setAdminPrice(Integer adminPrice) {
        this.adminPrice = adminPrice;
    }

    public Integer getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(Integer storePrice) {
        this.storePrice = storePrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
