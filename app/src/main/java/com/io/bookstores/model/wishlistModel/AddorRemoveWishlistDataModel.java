package com.io.bookstores.model.wishlistModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddorRemoveWishlistDataModel {
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("bookId")
    @Expose
    private Integer bookId;
    @SerializedName("wishListId")
    @Expose
    private Integer wishListId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getWishListId() {
        return wishListId;
    }

    public void setWishListId(Integer wishListId) {
        this.wishListId = wishListId;
    }
}
