package com.io.bookstore;

/**
 * Created by Utkarsh Raj 10/12/2019.
 */

public class Config {
    public final static String IP = "15.185.106.133";
    public static String imageUrl ="http://15.185.106.133/api/media/render/?path=";

    public static class Url {
        public final static String registerUser = "user/register";
        public final static String login = "user/login";
        public static String forgotPassword ="user/forgot-password";
        public static String registerCustomer = "user/signup";
        public static String verifyemail = "user/email-verify";
        public static String changepassword = "user/change-password";
        public static String getCategoryModel = "category/categorylist";
        public static String getAllBook = "book/booklist/";
        public static String editProfile = "user/update/";

        public static String getStoreApi = "store/storelist";
        public static String getAdressApi = "store/addresslist";
        public static String getStoreApifilter = "store/filter";
        public static String getDeliverCharge = "order/delivery-type";
        public static String addAddress = "order/add-address";
        public static String getAddressList = "user/detail";
        public static String placePrder = "order/placed";
        public static String getAllOrder = "user/orders/";
        public static String addorremoveWishlist = "book/add-remove-wishlist/?id=";
        public static String getWishList = "book/wishlist";
        public static String adminDeleteBook = "book/delete";
        public static String addbook = "book/add/";
        public static String editBookDetial = "book/edit/";
        public static String adminOrders = "store/orders/";
        public static String bookFilter = "book/booklist/-1/";
        public static String getStoreDetail = "store/detail?storeId=";
        public static String storePasswordChange = "store/change-password";
        public static String storeEdit = "store/edit";
        public static String statusUpdate = "store/orders-status";

        public static String insituitelist = "institute/list";
        public static String courseList = "course/list/";
        public static String courseEnroll = "course/enroll?cId=";
        public static String courseEnrolledList = "course/enrolled";
        public static String trendingInstitute = "institute/trending";
        public static String coursedetial = "course/detail?id=";
        public static String discountList = "discount/list";
        public static String logout = "user/logout";
        public static String storelogout = "store/logout";
        public static String disticGet = "order/district/-1";
        public static String orderprice = "order/delivery-price/?";
        public static String contactus = "user/complain";
        public static String getAddsList = "discount/adlist";
    }

    public final static int splashTimeout = 3000;

    public final static String serverNotResponding = "Service temporarily unavailable.";
    public final static String backPressMessage = "double tab to minimize";
    public final static String emptyInbox = "No message found";
    public final static String zeroSearchResuls = "No results found\nPlease search again";

    public final static int PaymentReceived = 1;
}
