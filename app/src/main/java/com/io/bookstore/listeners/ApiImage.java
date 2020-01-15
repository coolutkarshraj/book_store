package com.io.bookstore.listeners;

import com.io.bookstore.model.adminResponseModel.AddBookResponseModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiImage {

   /* @Multipart
    @POST("upload")
    fun createImage(
            @Part("avatar\"; filename=\"myfile.jpg\" ") file: RequestBody,
            @Part("name ") name: RequestBody
    ):Call<UpdateImage>*/

    @Multipart
    @POST("customer/edit-profile")
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
