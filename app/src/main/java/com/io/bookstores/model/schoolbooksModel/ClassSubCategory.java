package com.io.bookstores.model.schoolbooksModel;

import com.google.gson.annotations.SerializedName;

public class ClassSubCategory{

	@SerializedName("name")
	private String name;

	@SerializedName("classSubCategoryId")
	private int classSubCategoryId;

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


}