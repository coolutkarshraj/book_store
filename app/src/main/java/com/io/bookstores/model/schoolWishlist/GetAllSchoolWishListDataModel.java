package com.io.bookstores.model.schoolWishlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllSchoolWishListDataModel {
    @SerializedName("classCategoryId")
    private int classCategoryId;

    @SerializedName("avatarHeight")
    private int avatarHeight;

    @SerializedName("classProductId")
    private int classProductId;

    @SerializedName("avatarPath")
    private String avatarPath;

    @SerializedName("productSizes")
    private List<ProductSizesItem> productSizes;

    @SerializedName("description")
    private String description;

    @SerializedName("classCategory")
    private ClassCategory classCategory;

    @SerializedName("type")
    private String type;

    @SerializedName("avatarName")
    private String avatarName;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("classSubCategory")
    private ClassSubCategory classSubCategory;

    @SerializedName("avatarWidth")
    private int avatarWidth;

    @SerializedName("school")
    private School school;

    @SerializedName("price")
    private String price;

    @SerializedName("schoolId")
    private int schoolId;

    @SerializedName("modifiedDate")
    private Object modifiedDate;

    @SerializedName("name")
    private String name;

    @SerializedName("classGroup")
    private ClassGroup classGroup;

    @SerializedName("id")
    private int id;

    @SerializedName("classSubCategoryId")
    private int classSubCategoryId;

    @SerializedName("classGroupId")
    private int classGroupId;

    @SerializedName("addedById")
    private int addedById;

    public void setClassCategoryId(int classCategoryId){
        this.classCategoryId = classCategoryId;
    }

    public int getClassCategoryId(){
        return classCategoryId;
    }

    public void setAvatarHeight(int avatarHeight){
        this.avatarHeight = avatarHeight;
    }

    public int getAvatarHeight(){
        return avatarHeight;
    }

    public void setClassProductId(int classProductId){
        this.classProductId = classProductId;
    }

    public int getClassProductId(){
        return classProductId;
    }

    public void setAvatarPath(String avatarPath){
        this.avatarPath = avatarPath;
    }

    public String getAvatarPath(){
        return avatarPath;
    }

    public void setProductSizes(List<ProductSizesItem> productSizes){
        this.productSizes = productSizes;
    }

    public List<ProductSizesItem> getProductSizes(){
        return productSizes;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setClassCategory(ClassCategory classCategory){
        this.classCategory = classCategory;
    }

    public ClassCategory getClassCategory(){
        return classCategory;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

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

    public void setClassSubCategory(ClassSubCategory classSubCategory){
        this.classSubCategory = classSubCategory;
    }

    public ClassSubCategory getClassSubCategory(){
        return classSubCategory;
    }

    public void setAvatarWidth(int avatarWidth){
        this.avatarWidth = avatarWidth;
    }

    public int getAvatarWidth(){
        return avatarWidth;
    }

    public void setSchool(School school){
        this.school = school;
    }

    public School getSchool(){
        return school;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public void setSchoolId(int schoolId){
        this.schoolId = schoolId;
    }

    public int getSchoolId(){
        return schoolId;
    }

    public void setModifiedDate(Object modifiedDate){
        this.modifiedDate = modifiedDate;
    }

    public Object getModifiedDate(){
        return modifiedDate;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setClassGroup(ClassGroup classGroup){
        this.classGroup = classGroup;
    }

    public ClassGroup getClassGroup(){
        return classGroup;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setClassSubCategoryId(int classSubCategoryId){
        this.classSubCategoryId = classSubCategoryId;
    }

    public int getClassSubCategoryId(){
        return classSubCategoryId;
    }

    public void setClassGroupId(int classGroupId){
        this.classGroupId = classGroupId;
    }

    public int getClassGroupId(){
        return classGroupId;
    }

    public void setAddedById(int addedById){
        this.addedById = addedById;
    }

    public int getAddedById(){
        return addedById;
    }
}
