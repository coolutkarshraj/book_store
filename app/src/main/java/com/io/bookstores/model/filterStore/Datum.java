
package com.io.bookstores.model.filterStore;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Datum {

    @SerializedName("active")
    private Boolean mActive;
    @SerializedName("addedBy")
    private AddedBy mAddedBy;
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
    @SerializedName("books")
    private Long mBooks;
    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("isPremium")
    private Boolean mIsPremium;
    @SerializedName("lastLogged")
    private Object mLastLogged;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("phone_code")
    private String mPhoneCode;
    @SerializedName("registrationToken")
    private String mRegistrationToken;
    @SerializedName("storeId")
    private Long mStoreId;

    @SerializedName("arabicName")
    String arabicName;

    @SerializedName("arabicDescription")
    String arabicDescription;

    public Boolean getmActive() {
        return mActive;
    }

    public void setmActive(Boolean mActive) {
        this.mActive = mActive;
    }

    public AddedBy getmAddedBy() {
        return mAddedBy;
    }

    public void setmAddedBy(AddedBy mAddedBy) {
        this.mAddedBy = mAddedBy;
    }

    public Long getmAddedById() {
        return mAddedById;
    }

    public void setmAddedById(Long mAddedById) {
        this.mAddedById = mAddedById;
    }

    public Address getmAddress() {
        return mAddress;
    }

    public void setmAddress(Address mAddress) {
        this.mAddress = mAddress;
    }

    public Long getmAddressId() {
        return mAddressId;
    }

    public void setmAddressId(Long mAddressId) {
        this.mAddressId = mAddressId;
    }

    public String getmAvatarName() {
        return mAvatarName;
    }

    public void setmAvatarName(String mAvatarName) {
        this.mAvatarName = mAvatarName;
    }

    public String getmAvatarPath() {
        return mAvatarPath;
    }

    public void setmAvatarPath(String mAvatarPath) {
        this.mAvatarPath = mAvatarPath;
    }

    public Long getmBooks() {
        return mBooks;
    }

    public void setmBooks(Long mBooks) {
        this.mBooks = mBooks;
    }

    public String getmCreatedDate() {
        return mCreatedDate;
    }

    public void setmCreatedDate(String mCreatedDate) {
        this.mCreatedDate = mCreatedDate;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public Boolean getmIsPremium() {
        return mIsPremium;
    }

    public void setmIsPremium(Boolean mIsPremium) {
        this.mIsPremium = mIsPremium;
    }

    public Object getmLastLogged() {
        return mLastLogged;
    }

    public void setmLastLogged(Object mLastLogged) {
        this.mLastLogged = mLastLogged;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmPhoneCode() {
        return mPhoneCode;
    }

    public void setmPhoneCode(String mPhoneCode) {
        this.mPhoneCode = mPhoneCode;
    }

    public String getmRegistrationToken() {
        return mRegistrationToken;
    }

    public void setmRegistrationToken(String mRegistrationToken) {
        this.mRegistrationToken = mRegistrationToken;
    }

    public Long getmStoreId() {
        return mStoreId;
    }

    public void setmStoreId(Long mStoreId) {
        this.mStoreId = mStoreId;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getArabicDescription() {
        return arabicDescription;
    }

    public void setArabicDescription(String arabicDescription) {
        this.arabicDescription = arabicDescription;
    }
}
