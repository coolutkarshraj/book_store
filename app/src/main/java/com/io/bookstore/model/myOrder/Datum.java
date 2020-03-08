
package com.io.bookstore.model.myOrder;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Datum {

    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("delivery")
    private Delivery mDelivery;
    @SerializedName("itemPrice")
    private Long mItemPrice;
    @SerializedName("orderId")
    private Long mOrderId;
    @SerializedName("orderItems")
    private List<OrderItem> mOrderItems;
    @SerializedName("orderStatus")
    private String mOrderStatus;
    @SerializedName("storeId")
    private Long mStoreId;
    @SerializedName("totalPrice")
    private Long mTotalPrice;
    @SerializedName("user")
    private User mUser;
    @SerializedName("userId")
    private Long mUserId;

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public Delivery getDelivery() {
        return mDelivery;
    }

    public void setDelivery(Delivery delivery) {
        mDelivery = delivery;
    }

    public Long getItemPrice() {
        return mItemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        mItemPrice = itemPrice;
    }

    public Long getOrderId() {
        return mOrderId;
    }

    public void setOrderId(Long orderId) {
        mOrderId = orderId;
    }

    public List<OrderItem> getOrderItems() {
        return mOrderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        mOrderItems = orderItems;
    }

    public String getOrderStatus() {
        return mOrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        mOrderStatus = orderStatus;
    }

    public Long getStoreId() {
        return mStoreId;
    }

    public void setStoreId(Long storeId) {
        mStoreId = storeId;
    }

    public Long getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        mTotalPrice = totalPrice;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

}
