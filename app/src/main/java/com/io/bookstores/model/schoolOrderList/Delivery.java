package com.io.bookstores.model.schoolOrderList;

import com.google.gson.annotations.SerializedName;

public class Delivery{

	@SerializedName("deliveryAddressId")
	private int deliveryAddressId;

	@SerializedName("deliveryId")
	private int deliveryId;

	@SerializedName("createdDate")
	private String createdDate;

	@SerializedName("deliveryType")
	private String deliveryType;

	@SerializedName("deliveryPrice")
	private int deliveryPrice;

	@SerializedName("deliveryStatus")
	private String deliveryStatus;

	public void setDeliveryAddressId(int deliveryAddressId){
		this.deliveryAddressId = deliveryAddressId;
	}

	public int getDeliveryAddressId(){
		return deliveryAddressId;
	}

	public void setDeliveryId(int deliveryId){
		this.deliveryId = deliveryId;
	}

	public int getDeliveryId(){
		return deliveryId;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setDeliveryType(String deliveryType){
		this.deliveryType = deliveryType;
	}

	public String getDeliveryType(){
		return deliveryType;
	}

	public void setDeliveryPrice(int deliveryPrice){
		this.deliveryPrice = deliveryPrice;
	}

	public int getDeliveryPrice(){
		return deliveryPrice;
	}

	public void setDeliveryStatus(String deliveryStatus){
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryStatus(){
		return deliveryStatus;
	}


}