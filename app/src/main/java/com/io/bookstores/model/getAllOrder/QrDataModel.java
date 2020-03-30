
package com.io.bookstores.model.getAllOrder;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class QrDataModel {

    @SerializedName("price")
    private Long mPrice;
    @SerializedName("type")
    private String mType;
    @SerializedName("unit")
    private String mUnit;

    public Long getPrice() {
        return mPrice;
    }

    public void setPrice(Long price) {
        mPrice = price;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

}
