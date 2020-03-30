
package com.io.bookstores.model.guestModel;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class GuestResponseModel {

    @SerializedName("data")
    private GuestDataModel mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public GuestDataModel getData() {
        return mData;
    }

    public void setData(GuestDataModel data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
