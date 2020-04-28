package com.io.bookstores.model.schoolDetial;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("avatarName")
	private String avatarName;

	@SerializedName("address")
	private Address address;

	@SerializedName("addedBy")
	private AddedBy addedBy;

	@SerializedName("schoolId")
	private int schoolId;

	@SerializedName("avatarPath")
	private String avatarPath;

	@SerializedName("classThrough")
	private List<ClassThroughItem> classThrough;

	@SerializedName("description")
	private String description;

	@SerializedName("schoolName")
	private String schoolName;

	@SerializedName("addedById")
	private int addedById;

	@SerializedName("addressId")
	private int addressId;

	public void setAvatarName(String avatarName){
		this.avatarName = avatarName;
	}

	public String getAvatarName(){
		return avatarName;
	}

	public void setAddress(Address address){
		this.address = address;
	}

	public Address getAddress(){
		return address;
	}

	public void setAddedBy(AddedBy addedBy){
		this.addedBy = addedBy;
	}

	public AddedBy getAddedBy(){
		return addedBy;
	}

	public void setSchoolId(int schoolId){
		this.schoolId = schoolId;
	}

	public int getSchoolId(){
		return schoolId;
	}

	public void setAvatarPath(String avatarPath){
		this.avatarPath = avatarPath;
	}

	public String getAvatarPath(){
		return avatarPath;
	}

	public void setClassThrough(List<ClassThroughItem> classThrough){
		this.classThrough = classThrough;
	}

	public List<ClassThroughItem> getClassThrough(){
		return classThrough;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setSchoolName(String schoolName){
		this.schoolName = schoolName;
	}

	public String getSchoolName(){
		return schoolName;
	}

	public void setAddedById(int addedById){
		this.addedById = addedById;
	}

	public int getAddedById(){
		return addedById;
	}

	public void setAddressId(int addressId){
		this.addressId = addressId;
	}

	public int getAddressId(){
		return addressId;
	}


}