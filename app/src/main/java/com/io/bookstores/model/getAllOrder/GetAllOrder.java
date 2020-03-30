
package com.io.bookstores.model.getAllOrder;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GetAllOrder {

    @SerializedName("data")
    private List<QrDataModel> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Integer mStatus;

    public List<QrDataModel> getData() {
        return mData;
    }

    public void setData(List<QrDataModel> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Integer getStatus() {
        return mStatus;
    }

    public void setStatus(Integer status) {
        mStatus = status;
    }

}
