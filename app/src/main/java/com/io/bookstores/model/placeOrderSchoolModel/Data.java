package com.io.bookstores.model.placeOrderSchoolModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("totalPrice")
	private int totalPrice;

	@SerializedName("orderId")
	private int orderId;

	@SerializedName("orderStatus")
	private String orderStatus;

	@SerializedName("itemPrice")
	private int itemPrice;

	@SerializedName("refernceId")
	private String refernceId;

	@SerializedName("orderItems")
	private List<OrderItemsItem> orderItems;

	public void setTotalPrice(int totalPrice){
		this.totalPrice = totalPrice;
	}

	public int getTotalPrice(){
		return totalPrice;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setOrderStatus(String orderStatus){
		this.orderStatus = orderStatus;
	}

	public String getOrderStatus(){
		return orderStatus;
	}

	public void setItemPrice(int itemPrice){
		this.itemPrice = itemPrice;
	}

	public int getItemPrice(){
		return itemPrice;
	}

	public void setRefernceId(String refernceId){
		this.refernceId = refernceId;
	}

	public String getRefernceId(){
		return refernceId;
	}

	public void setOrderItems(List<OrderItemsItem> orderItems){
		this.orderItems = orderItems;
	}

	public List<OrderItemsItem> getOrderItems(){
		return orderItems;
	}


}