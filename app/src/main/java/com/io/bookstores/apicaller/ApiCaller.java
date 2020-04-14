package com.io.bookstores.apicaller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.io.bookstores.model.LogoutResponseModel;
import com.io.bookstores.model.PlaceOrderModel.OrderModel;
import com.io.bookstores.model.addAddressResponseModel.AddAddressResponseModel;
import com.io.bookstores.model.addAddressResponseModel.EditBookResponseModel;
import com.io.bookstores.model.addAddressResponseModel.GetAddressListResponseModel;
import com.io.bookstores.model.addAddressResponseModel.GetAdminOrderListResponseModel;
import com.io.bookstores.model.adminResponseModel.AddBookResponseModel;
import com.io.bookstores.model.adminResponseModel.AdminBookListResponseModel;
import com.io.bookstores.model.adminResponseModel.DeleteBookResponseModel;
import com.io.bookstores.model.bookListModel.BookListModel;
import com.io.bookstores.model.categoryModel.CategoryModel;
import com.io.bookstores.model.changePasswordOtpModel.ChangePasswordVerifyOtpModel;
import com.io.bookstores.model.classModel.ClassCategoryModel;
import com.io.bookstores.model.classModel.ClassResponseModel;
import com.io.bookstores.model.contactUs.AdsResponseModel;
import com.io.bookstores.model.contactUs.ContactUsResponseModel;
import com.io.bookstores.model.contactUs.UpdateDeviceToken;
import com.io.bookstores.model.courseModel.CourseDetialResponseModel;
import com.io.bookstores.model.courseModel.CourseResponseModel;
import com.io.bookstores.model.courseModel.EnrollCourseResponseModel;
import com.io.bookstores.model.courseModel.EnrolledCourseListResponseModel;
import com.io.bookstores.model.deleteAddressResponseModel.DeleteAddressResponseModel;
import com.io.bookstores.model.deliveryPriceModel.DeliveryResponseModel;
import com.io.bookstores.model.dilvery.DilveryAdressResponseModel;
import com.io.bookstores.model.dilvery.GetDPriceResponseModel;
import com.io.bookstores.model.editProfileResponseModel.EditProfileResponseModel;
import com.io.bookstores.model.filterStore.FilterStoreResponseModel;
import com.io.bookstores.model.getAddressResponseModel.AddressResponseModel;
import com.io.bookstores.model.getAllOrder.QrResponseModel;
import com.io.bookstores.model.getProfileResponseModel.GetProfileResponseModel;
import com.io.bookstores.model.guestModel.GuestEnrollCourseResponseModel;
import com.io.bookstores.model.guestModel.GuestResponseModel;
import com.io.bookstores.model.insituteModel.InsituiteResponseModel;
import com.io.bookstores.model.insituteModel.TrendingInstituteResponseModel;
import com.io.bookstores.model.instituteDetial.InsituiteDetialResponseModel;
import com.io.bookstores.model.loginModel.LoginModel;
import com.io.bookstores.model.myOrder.MyOrderResponseModel;
import com.io.bookstores.model.orderModel.OrderStatusChangeResponseModel;
import com.io.bookstores.model.registerModel.RegisterModel;
import com.io.bookstores.model.schoolModel.GetAllSchoolResponseModel;
import com.io.bookstores.model.sliderAdModel.AdModel;
import com.io.bookstores.model.store.EditStoreDetialResponseModel;
import com.io.bookstores.model.store.StoreDetailResponseModel;
import com.io.bookstores.model.storeDetailsModel.StoreDetailsModel;
import com.io.bookstores.model.storeModel.StoreModel;
import com.io.bookstores.model.updateAddResponseModel.UpdateAddResponseModel;
import com.io.bookstores.model.updatePasswordModel.UpdatePasswordModel;
import com.io.bookstores.model.verifyOtpModel.VerifyOtpModel;
import com.io.bookstores.model.wishlistModel.AddorRemoveWishlistResponseModel;
import com.io.bookstores.model.wishlistModel.GetWishlistResponseModel;
import com.io.bookstores.utility.UrlLocator;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.FilePart;
import com.koushikdutta.async.http.body.Part;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.io.bookstores.localStorage.LocalStorage.token;


public class ApiCaller {

    /* -----------------------------------------------------Registration api------------------------------------------------------*/

    public static void registerCustomer(Activity activity, String url, String email,
                                        String phone, String password, String firstname,
                                        String address, final FutureCallback<RegisterModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                /* .uploadProgressHandler(new ProgressCallback() {
                     @Override
                     public void onProgress(long uploaded, long totaadl) {
                         int percent = (int) (uploaded * 100 / total);
                     }
                 })
                 .setTimeout(60 * 60 * 1000)*/
                .setMultipartParameter("name", firstname)
                .setMultipartParameter("email", email)
                .setMultipartParameter("phone", phone)
                .setMultipartParameter("avatar", "")
                .setMultipartParameter("password", password)
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


    public static void addAddress(Activity activity, String url, String name, String address, String addresstype, String city, String state,
                                  int zipcode, String locality, String country, String code, String landmark, String token, String distict, final FutureCallback<AddAddressResponseModel> apiCallBack) {

        final JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("address", address);
        json.addProperty("addresstype", addresstype);
        json.addProperty("phonenum", 0000000000);
        json.addProperty("city", city);
        json.addProperty("state", state);
        json.addProperty("country", country);
        json.addProperty("landmark", landmark);
        json.addProperty("zipcode", zipcode);
        json.addProperty("district", distict);
        json.addProperty("locality", locality);
        json.addProperty("code", code);
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

    public static void verifyOTP(Context activity, String url, int otp, String userid,
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

    public static void getBookModel(Activity activity, String url, String sId, String cId, String name, String token,
                                    final FutureCallback<BookListModel> apiCallback) {

        if (token == "" || token == null) {
            final Gson gson = new Gson();
            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .setHeader("SkipAuth", "true")
                    .noCache()
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            BookListModel featuredProductResponseModel = gson.fromJson(result, BookListModel.class);
                            apiCallback.onCompleted(e, featuredProductResponseModel);
                        }
                    });
        } else {
            final Gson gson = new Gson();
            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .setHeader("Authorization", "Bearer " + token)
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

    }

    /* ------------------------------------------------------ Store detail Model ------------------------------------------------------*/

    public static void getStoreDetails(Activity activity, String url, String storeId, String name,
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
                        if (e != null) {

                            return;
                        }
                        StoreModel ourBrandsResponseModel = gson.fromJson(result, StoreModel.class);
                        apiCallBack.onCompleted(e, ourBrandsResponseModel);
                    }
                });
    }

    /*-------------------------------------------------- Checkout(Proced order) --------------------------------------------------------)*/

    public static void procedorder(Activity activity, String url, JsonObject jsonObject, String token, final FutureCallback<OrderModel> apiCallBack) {


        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        OrderModel placeOrderResponseModel = gson.fromJson(result, OrderModel.class);
                        apiCallBack.onCompleted(e, placeOrderResponseModel);
                    }
                });

    }

    /*-------------------------------------------------- (Get order) --------------------------------------------------------)*/

    public static void getOrder(Activity activity, String url, String token, final FutureCallback<MyOrderResponseModel> apiCallBack) {


        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        MyOrderResponseModel placeOrderResponseModel = gson.fromJson(result, MyOrderResponseModel.class);
                        apiCallBack.onCompleted(e, placeOrderResponseModel);
                    }
                });

    }

    public static void getInstuteDetial(Activity activity, String url,
                                        final FutureCallback<InsituiteDetialResponseModel> apiCallBack) {


        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()

                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        InsituiteDetialResponseModel placeOrderResponseModel = gson.fromJson(result, InsituiteDetialResponseModel.class);
                        apiCallBack.onCompleted(e, placeOrderResponseModel);
                    }
                });

    }

    /*------------------------------------------------------- serach product api --------------------------------------------------*/

    public static void getfilterData(Activity activity, String url, String city,
                                     final FutureCallback<FilterStoreResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        final JsonObject json = new JsonObject();
        json.addProperty("address.district", city);
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        FilterStoreResponseModel searchResponseModel = gson.fromJson(result, FilterStoreResponseModel.class);
                        apiCallBack.onCompleted(e, searchResponseModel);
                    }
                });
    }

    public static void getUserSavedAddressList(Activity activity, String url, String token,
                                               final FutureCallback<GetAddressListResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        GetAddressListResponseModel deliveryAddress = gson.fromJson(result, GetAddressListResponseModel.class);
                        apiCallBack.onCompleted(e, deliveryAddress);
                    }
                });
    }

    /*------------------------------------- add or remove product wishlist ------------------------------*/

    public static void addOrRemoveWishList(Context activity, String url,
                                           String token, final FutureCallback<AddorRemoveWishlistResponseModel> apiCallBack) {


        final Gson gson = new Gson();
        Ion.with(activity)
                .load("POST ", UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        AddorRemoveWishlistResponseModel updateAddResponseModel = gson.fromJson(result, AddorRemoveWishlistResponseModel.class);
                        apiCallBack.onCompleted(e, updateAddResponseModel);
                    }
                });
    }

    public static void getWishList(Activity activity, String url, String token,
                                   final FutureCallback<GetWishlistResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        GetWishlistResponseModel deliveryAddress = gson.fromJson(result, GetWishlistResponseModel.class);
                        apiCallBack.onCompleted(e, deliveryAddress);
                    }
                });
    }


    public static void getAdminBookList(Activity activity, String url, String token,
                                        final FutureCallback<AdminBookListResponseModel> apiCallback) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        AdminBookListResponseModel featuredProductResponseModel = gson.fromJson(result, AdminBookListResponseModel.class);
                        apiCallback.onCompleted(e, featuredProductResponseModel);
                    }
                });
    }

    public static void deleteBook(Context activity, String url, String token,
                                  final FutureCallback<DeleteBookResponseModel> apiCallback) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .setHeader("Role", "store")
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        DeleteBookResponseModel featuredProductResponseModel = gson.fromJson(result, DeleteBookResponseModel.class);
                        apiCallback.onCompleted(e, featuredProductResponseModel);
                    }
                });
    }

    public static void upload(Activity activity, String url, String author, String bookname, String bookdesc,
                              String catId, String Quantity, String amount, String token, File image,
                              final FutureCallback<AddBookResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        List<Part> files = new ArrayList();
        files.add(new FilePart("avatar", image));
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .setHeader("Role", "store")
                .addMultipartParts(files)
                .setMultipartParameter("name", bookname)
                .setMultipartParameter("author", author)
                .setMultipartParameter("categoryId", catId)
                .setMultipartParameter("description", bookdesc)
                .setMultipartParameter("price", amount)
                .setMultipartParameter("quantity", Quantity)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("Respnse is", result.toString());
                        AddBookResponseModel customerRegisterResponseModel = gson.fromJson(result, AddBookResponseModel.class);
                        apiCallBack.onCompleted(e, customerRegisterResponseModel);
                    }
                });
    }


    public static void editBookDetial(Activity activity, String url, String bookname, String bookdesc,
                                      String catId, String Quantity, String amount, String token, File image,
                                      Integer bookId, String author, final FutureCallback<EditBookResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        List<Part> files = new ArrayList();
        if (image == null) {
            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .setHeader("Authorization", "Bearer " + token)
                    .setHeader("Role", "store")
                    .setMultipartParameter("name", bookname)
                    .setMultipartParameter("author", author)
                    .setMultipartParameter("categoryId", catId)
                    .setMultipartParameter("description", bookdesc)
                    .setMultipartParameter("price", amount)
                    .setMultipartParameter("quantity", Quantity)
                    .setMultipartParameter("bookId", String.valueOf(bookId))
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            EditBookResponseModel editBookResponseModel = gson.fromJson(result, EditBookResponseModel.class);
                            apiCallBack.onCompleted(e, editBookResponseModel);
                        }
                    });
        } else {
            files.add(new FilePart("avatar", image));
            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .setHeader("Authorization", "Bearer " + token)
                    .setHeader("Role", "store")
                    .addMultipartParts(files)
                    .setMultipartParameter("name", bookname)
                    .setMultipartParameter("author", author)
                    .setMultipartParameter("categoryId", catId)
                    .setMultipartParameter("description", bookdesc)
                    .setMultipartParameter("price", amount)
                    .setMultipartParameter("quantity", Quantity)
                    .setMultipartParameter("bookId", String.valueOf(bookId))
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            EditBookResponseModel editBookResponseModel = gson.fromJson(result, EditBookResponseModel.class);
                            apiCallBack.onCompleted(e, editBookResponseModel);
                        }
                    });
        }

    }
    /* get admin order*/

    public static void getAdminOrder(Activity activity, String url, String token, final FutureCallback<GetAdminOrderListResponseModel> apiCallBack) {


        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .setHeader("Role", "store")
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        GetAdminOrderListResponseModel placeOrderResponseModel = gson.fromJson(result, GetAdminOrderListResponseModel.class);
                        apiCallBack.onCompleted(e, placeOrderResponseModel);
                    }
                });

    }

    public static void editProfileUser(Activity activity, String url, String name, String address,
                                       String phone, String token, File image,
                                       final FutureCallback<EditProfileResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        List<Part> files = new ArrayList();
        if (image == null) {
            image = new File("");
            files.add(new FilePart("avatar", image));
            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .setHeader("Authorization", "Bearer " + token)
                    .setMultipartParameter("name", name)
                    .setMultipartParameter("address", address)
                    .setMultipartParameter("phone", phone)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            EditProfileResponseModel editProfileResponseModel = gson.fromJson(result, EditProfileResponseModel.class);
                            apiCallBack.onCompleted(e, editProfileResponseModel);
                        }
                    });
        } else {
            files.add(new FilePart("avatar", image));
            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .setHeader("Authorization", "Bearer " + token)
                    .addMultipartParts(files)
                    .setMultipartParameter("name", name)
                    .setMultipartParameter("address", address)
                    .setMultipartParameter("phone", phone)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            EditProfileResponseModel editProfileResponseModel = gson.fromJson(result, EditProfileResponseModel.class);
                            apiCallBack.onCompleted(e, editProfileResponseModel);
                        }
                    });
        }


    }

    public static void getStoreDetial(Activity activity, String url,
                                      final FutureCallback<StoreDetailResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        StoreDetailResponseModel storeDetailResponseModel = gson.fromJson(result, StoreDetailResponseModel.class);
                        apiCallBack.onCompleted(e, storeDetailResponseModel);
                    }
                });

    }

    public static void editstoreDetial(Activity activity, String url, String name, String des,
                                       String phone, String token, File image, String address,
                                       final FutureCallback<EditStoreDetialResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        List<Part> files = new ArrayList();
        if (image == null) {
            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .setHeader("Authorization", "Bearer " + token)
                    .setHeader("Role", "store")
                    .setMultipartParameter("name", name)
                    .setMultipartParameter("description", des)
                    .setMultipartParameter("phone", phone)
                    .setMultipartParameter("address", address)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            EditStoreDetialResponseModel editStoreDetialResponseModel = gson.fromJson(result, EditStoreDetialResponseModel.class);
                            apiCallBack.onCompleted(e, editStoreDetialResponseModel);
                        }
                    });
        } else {
            files.add(new FilePart("avatar", image));
            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .setHeader("Authorization", "Bearer " + token)
                    .setHeader("Role", "store")
                    .addMultipartParts(files)
                    .setMultipartParameter("name", name)
                    .setMultipartParameter("description", des)
                    .setMultipartParameter("phone", phone)
                    .setMultipartParameter("address", address)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            EditStoreDetialResponseModel editStoreDetialResponseModel = gson.fromJson(result, EditStoreDetialResponseModel.class);
                            apiCallBack.onCompleted(e, editStoreDetialResponseModel);
                        }
                    });
        }


    }

    public static void updateOrderStaus(Activity activity, String url,
                                        int orderId, String status, String token,
                                        final FutureCallback<OrderStatusChangeResponseModel> apiCallBack) {

        final JsonObject json = new JsonObject();
        json.addProperty("orderId", orderId);
        json.addProperty("status", status);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .setHeader("Role", "store")
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        OrderStatusChangeResponseModel orderStatusChangeResponseModel = gson.fromJson(result, OrderStatusChangeResponseModel.class);
                        apiCallBack.onCompleted(e, orderStatusChangeResponseModel);
                    }
                });
    }


    public static void getInstiuiteList(Activity activity, String url,
                                        final FutureCallback<InsituiteResponseModel> apiCallback) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        InsituiteResponseModel insituiteResponseModel = gson.fromJson(result, InsituiteResponseModel.class);
                        apiCallback.onCompleted(e, insituiteResponseModel);
                    }
                });
    }

    public static void getTrendingInstiuiteList(Activity activity, String url,
                                                final FutureCallback<TrendingInstituteResponseModel> apiCallback) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        TrendingInstituteResponseModel trendingInstituteResponseModel = gson.fromJson(result, TrendingInstituteResponseModel.class);
                        apiCallback.onCompleted(e, trendingInstituteResponseModel);
                    }
                });
    }

    public static void getCourseList(Activity activity, String url,
                                     final FutureCallback<CourseResponseModel> apiCallback) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        CourseResponseModel courseResponseModel = gson.fromJson(result, CourseResponseModel.class);
                        apiCallback.onCompleted(e, courseResponseModel);
                    }
                });
    }

    public static void enrollCourse(Activity activity, String url, String token,
                                    final FutureCallback<EnrollCourseResponseModel> apiCallback) {
        final JsonObject json = new JsonObject();
        final Gson gson = new Gson();
        Ion.with(activity)
                .load("POST", UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        EnrollCourseResponseModel enrollCourseResponseModel = gson.fromJson(result, EnrollCourseResponseModel.class);
                        apiCallback.onCompleted(e, enrollCourseResponseModel);
                    }
                });
    }

    public static void enrollCourseList(Activity activity, String url, String token,
                                        final FutureCallback<EnrolledCourseListResponseModel> apiCallback) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        EnrolledCourseListResponseModel enrolledCourseListResponseModel = gson.fromJson(result, EnrolledCourseListResponseModel.class);
                        apiCallback.onCompleted(e, enrolledCourseListResponseModel);
                    }
                });
    }

    public static void courseDetial(Activity activity, String url,
                                    final FutureCallback<CourseDetialResponseModel> apiCallback) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        CourseDetialResponseModel courseDetialResponseModel = gson.fromJson(result, CourseDetialResponseModel.class);
                        apiCallback.onCompleted(e, courseDetialResponseModel);
                    }
                });
    }

    public static void getAdModel(Activity activity, String url,
                                  final FutureCallback<AdModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        AdModel getSliderAd = gson.fromJson(result, AdModel.class);
                        apiCallBack.onCompleted(e, getSliderAd);
                    }
                });
    }

    public static void logout(Activity activity, String url,
                              final FutureCallback<LogoutResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        LogoutResponseModel logoutResponseModel = gson.fromJson(result, LogoutResponseModel.class);
                        apiCallBack.onCompleted(e, logoutResponseModel);
                    }
                });

    }

    public static void getdistic(Activity activity, String url,
                                 final FutureCallback<DilveryAdressResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        DilveryAdressResponseModel dilveryAddressDataModel = gson.fromJson(result, DilveryAdressResponseModel.class);
                        apiCallBack.onCompleted(e, dilveryAddressDataModel);
                    }
                });

    }

    public static void orderPrice(Activity activity, String url, String token,
                                  final FutureCallback<GetDPriceResponseModel> apiCallBack) {
        final JsonObject json = new JsonObject();
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        GetDPriceResponseModel dilveryAddressDataModel = gson.fromJson(result, GetDPriceResponseModel.class);
                        apiCallBack.onCompleted(e, dilveryAddressDataModel);
                    }
                });

    }

    public static void contactUs(Activity activity, String url, String token,
                                 String email, String title, String body,
                                 final FutureCallback<ContactUsResponseModel> apiCallBack) {
        final JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("tittle", title);
        json.addProperty("body", body);

        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ContactUsResponseModel contactUsResponseModel = gson.fromJson(result, ContactUsResponseModel.class);
                        apiCallBack.onCompleted(e, contactUsResponseModel);
                    }
                });

    }

    public static void getAdvertisment(Activity activity, String url,
                                       final FutureCallback<AdsResponseModel> apiCallBack) {

        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        AdsResponseModel adsResponseModel = gson.fromJson(result, AdsResponseModel.class);
                        apiCallBack.onCompleted(e, adsResponseModel);
                    }
                });

    }

    public static void updateDevice(Activity activity, String url, String token,
                                    String devicetoken,
                                    final FutureCallback<UpdateDeviceToken> apiCallBack) {
        final JsonObject json = new JsonObject();
        json.addProperty("registrationToken", devicetoken);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        UpdateDeviceToken updateDeviceToken = gson.fromJson(result, UpdateDeviceToken.class);
                        apiCallBack.onCompleted(e, updateDeviceToken);
                    }
                });

    }

    public static void guestApi(Activity activity, String url, String name, String email, String Phone,
                                final FutureCallback<GuestResponseModel> apiCallBack) {
        final JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("email ", email);
        json.addProperty("phone", Phone);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        GuestResponseModel guestResponseModel = gson.fromJson(result, GuestResponseModel.class);
                        apiCallBack.onCompleted(e, guestResponseModel);
                    }
                });
    }

    public static void guestEnrollCourse(Activity activity, String url, int cId, Long gId,
                                         final FutureCallback<GuestEnrollCourseResponseModel> apiCallBack) {

        final JsonObject json = new JsonObject();
        json.addProperty("cId", cId);
        json.addProperty("gId", gId);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        GuestEnrollCourseResponseModel guestEnrollCourseResponseModel = gson.fromJson(result, GuestEnrollCourseResponseModel.class);
                        apiCallBack.onCompleted(e, guestEnrollCourseResponseModel);
                    }
                });
    }

    public static void qrCreator(Activity activity, String url, File file, String parmeter, String parmId, Long OId
            , final FutureCallback<QrResponseModel> apiCallBack) {

        final Gson gson = new Gson();
        List<Part> files = new ArrayList();

        files.add(new FilePart("avatar", file));
        Ion.with(activity)
                .load("POST", UrlLocator.getFinalUrl(url))
                .addMultipartParts(files)
                .setMultipartParameter(parmeter, parmId)
                .setMultipartParameter("oId", String.valueOf(OId))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        QrResponseModel qrResponseModel = gson.fromJson(result, QrResponseModel.class);
                        apiCallBack.onCompleted(e, qrResponseModel);
                    }
                });
    }


    /*------------------------------------------------------- get schools Api------------------------------------------------------------*/

    public static void getSchoolApi(Activity activity, String url,
                                    final FutureCallback<GetAllSchoolResponseModel> apiCallBack) {

        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()

                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        GetAllSchoolResponseModel allSchollResponseModel = gson.fromJson(result, GetAllSchoolResponseModel.class);
                        apiCallBack.onCompleted(e, allSchollResponseModel);
                    }
                });
    }

    /*------------------------------------------------------- get class group Api------------------------------------------------------------*/

    public static void getclassApi(Activity activity, String url, String schoolId,
                                   final FutureCallback<ClassResponseModel> apiCallBack) {
        final JsonObject json = new JsonObject();
        json.addProperty("schoolId", schoolId);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ClassResponseModel classResponseModel = gson.fromJson(result, ClassResponseModel.class);
                        apiCallBack.onCompleted(e, classResponseModel);
                    }
                });
    }

    /*------------------------------------------------------- get class ategory Api------------------------------------------------------------*/

    public static void getclassCategoryApi(Activity activity, String url, String classGroupId,
                                           final FutureCallback<ClassCategoryModel> apiCallBack) {
        final JsonObject json = new JsonObject();
        json.addProperty("classGroupId", classGroupId);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load("POST",UrlLocator.getFinalUrl(url))
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ClassCategoryModel classCategoryModel = gson.fromJson(result, ClassCategoryModel.class);
                        apiCallBack.onCompleted(e, classCategoryModel);
                    }
                });
    }


}
