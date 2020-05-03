package com.io.bookstores.model.classList;

import java.util.List;

public class DataItem{
	private int classId;
	private String createdDate;
	private List<ClassCategoriesItem> classCategories;
	private ClassGroup classGroup;
	private String description;
	private String label;
	private int classGroupId;

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

	public void setClassCategories(List<ClassCategoriesItem> classCategories){
		this.classCategories = classCategories;
	}

	public List<ClassCategoriesItem> getClassCategories(){
		return classCategories;
	}

	public void setClassGroup(ClassGroup classGroup){
		this.classGroup = classGroup;
	}

	public ClassGroup getClassGroup(){
		return classGroup;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return label;
	}

	public void setClassGroupId(int classGroupId){
		this.classGroupId = classGroupId;
	}

	public int getClassGroupId(){
		return classGroupId;
	}
}