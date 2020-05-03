package com.io.bookstores.model.guestModel;

import com.google.gson.annotations.SerializedName;

public class GuestDetailResponseModel{

	@SerializedName("data")
	private GuestDetailDataModel data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(GuestDetailDataModel data){
		this.data = data;
	}

	public GuestDetailDataModel getData(){
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