package com.io.bookstore.model.sliderAdModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdData {


    @SerializedName("discountId")
    @Expose
    private Integer discountId;
    @SerializedName("minOrder")
    @Expose
    private Integer minOrder;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("avatarPath")
    @Expose
    private String imagePath;


    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public Integer getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(Integer minOrder) {
        this.minOrder = minOrder;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}


