package com.io.bookstore.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.Toast;

import com.io.bookstore.Config;
import com.io.bookstore.activity.authentication.LoginActivity;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.DbHelper;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.LogoutResponseModel;
import com.koushikdutta.async.future.FutureCallback;

/**
 * Created by Utkarsh Raj 10/12/2019.
 */
public class Utils {
    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
//        if (!email.contains(".") || !email.contains("@") || email.length() < 5) {
//            return false;
//        }
        return true;
    }

    public static boolean isMobileNumberValid(String number) {
        if (number == null || number.isEmpty()) {
            return false;
        }
//        if (number.length() != 10) {
//            return false;
//        }
        return true;
    }

    public static boolean isPasswordValid(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
//        if (password.length() < 8) {
//            return false;
//        }
        return true;
    }

    public static boolean isUserNameValid(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
//        if (password.length() < 8) {
//            return false;
//        }
        return true;
    }

    public static String toSentenceCase(String string) {
        if (string == null || string.isEmpty())
            return "";

        String charAt0 = String.valueOf(string.charAt(0));
        return string.replaceFirst(charAt0, charAt0.toUpperCase());
    }

    public static String toTitleCase(String string) {
        if (string == null || string.isEmpty())
            return "";

        String[] words = string.split("\\s+");
        String str = "";
        for (int i = 0; i < words.length; i++) {
            str += toSentenceCase(words[i].replaceAll("[^\\w]", "")) + " ";
        }
        str += "\b";
        return str;
    }

    public static void showAlertDialog(Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setTitle(message);
        dialog.show();
    }

    public static void showAlertDialogs(Activity activity, String message, final RadioButton rb2nd, final RadioButton rb_2nd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                rb_2nd.setChecked(false);
                rb2nd.setChecked(true);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setTitle(message);
        dialog.show();
    }

    public static void showAlertDialogLogout(final Activity activity, String message, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ApiCaller.logout(activity, Config.Url.logout + "/" + id, new FutureCallback<LogoutResponseModel>() {
                    @Override
                    public void onCompleted(Exception e, LogoutResponseModel result) {
                        if (e != null) {
                            Utils.showAlertDialog(activity, "Something Went Wrong");
                            return;
                        }

                        if (result != null) {
                            if (result.getStatus() == true) {
                                DbHelper dbHelper = new DbHelper(activity);
                                dbHelper.deleteAll();
                                LocalStorage localStorage = new LocalStorage(activity);
                                localStorage.putBooleAan(LocalStorage.isLoggedIn, false);
                                localStorage.putString(LocalStorage.token, "");
                                localStorage.putDistributorProfile(null);
                                Intent i = new Intent(activity, LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                activity.startActivity(i);
                            } else {
                                Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }


                    }
                });

            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setTitle(message);
        dialog.show();
    }

    public static void showAlertDialogAdminLogout(final Activity activity, String message, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ApiCaller.logout(activity, Config.Url.storelogout + "/" + id, new FutureCallback<LogoutResponseModel>() {
                    @Override
                    public void onCompleted(Exception e, LogoutResponseModel result) {
                        if (e != null) {
                            Utils.showAlertDialog(activity, "Something Went Wrong");
                            return;
                        }

                        if (result != null) {
                            if (result.getStatus() == true) {
                                DbHelper dbHelper = new DbHelper(activity);
                                dbHelper.deleteAll();
                                LocalStorage localStorage = new LocalStorage(activity);
                                localStorage.putBooleAan(LocalStorage.isLoggedIn, false);
                                localStorage.putString(LocalStorage.token, "");
                                localStorage.putDistributorProfile(null);
                                Intent i = new Intent(activity, LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                activity.startActivity(i);
                            } else {
                                Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }


                    }
                });

            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setTitle(message);
        dialog.show();
    }

    public static ProgressDialog getProgressDialog(Activity activity, String message) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(message);
        dialog.show();
        return dialog;
    }

    public static Dialog getModalDialog(Activity activity, boolean cancelable, int layout) {
        final android.app.Dialog dialog = new android.app.Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(cancelable);
        dialog.setContentView(layout);
        return dialog;
    }
}
