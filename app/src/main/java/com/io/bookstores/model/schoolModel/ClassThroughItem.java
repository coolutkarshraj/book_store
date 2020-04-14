package com.io.bookstores.model.schoolModel;

import com.google.gson.annotations.SerializedName;

public class ClassThroughItem{

	@SerializedName("createdDate")
	private String createdDate;

	@SerializedName("schoolId")
	private int schoolId;

	@SerializedName("schoolClassId")
	private int schoolClassId;

	@SerializedName("classGroupId")
	private int classGroupId;

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setSchoolId(int schoolId){
		this.schoolId = schoolId;
	}

	public int getSchoolId(){
		return schoolId;
	}

	public void setSchoolClassId(int schoolClassId){
		this.schoolClassId = schoolClassId;
	}

	public int getSchoolClassId(){
		return schoolClassId;
	}

	public void setClassGroupId(int classGroupId){
		this.classGroupId = classGroupId;
	}

	public int getClassGroupId(){
		return classGroupId;
	}


}