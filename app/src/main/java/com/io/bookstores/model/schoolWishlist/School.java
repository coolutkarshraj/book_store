package com.io.bookstores.model.schoolWishlist;

import com.google.gson.annotations.SerializedName;

public class School{

	@SerializedName("schoolId")
	private int schoolId;

	@SerializedName("schoolName")
	private String schoolName;

	public void setSchoolId(int schoolId){
		this.schoolId = schoolId;
	}

	public int getSchoolId(){
		return schoolId;
	}

	public void setSchoolName(String schoolName){
		this.schoolName = schoolName;
	}

	public String getSchoolName(){
		return schoolName;
	}


}