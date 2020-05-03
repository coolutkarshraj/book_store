package com.io.bookstores.model.guestCoursedModel;

public class DataItem{
	private String createdDate;
	private Course course;
	private int enrollId;
	private int guestId;
	private int courseId;

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setCourse(Course course){
		this.course = course;
	}

	public Course getCourse(){
		return course;
	}

	public void setEnrollId(int enrollId){
		this.enrollId = enrollId;
	}

	public int getEnrollId(){
		return enrollId;
	}

	public void setGuestId(int guestId){
		this.guestId = guestId;
	}

	public int getGuestId(){
		return guestId;
	}

	public void setCourseId(int courseId){
		this.courseId = courseId;
	}

	public int getCourseId(){
		return courseId;
	}
}
