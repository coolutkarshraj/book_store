package com.io.bookstore.model.addAddressResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAddressListDataModel {

    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("avatarPath")
    @Expose
    private Object avatarPath;
    @SerializedName("avatarName")
    @Expose
    private Object avatarName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("otp")
    @Expose
    private Object otp;
    @SerializedName("deliveryAddresses")
    @Expose
    private List<DeliveryAddress> deliveryAddresses = null;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(Object avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Object getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(Object avatarName) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getOtp() {
        return otp;
    }

    public void setOtp(Object otp) {
        this.otp = otp;
    }

    public List<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }


}
