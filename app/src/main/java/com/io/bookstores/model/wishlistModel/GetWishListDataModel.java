package com.io.bookstores.model.wishlistModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetWishListDataModel {

    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("modifiedDate")
    @Expose
    private Object modifiedDate;
    @SerializedName("bookId")
    @Expose
    private Long bookId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("outOfStock")
    @Expose
    private Boolean outOfStock;
    @SerializedName("avatarPath")
    @Expose
    private String avatarPath;
    @SerializedName("avatarId")
    @Expose
    private String avatarId;
    @SerializedName("avatarName")
    @Expose
    private String avatarName;
    @SerializedName("avatarWidth")
    @Expose
    private Integer avatarWidth;
    @SerializedName("avatarHeight")
    @Expose
    private Integer avatarHeight;
    @SerializedName("price")
    @Expose
    private Long price;
    @SerializedName("dateAvailable")
    @Expose
    private String dateAvailable;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("gstPrice")
    @Expose
    private Long gstPrice;
    @SerializedName("categoryId")
    @Expose
    private Object categoryId;
    @SerializedName("storeId")
    @Expose
    private Integer storeId;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Object modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(Boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public Integer getAvatarWidth() {
        return avatarWidth;
    }

    public void setAvatarWidth(Integer avatarWidth) {
        this.avatarWidth = avatarWidth;
    }

    public Integer getAvatarHeight() {
        return avatarHeight;
    }

    public void setAvatarHeight(Integer avatarHeight) {
        this.avatarHeight = avatarHeight;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(String dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getGstPrice() {
        return gstPrice;
    }

    public void setGstPrice(Long gstPrice) {
        this.gstPrice = gstPrice;
    }

    public Object getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Object categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

}
