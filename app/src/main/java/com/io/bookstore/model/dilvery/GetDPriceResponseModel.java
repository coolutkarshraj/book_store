package com.io.bookstore.model.dilvery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDPriceResponseModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Price data;

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

    public Price getData() {
        return data;
    }

    public void setData(Price data) {
        this.data = data;
    }
}