package com.io.bookstores.model.contactUs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.io.bookstores.model.loginModel.User;

public class ContactUsDataModel {

    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("tittle")
    @Expose
    private String tittle;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("complainId")
    @Expose
    private Integer complainId;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getComplainId() {
        return complainId;
    }

    public void setComplainId(Integer complainId) {
        this.complainId = complainId;
    }

}
