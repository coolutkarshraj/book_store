package com.io.bookstores.model.schoolbooksModel;

import com.google.gson.annotations.SerializedName;

public class ClassGroup{

	@SerializedName("name")
	private String name;

	@SerializedName("classGroupId")
	private int classGroupId;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setClassGroupId(int classGroupId){
		this.classGroupId = classGroupId;
	}

	public int getClassGroupId(){
		return classGroupId;
	}


}