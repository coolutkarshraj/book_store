
package com.io.bookstores.model.filterStore;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AddedBy {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("adminId")
    private Long mAdminId;
    @SerializedName("avatarName")
    private String mAvatarName;
    @SerializedName("avatarPath")
    private String mAvatarPath;
    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("isActive")
    private Boolean mIsActive;
    @SerializedName("lastLogged")
    private Object mLastLogged;
    @SerializedName("name")
    private String mName;
    @SerializedName("parentId")
    private Object mParentId;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("username")
    private String mUsername;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public Long getAdminId() {
        return mAdminId;
    }

    public void setAdminId(Long adminId) {
        mAdminId = adminId;
    }

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

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Boolean getIsActive() {
        return mIsActive;
    }

    public void setIsActive(Boolean isActive) {
        mIsActive = isActive;
    }

    public Object getLastLogged() {
        return mLastLogged;
    }

    public void setLastLogged(Object lastLogged) {
        mLastLogged = lastLogged;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Object getParentId() {
        return mParentId;
    }

    public void setParentId(Object parentId) {
        mParentId = parentId;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
