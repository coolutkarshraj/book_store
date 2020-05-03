package com.io.bookstores.model.classList;

public class ClassCategoriesItem{
	private String avatarName;
	private int classCategoryId;
	private int classId;
	private String createdDate;
	private String name;
	private String avatarPath;
	private String description;

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

	public void setClassId(int classId){
		this.classId = classId;
	}

	public int getClassId(){
		return classId;
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
}
