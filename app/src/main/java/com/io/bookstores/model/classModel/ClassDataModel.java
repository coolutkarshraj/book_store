package com.io.bookstores.model.classModel;

import com.google.gson.annotations.SerializedName;

public class ClassDataModel {
    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("classGroupId")
    private int classGroupId;

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setClassGroupId(int classGroupId) {
        this.classGroupId = classGroupId;
    }

    public int getClassGroupId() {
        return classGroupId;
    }


}
