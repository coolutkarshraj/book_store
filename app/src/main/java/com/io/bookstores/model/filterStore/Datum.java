
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

    public Boolean getActive() {
        return mActive;
    }

    public void setActive(Boolean active) {
        mActive = active;
    }

    public AddedBy getAddedBy() {
        return mAddedBy;
    }

    public void setAddedBy(AddedBy addedBy) {
        mAddedBy = addedBy;
    }

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

    public Long getBooks() {
        return mBooks;
    }

    public void setBooks(Long books) {
        mBooks = books;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Boolean getIsPremium() {
        return mIsPremium;
    }

    public void setIsPremium(Boolean isPremium) {
        mIsPremium = isPremium;
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

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPhoneCode() {
        return mPhoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        mPhoneCode = phoneCode;
    }

    public String getRegistrationToken() {
        return mRegistrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        mRegistrationToken = registrationToken;
    }

    public Long getStoreId() {
        return mStoreId;
    }

    public void setStoreId(Long storeId) {
        mStoreId = storeId;
    }

}
