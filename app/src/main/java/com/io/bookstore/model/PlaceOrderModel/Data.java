
package com.io.bookstore.model.PlaceOrderModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("adminPrice")
    private Long mAdminPrice;
    @SerializedName("itemPrice")
    private Long mItemPrice;
    @SerializedName("orderId")
    private Long mOrderId;
    @SerializedName("orderStatus")
    private String mOrderStatus;
    @SerializedName("storePrice")
    private Long mStorePrice;
    @SerializedName("totalPrice")
    private Long mTotalPrice;

    public Long getAdminPrice() {
        return mAdminPrice;
    }

    public void setAdminPrice(Long adminPrice) {
        mAdminPrice = adminPrice;
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

}
