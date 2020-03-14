
package com.io.bookstores.model.myOrder;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Delivery {

    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("deliveryAddressId")
    private Long mDeliveryAddressId;
    @SerializedName("deliveryId")
    private Long mDeliveryId;
    @SerializedName("deliveryPrice")
    private Long mDeliveryPrice;
    @SerializedName("deliveryStatus")
    private String mDeliveryStatus;
    @SerializedName("deliveryType")
    private String mDeliveryType;

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public Long getDeliveryAddressId() {
        return mDeliveryAddressId;
    }

    public void setDeliveryAddressId(Long deliveryAddressId) {
        mDeliveryAddressId = deliveryAddressId;
    }

    public Long getDeliveryId() {
        return mDeliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        mDeliveryId = deliveryId;
    }

    public Long getDeliveryPrice() {
        return mDeliveryPrice;
    }

    public void setDeliveryPrice(Long deliveryPrice) {
        mDeliveryPrice = deliveryPrice;
    }

    public String getDeliveryStatus() {
        return mDeliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        mDeliveryStatus = deliveryStatus;
    }

    public String getDeliveryType() {
        return mDeliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        mDeliveryType = deliveryType;
    }

}
