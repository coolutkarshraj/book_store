package com.io.bookstores.model.schoolOrderList;

import com.google.gson.annotations.SerializedName;

public class ClassProduct{

	@SerializedName("classCategoryId")
	private int classCategoryId;

	@SerializedName("classProductId")
	private int classProductId;

	@SerializedName("avatarPath")
	private String avatarPath;

	@SerializedName("description")
	private String description;

	@SerializedName("type")
	private String type;

	@SerializedName("avatarName")
	private String avatarName;

	@SerializedName("createdDate")
	private String createdDate;

	@SerializedName("price")
	private String price;

	@SerializedName("schoolId")
	private int schoolId;

	@SerializedName("name")
	private String name;

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

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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