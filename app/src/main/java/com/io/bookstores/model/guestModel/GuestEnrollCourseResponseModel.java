
package com.io.bookstores.model.guestModel;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class GuestEnrollCourseResponseModel {

    @SerializedName("data")
    private GuestEnrollCourseDataModel mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public GuestEnrollCourseDataModel getData() {
        return mData;
    }

    public void setData(GuestEnrollCourseDataModel data) {
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
