package com.io.bookstore.model.wishlistModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddorRemoveWishlistResponseModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private AddorRemoveWishlistDataModel data;

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

    public AddorRemoveWishlistDataModel getData() {
        return data;
    }

    public void setData(AddorRemoveWishlistDataModel data) {
        this.data = data;
    }

}
