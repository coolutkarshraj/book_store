package com.io.bookstores.model.bookListModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishListLocalResponseModel {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Quantity")
    @Expose
    private String quantity;
    @SerializedName("Price")
    @Expose
    private String price;

    @SerializedName("avalible")
    private String availibleQty;
    @SerializedName("P_ID")
    @Expose
    private String pID;
    private String gst;
    @SerializedName("wishlist")
    private String wishlist;
    @SerializedName("type")
    private String type;

    @SerializedName("size")
    private String size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailibleQty() {
        return availibleQty;
    }

    public void setAvailibleQty(String availibleQty) {
        this.availibleQty = availibleQty;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getWishlist() {
        return wishlist;
    }

    public void setWishlist(String wishlist) {
        this.wishlist = wishlist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
