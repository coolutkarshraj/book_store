package com.io.bookstores.model.classModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassSubCategoryResponseModel {

    @SerializedName("data")
    private List<ClassSubCategoryDataModel> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public void setData(List<ClassSubCategoryDataModel> data) {
        this.data = data;
    }

    public List<ClassSubCategoryDataModel> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }


}