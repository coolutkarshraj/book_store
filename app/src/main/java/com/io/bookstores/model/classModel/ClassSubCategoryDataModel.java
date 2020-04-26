package com.io.bookstores.model.classModel;

import com.google.gson.annotations.SerializedName;

public class ClassSubCategoryDataModel {
    @SerializedName("avatarName")
    private String avatarName;

    @SerializedName("classCategoryId")
    private int classCategoryId;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("name")
    private String name;

    @SerializedName("avatarPath")
    private String avatarPath;

    @SerializedName("description")
    private String description;

    @SerializedName("classSubCategoryId")
    private int classSubCategoryId;

    public void setAvatarName(String avatarName){
        this.avatarName = avatarName;
    }

    public String getAvatarName(){
        return avatarName;
    }

    public void setClassCategoryId(int classCategoryId){
        this.classCategoryId = classCategoryId;
    }

    public int getClassCategoryId(){
        return classCategoryId;
    }

    public void setCreatedDate(String createdDate){
        this.createdDate = createdDate;
    }

    public String getCreatedDate(){
        return createdDate;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setAvatarPath(String avatarPath){
        this.avatarPath = avatarPath;
    }

    public String getAvatarPath(){
        return avatarPath;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setClassSubCategoryId(int classSubCategoryId){
        this.classSubCategoryId = classSubCategoryId;
    }

    public int getClassSubCategoryId(){
        return classSubCategoryId;
    }

}
