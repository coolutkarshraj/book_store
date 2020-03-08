
package com.io.bookstore.model.myOrder;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class OrderItem {

    @SerializedName("book")
    private Book mBook;
    @SerializedName("bookId")
    private Long mBookId;
    @SerializedName("count")
    private Long mCount;
    @SerializedName("createdDate")
    private String mCreatedDate;
    @SerializedName("orderId")
    private Long mOrderId;
    @SerializedName("orderItemId")
    private Long mOrderItemId;
    @SerializedName("price")
    private Long mPrice;

    public Book getBook() {
        return mBook;
    }

    public void setBook(Book book) {
        mBook = book;
    }

    public Long getBookId() {
        return mBookId;
    }

    public void setBookId(Long bookId) {
        mBookId = bookId;
    }

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public Long getOrderId() {
        return mOrderId;
    }

    public void setOrderId(Long orderId) {
        mOrderId = orderId;
    }

    public Long getOrderItemId() {
        return mOrderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        mOrderItemId = orderItemId;
    }

    public Long getPrice() {
        return mPrice;
    }

    public void setPrice(Long price) {
        mPrice = price;
    }

}
