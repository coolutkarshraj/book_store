
package com.io.bookstores.model.getAddressResponseModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("addressId")
    private Long mAddressId;
    @SerializedName("city")
    private String mCity;
    @SerializedName("district")
    private String district;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("landmark")
    private String mLandmark;
    @SerializedName("locality")
    private String mLocality;
    @SerializedName("state")
    private String mState;
    @SerializedName("storeId")
    private Object mStoreId;
    @SerializedName("zipcode")
    private Long mZipcode;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public Long getAddressId() {
        return mAddressId;
    }

    public void setAddressId(Long addressId) {
        mAddressId = addressId;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public String getLandmark() {
        return mLandmark;
    }

    public void setLandmark(String landmark) {
        mLandmark = landmark;
    }

    public String getLocality() {
        return mLocality;
    }

    public void setLocality(String locality) {
        mLocality = locality;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public Object getStoreId() {
        return mStoreId;
    }

    public void setStoreId(Object storeId) {
        mStoreId = storeId;
    }

    public Long getZipcode() {
        return mZipcode;
    }

    public void setZipcode(Long zipcode) {
        mZipcode = zipcode;
    }

}
