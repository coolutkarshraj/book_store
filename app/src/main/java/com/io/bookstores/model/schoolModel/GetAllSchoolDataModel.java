package com.io.bookstores.model.schoolModel;

import com.google.gson.annotations.SerializedName;

public class GetAllSchoolDataModel {

    @SerializedName("avatarName")
    private String avatarName;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("city")
    private String city;

    @SerializedName("schoolId")
    private int schoolId;

    @SerializedName("avatarPath")
    private String avatarPath;

    @SerializedName("description")
    private String description;

    @SerializedName("state")
    private String state;

    @SerializedName("schoolName")
    private String schoolName;

    @SerializedName("addedById")
    private int addedById;

    @SerializedName("addressId")
    private int addressId;

    public String getAvatarName() {		return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getAddedById() {
        return addedById;
    }

    public void setAddedById(int addedById) {
        this.addedById = addedById;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
