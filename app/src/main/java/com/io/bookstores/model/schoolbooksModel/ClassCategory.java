package com.io.bookstores.model.schoolbooksModel;

import com.google.gson.annotations.SerializedName;

public class ClassCategory{

	@SerializedName("classCategoryId")
	private int classCategoryId;

	@SerializedName("name")
	private String name;

	public void setClassCategoryId(int classCategoryId){
		this.classCategoryId = classCategoryId;
	}

	public int getClassCategoryId(){
		return classCategoryId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}


}