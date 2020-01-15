package com.io.bookstore.model.adminResponseModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBookResponseModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private AddBookDataModel data;

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

    public AddBookDataModel getData() {
        return data;
    }

    public void setData(AddBookDataModel data) {
        this.data = data;
    }

}
