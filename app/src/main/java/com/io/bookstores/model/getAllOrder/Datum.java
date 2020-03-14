
package com.io.bookstores.model.getAllOrder;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("adminId")
    private Long mAdminId;
    @SerializedName("adminPrice")
    private Long mAdminPrice;
    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("itemPrice")
    private Long mItemPrice;
    @SerializedName("orderId")
    private Long mOrderId;
    @SerializedName("orderStatus")
    private String mOrderStatus;
    @SerializedName("storeId")
    private Long mStoreId;
    @SerializedName("storePrice")
    private Long mStorePrice;
    @SerializedName("totalPrice")
    private Long mTotalPrice;
    @SerializedName("userId")
    private Long mUserId;

    public Long getAdminId() {
        return mAdminId;
    }

    public void setAdminId(Long adminId) {
        mAdminId = adminId;
    }

    public Long getAdminPrice() {
        return mAdminPrice;
    }

    public void setAdminPrice(Long adminPrice) {
        mAdminPrice = adminPrice;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
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

    public Long getStorePrice() {
        return mStorePrice;
    }

    public void setStorePrice(Long storePrice) {
        mStorePrice = storePrice;
    }

    public Long getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        mTotalPrice = totalPrice;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

}
