
package com.io.bookstores.model.bookListModel;

import android.os.Build;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.RequiresApi;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("avatarName")
    private String mAvatarName;
    @SerializedName("avatarPath")
    private String mAvatarPath;
    @SerializedName("bookId")
    private Long mBookId;
    @SerializedName("category")
    private Category mCategory;
    @SerializedName("categoryId")
    private Object mCategoryId;
    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("dateAvailable")
    private String mDateAvailable;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("discount")
    private Long mDiscount;
    @SerializedName("gstPrice")
    private Long mGstPrice;
    @SerializedName("name")
    private String mName;
    @SerializedName("outOfStock")
    private Boolean mOutOfStock;
    @SerializedName("price")
    private Long mPrice;
    @SerializedName("quantity")
    private int mQuantity;
    @SerializedName("rating")
    private Long mRating;
    @SerializedName("store")
    private Store mStore;
    @SerializedName("storeId")
    private String mStoreId;

    @SerializedName("isWishlist")
    @Expose
    private boolean isWishlist;

    public String getAvatarName() {
        return mAvatarName;
    }

    public void setAvatarName(String avatarName) {
        mAvatarName = avatarName;
    }

    public String getAvatarPath() {
        return mAvatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        mAvatarPath = avatarPath;
    }

    public Long getBookId() {
        return mBookId;
    }

    public void setBookId(Long bookId) {
        mBookId = bookId;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    public Object getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(Object categoryId) {
        mCategoryId = categoryId;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public String getDateAvailable() {
        return mDateAvailable;
    }

    public void setDateAvailable(String dateAvailable) {
        mDateAvailable = dateAvailable;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Long getDiscount() {
        return mDiscount;
    }

    public void setDiscount(Long discount) {
        mDiscount = discount;
    }

    public Long getGstPrice() {
        return mGstPrice;
    }

    public void setGstPrice(Long gstPrice) {
        mGstPrice = gstPrice;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Boolean getOutOfStock() {
        return mOutOfStock;
    }

    public void setOutOfStock(Boolean outOfStock) {
        mOutOfStock = outOfStock;
    }

    public Long getPrice() {
        return mPrice;
    }

    public void setPrice(Long price) {
        mPrice = price;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public Long getRating() {
        return mRating;
    }

    public void setRating(Long rating) {
        mRating = rating;
    }

    public Store getStore() {
        return mStore;
    }

    public void setStore(Store store) {
        mStore = store;
    }

    public String getStoreId() {
        return mStoreId;
    }

    public void setStoreId(String storeId) {
        mStoreId = storeId;
    }

    public boolean isWishlist() {
        return isWishlist;
    }

    public void setWishlist(boolean wishlist) {
        isWishlist = wishlist;
    }
}
