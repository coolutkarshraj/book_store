package com.io.bookstores.model.schoolWishlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllSchoolWishListResponseModel {

    @SerializedName("data")
    private List<GetAllSchoolWishListDataModel> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public List<GetAllSchoolWishListDataModel> getData() {
        return data;
    }

    public void setData(List<GetAllSchoolWishListDataModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}