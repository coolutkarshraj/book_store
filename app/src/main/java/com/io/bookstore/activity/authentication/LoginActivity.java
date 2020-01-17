package com.io.bookstore.activity.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.activity.homeActivity.MainActivity;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.apicaller.LoginAPICaller;
import com.io.bookstore.bookStore.BookStoreMainActivity;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.UserModel;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

/**
 * Created by Utkarsh Raj 10/12/2019.
 */

public class LoginActivity extends AppCompatActivity {
    private Activity activity;
    private TextView tvSignUp,tv_tnc ;
    private Button login_btn;
    private EditText email,pass;
    userOnlineInfo user;
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
    }

    private void initView() {
        tvSignUp = findViewById(R.id.tvSignUp);
        tv_forgotpassword = findViewById(R.id.tv_forgotpassword);
        tv_tnc = findViewById(R.id.tv_tnc);
        login_btn = findViewById(R.id.login_btn);
        activity = this;
        user = new userOnlineInfo();
        localStorage = new LocalStorage(activity);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
    }

    private void loginApi() {
        userNaame = email.getText().toString().trim();
        password = pass.getText().toString().trim();
        if(userNaame.length()<1){
            Utils.showAlertDialog(activity, "Please Enter Username");
        }
        if(password.length()<6){
            Utils.showAlertDialog(activity, "Please Enter Minimum 6 Digit Password");
        }
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            ApiCaller.loginCustomer(activity, Config.Url.login, userNaame, password,
                    new FutureCallback<LoginModel>() {
                        @Override
                        public void onCompleted(Exception e, LoginModel result) {
                            if(e!=null){
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }
                            if(result.getStatus()){
                                saveLoginData(result);
                                localStorage.putBooleAan(LocalStorage.isLoggedIn,true);
                                navigateToHomeActivit(result);
                            }
                        }
                    });

        }else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }


    }

    private void navigateToHomeActivit(LoginModel result) {
        Toast.makeText(this, ""+result.getRole(), Toast.LENGTH_SHORT).show();
        localStorage.putInt(LocalStorage.role,result.getRole());
        if(result.getRole() == 0){
            Intent i = new Intent(activity , BookStoreMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }else {
            Intent i = new Intent(activity , MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }

    private void saveLoginData(LoginModel result) {
        Gson gson = new Gson();
        String json = gson.toJson(result);
        localStorage.putDistributorProfile(result);
        localStorage.putString(LocalStorage.token,result.getData().getToken());
        localStorage.putInt(LocalStorage.role,result.getRole());
        if(result.getRole() ==1){
            localStorage.putInt(LocalStorage.userId,result.getData().getUser().getUserId());
        }else {
            localStorage.putInt(LocalStorage.userId,result.getData().getUser().getStoreId());
        }

    }
}
