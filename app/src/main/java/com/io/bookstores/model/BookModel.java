package com.io.bookstores.model;

public class BookModel {

    // private String Title;
    //  private String Category ;
    // private String Description ;
    private  int Price;
    private int Thumbnail ;

    public BookModel() {
    }

    public BookModel(int price, int thumbnail) {
        // Title = title;
        //  Category = category;
        //Description = description;
        Price = price;
        Thumbnail = thumbnail;

    }


    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
