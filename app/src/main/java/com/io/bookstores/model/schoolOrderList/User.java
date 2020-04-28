package com.io.bookstores.model.schoolOrderList;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}