package com.io.bookstores.model.guestCoursedModel;

public class Course{
	private String avatarName;
	private String courseName;
	private String createdDate;
	private int price;
	private String avatarPath;
	private int instituteId;
	private int courseId;
	private String courseDescription;

	public void setAvatarName(String avatarName){
		this.avatarName = avatarName;
	}

	public String getAvatarName(){
		return avatarName;
	}

	public void setCourseName(String courseName){
		this.courseName = courseName;
	}

	public String getCourseName(){
		return courseName;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setAvatarPath(String avatarPath){
		this.avatarPath = avatarPath;
	}

	public String getAvatarPath(){
		return avatarPath;
	}

	public void setInstituteId(int instituteId){
		this.instituteId = instituteId;
	}

	public int getInstituteId(){
		return instituteId;
	}

	public void setCourseId(int courseId){
		this.courseId = courseId;
	}

	public int getCourseId(){
		return courseId;
	}

	public void setCourseDescription(String courseDescription){
		this.courseDescription = courseDescription;
	}

	public String getCourseDescription(){
		return courseDescription;
	}
}
