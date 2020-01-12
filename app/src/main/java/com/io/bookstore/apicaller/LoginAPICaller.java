package com.io.bookstore.apicaller;

import android.app.Activity;
import android.app.ProgressDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.io.bookstore.Config;
import com.io.bookstore.model.UserModel;
import com.io.bookstore.utility.UrlLocator;
import com.io.bookstore.utility.Utils;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

/**
 * Created by Utkarsh Raj 10/12/2019.
 */

public class LoginAPICaller {

    public static ProgressDialog progressDialog;



    public static void loginUser(final UserModel loginModel,
                                 final Activity activity,
                                 final FutureCallback<Response<JsonObject>> apiCallBack) {
        progressDialog = Utils.getProgressDialog(activity, "Logging in please wait...");
        final Gson gson = new Gson();
        final String requestString = gson.toJson(loginModel);
        JsonObject json = gson.fromJson(requestString, JsonObject.class);
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(Config.Url.login))
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {

                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        progressDialog.dismiss();
                        apiCallBack.onCompleted(e, result);
                    }
                });
    }





}
