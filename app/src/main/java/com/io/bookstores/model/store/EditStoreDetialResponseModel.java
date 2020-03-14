package com.io.bookstores.model.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditStoreDetialResponseModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private EditStoreDetialDataModel data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EditStoreDetialDataModel getData() {
        return data;
    }

    public void setData(EditStoreDetialDataModel data) {
        this.data = data;
    }
}
