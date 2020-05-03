package com.io.bookstores.model.guestCoursedModel;

import java.util.List;

public class GuestEnrolledCourseResponseModel{
	private List<DataItem> data;
	private String message;
	private boolean status;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}