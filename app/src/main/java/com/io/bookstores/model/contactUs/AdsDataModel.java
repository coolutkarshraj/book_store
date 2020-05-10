package com.io.bookstores.model.contactUs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsDataModel {
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("addvertismentId")
    @Expose
    private Integer addvertismentId;
    @SerializedName("tittle")
    @Expose
    private String tittle;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("avatarPath")
    @Expose
    private String avatarPath;
    @SerializedName("avatarName")
    @Expose
    private String avatarName;

    @SerializedName("url")
    @Expose
    private String url;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getAddvertismentId() {
        return addvertismentId;
    }

    public void setAddvertismentId(Integer addvertismentId) {
        this.addvertismentId = addvertismentId;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
