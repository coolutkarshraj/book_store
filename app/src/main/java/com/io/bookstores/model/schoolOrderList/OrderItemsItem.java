package com.io.bookstores.model.schoolOrderList;

import com.google.gson.annotations.SerializedName;

public class OrderItemsItem{

	@SerializedName("pSizeId")
	private int pSizeId;

	@SerializedName("itemId")
	private int itemId;

	@SerializedName("createdDate")
	private String createdDate;

	@SerializedName("orderId")
	private int orderId;

	@SerializedName("price")
	private int price;

	@SerializedName("classProductId")
	private int classProductId;

	@SerializedName("count")
	private int count;

	@SerializedName("qrPath")
	private String qrPath;

	@SerializedName("classProduct")
	private ClassProduct classProduct;

	public void setPSizeId(int pSizeId){
		this.pSizeId = pSizeId;
	}

	public int getPSizeId(){
		return pSizeId;
	}

	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemId(){
		return itemId;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setClassProductId(int classProductId){
		this.classProductId = classProductId;
	}

	public int getClassProductId(){
		return classProductId;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setQrPath(String qrPath){
		this.qrPath = qrPath;
	}

	public String getQrPath(){
		return qrPath;
	}

	public void setClassProduct(ClassProduct classProduct){
		this.classProduct = classProduct;
	}

	public ClassProduct getClassProduct(){
		return classProduct;
	}


}