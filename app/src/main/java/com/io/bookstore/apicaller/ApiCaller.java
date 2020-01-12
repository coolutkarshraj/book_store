package com.io.bookstore.apicaller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.io.bookstore.model.BookModel;
import com.io.bookstore.model.FilterDetailsModel.FilterDetailsModel;
import com.io.bookstore.model.addAddressResponseModel.AddAddressResponseModel;
import com.io.bookstore.model.bookListModel.BookListModel;
import com.io.bookstore.model.categoryModel.CategoryModel;
import com.io.bookstore.model.changePasswordOtpModel.ChangePasswordVerifyOtpModel;
import com.io.bookstore.model.deleteAddressResponseModel.DeleteAddressResponseModel;
import com.io.bookstore.model.deliveryPriceModel.DeliveryResponseModel;
import com.io.bookstore.model.editProfileResponseModel.EditProfileResponseModel;
import com.io.bookstore.model.filterByAddress.FilterAddressModel;
import com.io.bookstore.model.getAddressResponseModel.AddressResponseModel;
import com.io.bookstore.model.getProfileResponseModel.GetProfileResponseModel;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.model.orderModel.OrderModel;
import com.io.bookstore.model.registerModel.RegisterModel;
import com.io.bookstore.model.storeDetailsModel.StoreDetailsModel;
import com.io.bookstore.model.storeModel.StoreModel;
import com.io.bookstore.model.updateAddResponseModel.UpdateAddResponseModel;
import com.io.bookstore.model.updatePasswordModel.UpdatePasswordModel;
import com.io.bookstore.model.verifyOtpModel.VerifyOtpModel;
import com.io.bookstore.utility.UrlLocator;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ApiCaller {

    /* -----------------------------------------------------Registration api------------------------------------------------------*/

    public static void registerCustomer(Activity activity, String url, String name, String email,
                                        String phone, String password,String firstname,String lastname,
                                        String address, final FutureCallback<RegisterModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .uploadProgressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long uploaded, long total) {
                        int percent = (int) (uploaded * 100 / total);
                    }
                })
                .setTimeout(60 * 60 * 1000)
                .setMultipartParameter("username", name)
                .setMultipartParameter("email", email)
                .setMultipartParameter("phone", phone)
                .setMultipartParameter("password", password)
                .setMultipartParameter("firstname", firstname)
                .setMultipartParameter("lastname", lastname)
                .setMultipartParameter("address", address)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        RegisterModel customerRegisterResponseModel = gson.fromJson(result, RegisterModel.class);
                       apiCallBack.onCompleted(e, customerRegisterResponseModel);
                    }
                });
    }



    /*-------------------------------------------------------- Login Api---------------------------------------------------------*/

    public static void loginCustomer(Activity activity, String url, String username, String password,
                                     final FutureCallback<LoginModel> apiCallBack) {
        JsonObject json = new JsonObject();
        json.addProperty("username", username);
        json.addProperty("password", password);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        LoginModel loginResponseModel = gson.fromJson(result, LoginModel.class);
                        apiCallBack.onCompleted(e, loginResponseModel);
                    }
                });
    }


    /*-------------------------------------------------------- forgot password Api---------------------------------------------------------*/

    public static void forgotPassword(Activity activity, String url, String email,
                                      final FutureCallback<ChangePasswordVerifyOtpModel> apiCallBack) {
        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ChangePasswordVerifyOtpModel forgotPasswordResponseModel = gson.fromJson(result, ChangePasswordVerifyOtpModel.class);
                        apiCallBack.onCompleted(e, forgotPasswordResponseModel);
                    }
                });
    }
    /*-------------------------------------------------------- change password Api---------------------------------------------------------*/

    public static void changePassword(Activity activity, String url, String oldPassword, String newPassowrd, String token,
                                      final FutureCallback<UpdatePasswordModel> apiCallBack) {
        JsonObject json = new JsonObject();
        json.addProperty("oldPassword", oldPassword);
        json.addProperty("newPassword", newPassowrd);


        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        UpdatePasswordModel changePasswordResponseModel = gson.fromJson(result, UpdatePasswordModel.class);
                        apiCallBack.onCompleted(e, changePasswordResponseModel);
                    }
                });
    }

    /*-------------------------------------------------get profile--------------------------------------------------------*/

    public static void getUserProfile(Activity activity, String url, String token,
                                      final FutureCallback<GetProfileResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        GetProfileResponseModel getProfileResponseModel = gson.fromJson(result, GetProfileResponseModel.class);
                        apiCallBack.onCompleted(e, getProfileResponseModel);
                    }
                });
    }

    /* -----------------------------------------------------edit profile api-------------------------------------------*/

    public static void editProfile(Activity activity, String url, String firstName, String lastName, String Address,
                                   String avatar, String username, String mobile, String token, String image,
                                   final FutureCallback<EditProfileResponseModel> apiCallBack) {
        final JsonObject json = new JsonObject();
        json.addProperty("firstName", firstName);
        json.addProperty("lastName", lastName);
        json.addProperty("address", Address);
        json.addProperty("avatar", avatar);
        json.addProperty("username", username);
        json.addProperty("phone", mobile);
        json.addProperty("image", image);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        EditProfileResponseModel editProfileDataModel = gson.fromJson(result, EditProfileResponseModel.class);
                        apiCallBack.onCompleted(e, editProfileDataModel);
                    }
                });

    }




    /* ------------------------------------------------------Add address api-------------------------------------------------------*/


    public static void addAddress(Activity activity, String url, int customerId, String address1, String address2, String city, String state,
                                  String pincode, String addressType, String token, final FutureCallback<AddAddressResponseModel> apiCallBack) {

        final JsonObject json = new JsonObject();
        json.addProperty("customerId", customerId);
        json.addProperty("address1", address1);
        json.addProperty("address2", address2);
        json.addProperty("city", city);
        json.addProperty("state", state);
        json.addProperty("postcode", pincode);
        json.addProperty("addressType", addressType);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        AddAddressResponseModel addAddressResponseModel = gson.fromJson(result, AddAddressResponseModel.class);
                        apiCallBack.onCompleted(e, addAddressResponseModel);
                    }
                });
    }

    /*------------------------------------------------------ get Customer Address list----------------------------------------------*/

    public static void getAddresssList(Activity activity, String url,
                                          final FutureCallback<AddressResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        AddressResponseModel getAddressResponseModel = gson.fromJson(result, AddressResponseModel.class);
                        apiCallBack.onCompleted(e, getAddressResponseModel);
                    }
                });
    }

    /*----------------------------------------------------- Update address api---------------------------------------------*/


    public static void updateAddress(Context activity, String url, int customerId, String address1, String address2, String city, String state,
                                     String pincode, String addressType, String token, final FutureCallback<UpdateAddResponseModel> apiCallBack) {

        final JsonObject json = new JsonObject();
        json.addProperty("customerId", customerId);
        json.addProperty("address1", address1);
        json.addProperty("address2", address2);
        json.addProperty("city", city);
        json.addProperty("state", state);
        json.addProperty("postcode", pincode);
        json.addProperty("addressType", addressType);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load("PUT", UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        UpdateAddResponseModel updateAddResponseModel = gson.fromJson(result, UpdateAddResponseModel.class);
                        apiCallBack.onCompleted(e, updateAddResponseModel);
                    }
                });
    }

    /* ---------------------------------------------Delete address api--------------------------------------------------------------*/

    public static void deleteAddress(Context activity, String url,
                                     String token, final FutureCallback<DeleteAddressResponseModel> apiCallBack) {


        final Gson gson = new Gson();
        Ion.with(activity)
                .load("DELETE ", UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        DeleteAddressResponseModel updateAddResponseModel = gson.fromJson(result, DeleteAddressResponseModel.class);
                        apiCallBack.onCompleted(e, updateAddResponseModel);
                    }
                });
    }



    /* --------------------------------------------------------ChangePasswordVerifyOtpModel--------------------------------------------------*/

    public static void chagePasswordOTPModel(Activity activity, String url,
                                        final FutureCallback<ChangePasswordVerifyOtpModel> apiCallBack) {

        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ChangePasswordVerifyOtpModel productDetailResponseModel = gson.fromJson(result, ChangePasswordVerifyOtpModel.class);
                        apiCallBack.onCompleted(e, productDetailResponseModel);
                    }
                });
    }

    /*----------------------------------------------------- verify OTP -------------------------------------------------------------*/

    public static void verifyOTP(Context activity, String url, int otp,String userid,
                                   final FutureCallback<VerifyOtpModel> apiCallBack) {

        final JsonObject json = new JsonObject();
        json.addProperty("otp", otp);
        json.addProperty("userid", Integer.parseInt(userid));

        final Gson gson = new Gson();
        Ion.with(activity)
                .load("POST", UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        VerifyOtpModel wishlistResponseModel = gson.fromJson(result, VerifyOtpModel.class);
                        apiCallBack.onCompleted(e, wishlistResponseModel);
                    }
                });
    }


    /* ------------------------------------------------------- get category list ---------------------------------------------*/

    public static void getCategoryModel(Activity activity, String url, String token,
                                       final FutureCallback<CategoryModel> apiCallBack) {

        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        CategoryModel getWishlistResponseModel = gson.fromJson(result, CategoryModel.class);
                        apiCallBack.onCompleted(e, getWishlistResponseModel);
                    }
                });
    }

    /* ------------------------------------------------------- get Dilevery Price---------------------------------------------*/

    public static void getDileveryPrice(Activity activity, String url,
                                        final FutureCallback<DeliveryResponseModel> apiCallBack) {

        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        DeliveryResponseModel getWishlistResponseModel = gson.fromJson(result, DeliveryResponseModel.class);
                        apiCallBack.onCompleted(e, getWishlistResponseModel);
                    }
                });
    }

    /*------------------------------------------------------ get Book Model -------------------------------------------------*/

    public static void getBookModel(Activity activity, String url,String sId,String cId,String name,
                                          final FutureCallback<BookListModel> apiCallback) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        BookListModel featuredProductResponseModel = gson.fromJson(result, BookListModel.class);
                        apiCallback.onCompleted(e, featuredProductResponseModel);
                    }
                });
    }

    /* ------------------------------------------------------ Store detail Model ------------------------------------------------------*/

    public static void getStoreDetails(Activity activity, String url,String storeId,String name,
                                     final FutureCallback<StoreDetailsModel> apiCallBack) {


        final Gson gson = new Gson();
        final JsonObject json = new JsonObject();
        json.addProperty("storeId", storeId);
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().
                setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        StoreDetailsModel todayDealsResponseModel = gson.fromJson(result, StoreDetailsModel.class);
                        apiCallBack.onCompleted(e, todayDealsResponseModel);
                    }
                });
    }

    /*------------------------------------------------------- Store Model Api------------------------------------------------------------*/

    public static void getStoreApi(Activity activity, String url,
                                   final FutureCallback<StoreModel> apiCallBack) {

        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        StoreModel ourBrandsResponseModel = gson.fromJson(result, StoreModel.class);
                        apiCallBack.onCompleted(e, ourBrandsResponseModel);
                    }
                });
    }

    /*-------------------------------------------------- Checkout(Proced order) --------------------------------------------------------)*/

    public static void procedorder(Activity activity, String url, String productDetails, String shippingFirstName, String shippingLastName,
                                   String shippingCompany, String shippingAddress_1, String shippingAddress_2, String shippingCity,
                                   String shippingPostCode, String shippingCountry, String shippingZone, String shippingAddressFormat,
                                   String phoneNumber, String emailId, String token, final FutureCallback<OrderModel> apiCallBack) {

        final JsonObject json = new JsonObject();
        json.addProperty("productDetails", productDetails);
        json.addProperty("shippingFirstName", shippingFirstName);
        json.addProperty("shippingLastName", shippingLastName);
        json.addProperty("shippingCompany", shippingCompany);
        json.addProperty("shippingAddress_1", shippingAddress_1);
        json.addProperty("shippingAddress_2", shippingAddress_2);
        json.addProperty("shippingCity", shippingCity);
        json.addProperty("shippingPostCode", shippingPostCode);
        json.addProperty("shippingCountry", shippingCountry);
        json.addProperty("shippingZone", shippingZone);
        json.addProperty("shippingAddressFormat", shippingAddressFormat);
        json.addProperty("phoneNumber", phoneNumber);
        json.addProperty("emailId", emailId);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        OrderModel placeOrderResponseModel = gson.fromJson(result, OrderModel.class);
                        apiCallBack.onCompleted(e, placeOrderResponseModel);
                    }
                });

    }



    /*------------------------------------------------------- serach product api --------------------------------------------------*/

    public static void getfilterData(Activity activity, String url,String city,
                                     final FutureCallback<FilterAddressModel> apiCallBack)  {

        final Gson gson = new Gson();
        final JsonObject json = new JsonObject();
        json.addProperty("address.city", city);
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        FilterAddressModel searchResponseModel = gson.fromJson(result, FilterAddressModel.class);
                        apiCallBack.onCompleted(e, searchResponseModel);
                    }
                });
    }



}
