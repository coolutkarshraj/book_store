package com.io.bookstore.apicaller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.io.bookstore.model.PlaceOrderModel.OrderModel;
import com.io.bookstore.model.addAddressResponseModel.AddAddressResponseModel;
import com.io.bookstore.model.addAddressResponseModel.EditBookDataModel;
import com.io.bookstore.model.addAddressResponseModel.EditBookResponseModel;
import com.io.bookstore.model.addAddressResponseModel.GetAddressListResponseModel;
import com.io.bookstore.model.addAddressResponseModel.GetAdminOrderListResponseModel;
import com.io.bookstore.model.adminResponseModel.AddBookResponseModel;
import com.io.bookstore.model.adminResponseModel.AdminBookListResponseModel;
import com.io.bookstore.model.adminResponseModel.DeleteBookResponseModel;
import com.io.bookstore.model.bookListModel.BookListModel;
import com.io.bookstore.model.categoryModel.CategoryModel;
import com.io.bookstore.model.changePasswordOtpModel.ChangePasswordVerifyOtpModel;
import com.io.bookstore.model.courseModel.CourseDetialResponseModel;
import com.io.bookstore.model.courseModel.CourseResponseModel;
import com.io.bookstore.model.courseModel.EnrollCourseResponseModel;
import com.io.bookstore.model.courseModel.EnrolledCourseListResponseModel;
import com.io.bookstore.model.deleteAddressResponseModel.DeleteAddressResponseModel;
import com.io.bookstore.model.deliveryPriceModel.DeliveryResponseModel;
import com.io.bookstore.model.editProfileResponseModel.EditProfileResponseModel;
import com.io.bookstore.model.filterByAddress.FilterAddressModel;
import com.io.bookstore.model.getAddressResponseModel.AddressResponseModel;
import com.io.bookstore.model.getAllOrder.GetAllOrder;
import com.io.bookstore.model.getProfileResponseModel.GetProfileResponseModel;
import com.io.bookstore.model.insituteModel.InsituiteResponseModel;
import com.io.bookstore.model.insituteModel.TrendingInstituteResponseModel;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.model.orderModel.OrderStatusChangeResponseModel;
import com.io.bookstore.model.registerModel.RegisterModel;
import com.io.bookstore.model.sliderAdModel.AdModel;
import com.io.bookstore.model.store.EditStoreDetialResponseModel;
import com.io.bookstore.model.store.StoreDetailResponseModel;
import com.io.bookstore.model.storeDetailsModel.StoreDetailsModel;
import com.io.bookstore.model.storeModel.StoreModel;
import com.io.bookstore.model.updateAddResponseModel.UpdateAddResponseModel;
import com.io.bookstore.model.updatePasswordModel.UpdatePasswordModel;
import com.io.bookstore.model.verifyOtpModel.VerifyOtpModel;
import com.io.bookstore.model.wishlistModel.AddorRemoveWishlistResponseModel;
import com.io.bookstore.model.wishlistModel.GetWishlistResponseModel;
import com.io.bookstore.utility.UrlLocator;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.FilePart;
import com.koushikdutta.async.http.body.Part;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.io.bookstore.localStorage.LocalStorage.token;


public class ApiCaller {

    /* -----------------------------------------------------Registration api------------------------------------------------------*/

    public static void registerCustomer(Activity activity, String url, String email,
                                        String phone, String password, String firstname,
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
                .setMultipartParameter("name", firstname)
                .setMultipartParameter("email", email)
                .setMultipartParameter("phone", phone)
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
                                  int zipcode, String locality, String country, String code, String landmark, String token, final FutureCallback<AddAddressResponseModel> apiCallBack) {

        final JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("address", address);
        json.addProperty("addresstype", addresstype);
        json.addProperty("phonenum", 980250410);
        json.addProperty("city", city);
        json.addProperty("state", state);
        json.addProperty("country", country);
        json.addProperty("landmark", landmark);
        json.addProperty("zipcode", zipcode);
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

    public static void getBookModel(Activity activity, String url, String sId, String cId, String name,
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

    public static void getOrder(Activity activity, String url, String token, final FutureCallback<GetAllOrder> apiCallBack) {


        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setHeader("Authorization", "Bearer " + token)
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        GetAllOrder placeOrderResponseModel = gson.fromJson(result, GetAllOrder.class);
                        apiCallBack.onCompleted(e, placeOrderResponseModel);
                    }
                });

    }

    /*------------------------------------------------------- serach product api --------------------------------------------------*/

    public static void getfilterData(Activity activity, String url, String city,
                                     final FutureCallback<FilterAddressModel> apiCallBack) {
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


    public static void getAdminBookList(Activity activity, String url, int sId, int cId, String name,
                                        final FutureCallback<AdminBookListResponseModel> apiCallback) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
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
                                      Integer bookId, final FutureCallback<EditBookResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        List<Part> files = new ArrayList();
        if (image == null) {
            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .setHeader("Authorization", "Bearer " + token)
                    .setHeader("Role", "store")
                    .setMultipartParameter("name", bookname)
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


}
