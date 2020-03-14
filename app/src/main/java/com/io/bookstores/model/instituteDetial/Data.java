
package com.io.bookstores.model.instituteDetial;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Data {

    @SerializedName("addedById")
    private Long mAddedById;
    @SerializedName("address")
    private Address mAddress;
    @SerializedName("addressId")
    private Long mAddressId;
    @SerializedName("avatarName")
    private String mAvatarName;
    @SerializedName("avatarPath")
    private String mAvatarPath;
    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("instituteId")
    private Long mInstituteId;
    @SerializedName("instituteName")
    private String mInstituteName;
    @SerializedName("isPremium")
    private Boolean mIsPremium;

    public Long getAddedById() {
        return mAddedById;
    }

    public void setAddedById(Long addedById) {
        mAddedById = addedById;
    }

    public Address getAddress() {
        return mAddress;
    }

    public void setAddress(Address address) {
        mAddress = address;
    }

    public Long getAddressId() {
        return mAddressId;
    }

    public void setAddressId(Long addressId) {
        mAddressId = addressId;
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

    public Long getInstituteId() {
        return mInstituteId;
    }

    public void setInstituteId(Long instituteId) {
        mInstituteId = instituteId;
    }

    public String getInstituteName() {
        return mInstituteName;
    }

    public void setInstituteName(String instituteName) {
        mInstituteName = instituteName;
    }

    public Boolean getIsPremium() {
        return mIsPremium;
    }

    public void setIsPremium(Boolean isPremium) {
        mIsPremium = isPremium;
    }

}
