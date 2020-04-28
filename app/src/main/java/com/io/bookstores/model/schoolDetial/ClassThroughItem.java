package com.io.bookstores.model.schoolDetial;

import com.google.gson.annotations.SerializedName;

public class ClassThroughItem{

	@SerializedName("createdDate")
	private String createdDate;

	@SerializedName("schoolId")
	private int schoolId;

	@SerializedName("classGroup")
	private ClassGroup classGroup;

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

	public void setClassGroup(ClassGroup classGroup){
		this.classGroup = classGroup;
	}

	public ClassGroup getClassGroup(){
		return classGroup;
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