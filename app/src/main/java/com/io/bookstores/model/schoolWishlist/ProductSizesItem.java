package com.io.bookstores.model.schoolWishlist;

import com.google.gson.annotations.SerializedName;

public class ProductSizesItem{

	@SerializedName("createdDate")
	private String createdDate;

	@SerializedName("productSizeId")
	private int productSizeId;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("classProductId")
	private int classProductId;

	@SerializedName("modifiedDate")
	private Object modifiedDate;

	@SerializedName("minSize")
	private int minSize;

	@SerializedName("maxSize")
	private int maxSize;

	@SerializedName("id")
	private int id;

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setProductSizeId(int productSizeId){
		this.productSizeId = productSizeId;
	}

	public int getProductSizeId(){
		return productSizeId;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setClassProductId(int classProductId){
		this.classProductId = classProductId;
	}

	public int getClassProductId(){
		return classProductId;
	}

	public void setModifiedDate(Object modifiedDate){
		this.modifiedDate = modifiedDate;
	}

	public Object getModifiedDate(){
		return modifiedDate;
	}

	public void setMinSize(int minSize){
		this.minSize = minSize;
	}

	public int getMinSize(){
		return minSize;
	}

	public void setMaxSize(int maxSize){
		this.maxSize = maxSize;
	}

	public int getMaxSize(){
		return maxSize;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}


}