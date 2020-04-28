package com.io.bookstores.model.productModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductResponseModel{

	@SerializedName("data")
	private List<ProductDataModel> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(List<ProductDataModel> data){
		this.data = data;
	}

	public List<ProductDataModel> getData(){
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