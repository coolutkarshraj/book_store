package com.io.bookstores.model.schoolOrderList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("delivery")
	private Delivery delivery;

	@SerializedName("createdDate")
	private String createdDate;

	@SerializedName("schoolOrderId")
	private int schoolOrderId;

	@SerializedName("totalPrice")
	private int totalPrice;

	@SerializedName("schoolId")
	private int schoolId;

	@SerializedName("orderStatus")
	private String orderStatus;

	@SerializedName("itemPrice")
	private int itemPrice;

	@SerializedName("userId")
	private int userId;

	@SerializedName("user")
	private User user;

	@SerializedName("orderItems")
	private List<OrderItemsItem> orderItems;

	public void setDelivery(Delivery delivery){
		this.delivery = delivery;
	}

	public Delivery getDelivery(){
		return delivery;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setSchoolOrderId(int schoolOrderId){
		this.schoolOrderId = schoolOrderId;
	}

	public int getSchoolOrderId(){
		return schoolOrderId;
	}

	public void setTotalPrice(int totalPrice){
		this.totalPrice = totalPrice;
	}

	public int getTotalPrice(){
		return totalPrice;
	}

	public void setSchoolId(int schoolId){
		this.schoolId = schoolId;
	}

	public int getSchoolId(){
		return schoolId;
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

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setOrderItems(List<OrderItemsItem> orderItems){
		this.orderItems = orderItems;
	}

	public List<OrderItemsItem> getOrderItems(){
		return orderItems;
	}


}