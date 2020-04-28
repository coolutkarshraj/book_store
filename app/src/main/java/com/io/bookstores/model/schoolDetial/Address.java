package com.io.bookstores.model.schoolDetial;

import com.google.gson.annotations.SerializedName;

public class Address{

	@SerializedName("country")
	private String country;

	@SerializedName("address")
	private String address;

	@SerializedName("city")
	private String city;

	@SerializedName("locality")
	private String locality;

	@SerializedName("storeId")
	private Object storeId;

	@SerializedName("long")
	private String jsonMemberLong;

	@SerializedName("addressId")
	private int addressId;

	@SerializedName("zipcode")
	private int zipcode;

	@SerializedName("createdDate")
	private String createdDate;

	@SerializedName("district")
	private String district;

	@SerializedName("schoolId")
	private Object schoolId;

	@SerializedName("instituteId")
	private Object instituteId;

	@SerializedName("state")
	private String state;

	@SerializedName("landmark")
	private String landmark;

	@SerializedName("lat")
	private String lat;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setLocality(String locality){
		this.locality = locality;
	}

	public String getLocality(){
		return locality;
	}

	public void setStoreId(Object storeId){
		this.storeId = storeId;
	}

	public Object getStoreId(){
		return storeId;
	}

	public void setJsonMemberLong(String jsonMemberLong){
		this.jsonMemberLong = jsonMemberLong;
	}

	public String getJsonMemberLong(){
		return jsonMemberLong;
	}

	public void setAddressId(int addressId){
		this.addressId = addressId;
	}

	public int getAddressId(){
		return addressId;
	}

	public void setZipcode(int zipcode){
		this.zipcode = zipcode;
	}

	public int getZipcode(){
		return zipcode;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setDistrict(String district){
		this.district = district;
	}

	public String getDistrict(){
		return district;
	}

	public void setSchoolId(Object schoolId){
		this.schoolId = schoolId;
	}

	public Object getSchoolId(){
		return schoolId;
	}

	public void setInstituteId(Object instituteId){
		this.instituteId = instituteId;
	}

	public Object getInstituteId(){
		return instituteId;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setLandmark(String landmark){
		this.landmark = landmark;
	}

	public String getLandmark(){
		return landmark;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}


}