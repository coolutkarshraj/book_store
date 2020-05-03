package com.io.bookstores.model.guestModel;

import com.google.gson.annotations.SerializedName;

public class GuestDetailDataModel {

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("phone")
    private String phone;

    @SerializedName("name")
    private String name;

    @SerializedName("guestId")
    private int guestId;

    @SerializedName("email")
    private Object email;

    public void setCreatedDate(String createdDate){
        this.createdDate = createdDate;
    }

    public String getCreatedDate(){
        return createdDate;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return phone;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setGuestId(int guestId){
        this.guestId = guestId;
    }

    public int getGuestId(){
        return guestId;
    }

    public void setEmail(Object email){
        this.email = email;
    }

    public Object getEmail(){
        return email;
    }
}
