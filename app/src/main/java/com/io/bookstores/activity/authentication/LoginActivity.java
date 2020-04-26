package com.io.bookstores.activity.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.activity.homeActivity.MainActivity;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.bookStore.BookStoreMainActivity;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.loginModel.LoginModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

/**
 * Created by Utkarsh Raj 10/12/2019.
 */

public class LoginActivity extends AppCompatActivity {
    private Activity activity;
    private TextView tvSignUp,tv_tnc ;
    private Button login_btn, btn_gust;
    private EditText email,pass;
    userOnlineInfo user;
    private  String deviceToken;
    private NewProgressBar dialog;
    private String userNaame,password = "";
    private LocalStorage localStorage;
    private TextView tv_forgotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        bindListner();
    }

    /*------------------------------------ intialize all Views that are used in this activity -----------------------------------*/

    private void initView() {
        tvSignUp = findViewById(R.id.tvSignUp);
        btn_gust = findViewById(R.id.btn_gust);
        tv_forgotpassword = findViewById(R.id.tvForgotPass);
        tv_tnc = findViewById(R.id.tvSkip);
        login_btn = findViewById(R.id.btnSignIn);
        activity = this;
        user = new userOnlineInfo();
        localStorage = new LocalStorage(activity);
        email = findViewById(R.id.etEmailLogin);
        pass = findViewById(R.id.etPassLogin);
        if (localStorage.getBoolean(LocalStorage.isCart) == true) {
            btn_gust.setVisibility(View.VISIBLE);
        }
        FirebaseApp.initializeApp(this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        deviceToken = instanceIdResult.getToken();
                    }
                });
    }

    /*------------------------------------------ bind all views that are used in this activity --------------------------------*/

    private void bindListner() {
        tv_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ForgotPasswodActivity.class);
                startActivity(intent);
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        tv_tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loginApi();
            }
        });

        btn_gust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, GuestLoginActivity.class));
            }
        });
    }

    /*----------------------------------------------- login Api Call ----------------------------------------------------------*/

    private void loginApi() {
        userNaame = email.getText().toString().trim();
        password = pass.getText().toString().trim();
        if(userNaame.length()<1){
            Utils.showAlertDialog(activity, "Please Enter Username");
        }
       else if(password.length()<5){
            Utils.showAlertDialog(activity, "Please Enter Minimum 5 Digit Password");
        }
       else if(userNaame.equals("") || password.equals("")){
            Utils.showAlertDialog(activity, "Please Enter Username and password");
        }else {
            if (user.isOnline(activity)) {
                dialog = new NewProgressBar(activity);
                dialog.show();
                ApiCaller.loginCustomer(activity, Config.Url.login, userNaame, password,
                        new FutureCallback<LoginModel>() {
                            @Override
                            public void onCompleted(Exception e, LoginModel result) {
                                if (e != null) {
                                    Utils.showAlertDialog(activity, "Something Went Wrong");
                                    dialog.dismiss();
                                    return;
                                }

                                if (result != null) {
                                    if (result.getStatus() == true) {
                                        dialog.dismiss();
                                        saveLoginData(result);
                                        localStorage.putBooleAan(LocalStorage.isLoggedIn, true);
                                        navigateToHomeActivit(result);
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(activity, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });

            } else {
                Utils.showAlertDialog(activity, "No Internet Connection");
            }
        }
    }
    /*------------------------------------------ Go to user portal or Book store portal ----------------------------------------*/

    private void navigateToHomeActivit(LoginModel result) {
        localStorage.putInt(LocalStorage.role,result.getData().getRole());
        if(result.getData().getRole()== 1){
            Intent i = new Intent(activity , BookStoreMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }else {
            Intent i = new Intent(activity , MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }

    /* ------------------------------------------- login data save into Local storage --------------------------------------------*/
    private void saveLoginData(LoginModel result) {
        Gson gson = new Gson();
        String json = gson.toJson(result);
        localStorage.putDistributorProfile(result);
        localStorage.putString(LocalStorage.token,result.getData().getToken());
        localStorage.putInt(LocalStorage.role,result.getData().getRole());
        if(result.getData().getRole() ==0){
            localStorage.putInt(LocalStorage.userId,result.getData().getUser().getUserId());
        }else {
           localStorage.putInt(LocalStorage.userId,result.getData().getUser().getStoreId());
        }

    }
}
