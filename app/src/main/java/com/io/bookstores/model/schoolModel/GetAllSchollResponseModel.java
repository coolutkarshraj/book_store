package com.io.bookstores.model.schoolModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetAllSchollResponseModel{

	@SerializedName("data")
	private List<GetAllSchoolDataModel> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(List<GetAllSchoolDataModel> data){
		this.data = data;
	}

	public List<GetAllSchoolDataModel> getData(){
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