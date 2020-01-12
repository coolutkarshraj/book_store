package com.io.bookstore.model.FilterDetailsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterDetailData {
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("storeId")
    @Expose
    private Integer storeId;
    @SerializedName("avatarPath")
    @Expose
    private String avatarPath;
    @SerializedName("avatarName")
    @Expose
    private String avatarName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private Integer phone;
    @SerializedName("address")
    @Expose
    private FilterDetailsAddress address;
    @SerializedName("addedBy")
    @Expose
    private FilterDetailsAddedBy addedBy;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public FilterDetailsAddress getAddress() {
        return address;
    }

    public void setAddress(FilterDetailsAddress address) {
        this.address = address;
    }

    public FilterDetailsAddedBy getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(FilterDetailsAddedBy addedBy) {
        this.addedBy = addedBy;
    }
}
