package com.io.bookstores.listeners;

import com.io.bookstores.model.adminResponseModel.AddBookResponseModel;

import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiImage {

   @Multipart
    @POST("book/add/")
   Callback<AddBookResponseModel> uploadImage(
            @Header("Authorization") String authHeader,
            @Header("Role") String role,
            @Part("avatar\"; fileName=\"myFile.png\" ") RequestBody image,
            @Part("name") RequestBody name,
            @Part("categoryId") RequestBody catId,
            @Part("description") RequestBody desc,
            @Part("price") RequestBody price,
            @Part("quantity") RequestBody quantity);

}
