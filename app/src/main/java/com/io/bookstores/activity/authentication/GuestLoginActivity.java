package com.io.bookstores.activity.authentication;

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
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.activity.homeActivity.MainActivity;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.bookStore.BookStoreMainActivity;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.loginModel.LoginModel;
import com.io.bookstores.model.registerModel.RegisterModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

public class GuestLoginActivity extends AppCompatActivity {
    private TextView tvLogin,tv_tnc ;
    private Button signup_btn;
    private EditText firstname;
    private EditText lastName;
    private EditText email;
    private EditText username;
    private EditText number;
    private EditText pass;
    private EditText et_address;
    String strImageFormet;
    private NewProgressBar dialog;
    private userOnlineInfo user;
    private Activity activity;
    private  LocalStorage localStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_login);
        initView();
        bindListner();
    }

    private void bindListner() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuestLoginActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        tv_tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuestLoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationValidation();
            }
        });
    }

    private void initView() {
        activity = GuestLoginActivity.this;
        firstname = findViewById(R.id.etFullName);
        // lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.etEmail);
        //  username = findViewById(R.id.username);
        number = findViewById(R.id.etPhoneSignUp);
        pass = findViewById(R.id.etPassLogin);
        et_address = findViewById(R.id.etAddressSignUp);
        tvLogin = findViewById(R.id.tvSignIn);
        tv_tnc = findViewById(R.id.tvSkipp);
        signup_btn = findViewById(R.id.btnSignUp);
        user = new userOnlineInfo();
        localStorage = new LocalStorage(activity);
    }


    /* --------------------------------------------------registration validation----------------------------------------------*/

    private void registrationValidation() {
        String f_name = firstname.getText().toString().trim();
        // String l_name = lastName.getText().toString().trim();
        String u_emael = email.getText().toString().trim();
        // String et_userName = username.getText().toString().trim();
        String et_number = number.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String address = et_address.getText().toString().trim();

        if(f_name.equals("") ||  u_emael.equals("")
                || password.equals("") || et_number.equals("") ||address.equals("")){
            Utils.showAlertDialog(GuestLoginActivity.this, "Please Enter Your Information Properly");
            return;
        }
        else {
            registrationApi(f_name,u_emael,et_number,password,address);
        }
    }

    /*----------------------------------------------- Registration Api------------------------------------------------------*/

    private void registrationApi(String f_name, final String u_emael, String et_number, final String password,
                                 String address) {
        if (user.isOnline(this)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            ApiCaller.registerCustomer(this, Config.Url.registerCustomer
                    , u_emael, et_number, password, f_name,address,
                    new FutureCallback<RegisterModel>() {
                        @Override
                        public void onCompleted(Exception e, RegisterModel result) {
                            LocalStorage localStorage = new LocalStorage(GuestLoginActivity.this);

                            if(e!= null){
                                Utils.showAlertDialog(GuestLoginActivity.this, "Something Went Wrong");
                                dialog.dismiss();
                                return;
                            }
                            if(result != null){
                                if(result.getStatus()== true) {
                                    dialog.dismiss();
                                    loginApi(u_emael,password);
                                   // Toast.makeText(GuestLoginActivity.this, "" + result.getMessage(), Toast.LENGTH_LONG).show();
                                   /* Intent intent = new Intent(GuestLoginActivity.this, LoginActivity.class);
                                    startActivity(intent);*/
                                }else {
                                    dialog.dismiss();
                                    Intent intent = new Intent(GuestLoginActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(GuestLoginActivity.this, "" + result.getMessage() +". please login", Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    });
        }else {
            Utils.showAlertDialog(this, "No Internet Connection");
        }

    }

    private void loginApi(String u_emael, String password) {

            if (user.isOnline(activity)) {
                dialog = new NewProgressBar(activity);
                dialog.show();
                ApiCaller.loginCustomer(activity, Config.Url.login, u_emael, password,
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // localStorage.putBooleAan(LocalStorage.isEnroll, false);
    }
}
