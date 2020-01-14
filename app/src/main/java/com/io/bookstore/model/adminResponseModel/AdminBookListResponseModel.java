package com.io.bookstore.model.adminResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminBookListResponseModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<AdminBookDataModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<AdminBookDataModel> getData() {
        return data;
    }

    public void setData(List<AdminBookDataModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
