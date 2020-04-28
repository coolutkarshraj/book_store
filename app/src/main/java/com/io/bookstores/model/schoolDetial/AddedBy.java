package com.io.bookstores.model.schoolDetial;

import com.google.gson.annotations.SerializedName;

public class AddedBy {

    @SerializedName("avatarName")
    private String avatarName;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("address")
    private String address;

    @SerializedName("phone")
    private String phone;

    @SerializedName("adminId")
    private int adminId;

    @SerializedName("avatarPath")
    private String avatarPath;

    @SerializedName("name")
    private String name;

    @SerializedName("lastLogged")
    private String lastLogged;

    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("email")
    private String email;

    @SerializedName("parentId")
    private Object parentId;

    @SerializedName("username")
    private String username;

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLastLogged(String lastLogged) {
        this.lastLogged = lastLogged;
    }

    public String getLastLogged() {
        return lastLogged;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


}