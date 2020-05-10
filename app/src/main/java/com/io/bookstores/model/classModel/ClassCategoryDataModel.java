package com.io.bookstores.model.classModel;

import com.google.gson.annotations.SerializedName;

public class ClassCategoryDataModel {

    @SerializedName("avatarName")
    private Object avatarName;

    @SerializedName("classCategoryId")
    private int classCategoryId;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("name")
    private String name;

    @SerializedName("avatarPath")
    private Object avatarPath;

    @SerializedName("description")
    private String description;

    @SerializedName("classGroupId")
    private int classGroupId;

    @SerializedName("arabicName")
    private String arabicName;

    public void setAvatarName(Object avatarName){
        this.avatarName = avatarName;
    }

    public Object getAvatarName(){
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

    public void setAvatarPath(Object avatarPath){
        this.avatarPath = avatarPath;
    }

    public Object getAvatarPath(){
        return avatarPath;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setClassGroupId(int classGroupId){
        this.classGroupId = classGroupId;
    }

    public int getClassGroupId(){
        return classGroupId;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }
}
