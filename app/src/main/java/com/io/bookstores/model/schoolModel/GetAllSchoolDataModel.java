package com.io.bookstores.model.schoolModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllSchoolDataModel {

    @SerializedName("avatarName")
    private String avatarName;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("city")
    private String city;

    @SerializedName("schoolId")
    private int schoolId;

    @SerializedName("avatarPath")
    private String avatarPath;

    @SerializedName("classThrough")
    private List<ClassThroughItem> classThrough;

    @SerializedName("description")
    private String description;

    @SerializedName("state")
    private String state;

    @SerializedName("schoolName")
    private String schoolName;

    @SerializedName("addedById")
    private int addedById;

    @SerializedName("addressId")
    private int addressId;

    @SerializedName("arabicName")
    private String arabicName;

    @SerializedName("arabicDescription")
    private  String arabicDescription;

    public void setAvatarName(String avatarName){
        this.avatarName = avatarName;
    }

    public String getAvatarName(){
        return avatarName;
    }

    public void setCreatedDate(String createdDate){
        this.createdDate = createdDate;
    }

    public String getCreatedDate(){
        return createdDate;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setSchoolId(int schoolId){
        this.schoolId = schoolId;
    }

    public int getSchoolId(){
        return schoolId;
    }

    public void setAvatarPath(String avatarPath){
        this.avatarPath = avatarPath;
    }

    public String getAvatarPath(){
        return avatarPath;
    }

    public void setClassThrough(List<ClassThroughItem> classThrough){
        this.classThrough = classThrough;
    }

    public List<ClassThroughItem> getClassThrough(){
        return classThrough;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

    public void setSchoolName(String schoolName){
        this.schoolName = schoolName;
    }

    public String getSchoolName(){
        return schoolName;
    }

    public void setAddedById(int addedById){
        this.addedById = addedById;
    }

    public int getAddedById(){
        return addedById;
    }

    public void setAddressId(int addressId){
        this.addressId = addressId;
    }

    public int getAddressId(){
        return addressId;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getArabicDescription() {
        return arabicDescription;
    }

    public void setArabicDescription(String arabicDescription) {
        this.arabicDescription = arabicDescription;
    }
}
