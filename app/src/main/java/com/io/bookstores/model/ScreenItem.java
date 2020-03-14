package com.io.bookstores.model;

public class ScreenItem {

    String Title,Description;
    int ScreenImg, logo;


    public ScreenItem(String title, String description, int screenImg, int logo) {
        Title = title;
        Description = description;
        ScreenImg = screenImg;
        this.logo = logo;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getScreenImg() {
        return ScreenImg;
    }
}
