package com.io.bookstores.model.classModel;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ClassCategoryModel {

    @SerializedName("data")
    private List<ClassCategoryDataModel> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public void setData(List<ClassCategoryDataModel> data) {
        this.data = data;
    }

    public List<ClassCategoryDataModel> getData() {
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