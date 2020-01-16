package com.io.bookstore.model.addAddressResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("orderItemId")
    @Expose
    private Integer orderItemId;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("book")
    @Expose
    private Book book;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
