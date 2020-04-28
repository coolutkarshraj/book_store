package com.io.bookstores.model.placeOrderSchoolModel;

import com.google.gson.annotations.SerializedName;

public class OrderItemsItem{

	@SerializedName("itemId")
	private int itemId;

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}


}