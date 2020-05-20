package com.io.bookstores.model.productModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDataModel {

    @SerializedName("avatarName")
    private String avatarName;

    @SerializedName("classSubCategory")
    private String  classSubCategory;

    @SerializedName("isWishlist")
    private boolean isWishlist;

    @SerializedName("school")
    private School school;

    @SerializedName("price")
    private String price;

    @SerializedName("classProductId")
    private int classProductId;

    @SerializedName("name")
    private String name;

    @SerializedName("avatarPath")
    private String avatarPath;

    @SerializedName("classGroup")
    private ClassGroup classGroup;

    @SerializedName("productSizes")
    private List<ProductSizesItem> productSizes;

    @SerializedName("description")
    private String description;

    @SerializedName("classCategory")
    private ClassCategory classCategory;

    public void setAvatarName(String avatarName){
        this.avatarName = avatarName;
    }

    public String getAvatarName(){
        return avatarName;
    }

    public void setClassSubCategory(String classSubCategory){
        this.classSubCategory = classSubCategory;
    }

    public String getClassSubCategory(){
        return classSubCategory;
    }

    public void setIsWishlist(boolean isWishlist){
        this.isWishlist = isWishlist;
    }

    public boolean isIsWishlist(){
        return isWishlist;
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

    public void setClassProductId(int classProductId){
        this.classProductId = classProductId;
    }

    public int getClassProductId(){
        return classProductId;
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

    public void setClassGroup(ClassGroup classGroup){
        this.classGroup = classGroup;
    }

    public ClassGroup getClassGroup(){
        return classGroup;
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
}
