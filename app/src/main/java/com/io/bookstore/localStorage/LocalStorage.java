package com.io.bookstore.localStorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.io.bookstore.model.UserModel;
import com.io.bookstore.model.loginModel.LoginModel;

/**
 * Created by Utkarsh Raj 10/12/2019.
 */

public class LocalStorage {
    public static final String LOGGEDINDATA = "LOGGEDINDATA";
    private static final String DISTRIBUTOR = "";
    public static String userId = "userId";
    private static LocalStorage instance;
    private SharedPreferences storage;
    public static final String isFirstLaunch = "isFirstLaunch";
    public static final String ONLINE_STATUS = "onlineStatus";

    public LocalStorage(Context context) {
        storage = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static LocalStorage getInstance(Context context) {
        if (instance == null)
            instance = new LocalStorage(context);
        return instance;
    }

    public void putString(String name, String value) {
        storage.edit().putString(name, String.valueOf(value)).apply();
    }

    public void putBooleAan(String name, Boolean value) {
        storage.edit().putBoolean(name, value).apply();
    }

    public boolean getBoolean(String name) {
        return storage.getBoolean(name, false);

    }

    public String getString(String name) {
        return storage.getString(name, "");
    }

    public void putInt(String name, int value) {
        storage.edit().putInt(name, value).apply();
    }

    public int getInt(String name) {
        return storage.getInt(name, -1);
    }

    public void putDistributorProfile(LoginModel profile) {
        final Gson gson = new Gson();
        String profileInJson = gson.toJson(profile);
        storage.edit().putString(DISTRIBUTOR, profileInJson).apply();
    }

    public LoginModel getUserProfile() {
        final Gson gson = new Gson();
        String profileJson = storage.getString(DISTRIBUTOR, null);
        LoginModel profile = gson.fromJson(profileJson, LoginModel.class);
        return profile;
    }
    public void clearAll() {
        storage.edit().clear().apply();
    }
}

