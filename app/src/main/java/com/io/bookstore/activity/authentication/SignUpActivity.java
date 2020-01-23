package com.io.bookstore.activity.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.activity.homeActivity.MainActivity;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.registerModel.RegisterModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Utkarsh Raj 10/12/2019.
 */

public class SignUpActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        bindListner();
    }


    private void bindListner() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        tv_tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
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
        firstname = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        number = findViewById(R.id.number);
        pass = findViewById(R.id.pass);
        et_address = findViewById(R.id.et_address);
        tvLogin = findViewById(R.id.tvLogin);
        tv_tnc = findViewById(R.id.tv_tnc);
        signup_btn = findViewById(R.id.signup_btn);
        user = new userOnlineInfo();
    }


    /* --------------------------------------------------registration validation----------------------------------------------*/

    private void registrationValidation() {
        String f_name = firstname.getText().toString().trim();
        String l_name = lastName.getText().toString().trim();
        String u_emael = email.getText().toString().trim();
        String et_userName = username.getText().toString().trim();
        String et_number = number.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String address = et_address.getText().toString().trim();

        if(f_name.equals("") || l_name.equals("") || u_emael.equals("") ||et_userName.equals("")
                    || password.equals("") || et_number.equals("") ||address.equals("")){
            Utils.showAlertDialog(SignUpActivity.this, "Please Enter Your Information Properly");
            return;
        }
        else {
            registrationApi(f_name,l_name,u_emael,et_userName,et_number,password,address);
        }
    }



    /*----------------------------------------------- Registration Api------------------------------------------------------*/

    private void registrationApi(String f_name, String l_name, String u_emael,
                                 String et_userName, String et_number, String password,
                                 String address) {
        if (user.isOnline(this)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            ApiCaller.registerCustomer(this, Config.Url.registerCustomer,et_userName
                    , u_emael, et_number, password, f_name,l_name,address,
                    new FutureCallback<RegisterModel>() {
                        @Override
                        public void onCompleted(Exception e, RegisterModel result) {
                            LocalStorage localStorage = new LocalStorage(SignUpActivity.this);

                            if(e!= null){
                                Utils.showAlertDialog(SignUpActivity.this, "Something Went Wrong");
                                return;
                            }
                            if(result != null){
                                if(result.getStatus()){

                                    Toast.makeText(SignUpActivity.this, ""+result.getMessage(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                  /*  if(result.getData()!= null){

                                       *//* try {
                                            localStorage.putString(LocalStorage.userId,result.getData().getUserId().toString());
                                        }catch (Exception excep){

                                        }*//*
                                    }*/

                                   /* Intent intent = new Intent(SignUpActivity.this,Lo.class);
                                    intent.putExtra("userId",localStorage.getString(LocalStorage.userId));
                                    startActivity(intent);*/
                                }
                            }

                        }
                    });
        }else {
            Utils.showAlertDialog(this, "No Internet Connection");
        }

    }
}
