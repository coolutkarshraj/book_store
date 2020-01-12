package com.io.bookstore.activity.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.model.changePasswordOtpModel.ChangePasswordVerifyOtpModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;


public class ForgotPasswodActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    EditText etEmail;
    Button btnRequest;
    Activity activity;
    userOnlineInfo user;
    NewProgressBar dialog;
    String endPoint,strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passwod);
        initializeViews();
        bindListner();
    }

    /* ---------------------------------------intialize all views that are used in this activity-------------------------------*/
    private void initializeViews() {
        user = new userOnlineInfo();
        activity = ForgotPasswodActivity.this;
        back = (ImageView)findViewById(R.id.back);
        etEmail = (EditText)findViewById(R.id.et_email);
        btnRequest = (Button)findViewById(R.id.btnrquest);

    }

    /*-------------------------------------------------- bindListner------------------------------------------------------------*/

    private void bindListner() {
        back.setOnClickListener(this);
        btnRequest.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back :
                onBackPressed();
                return;

            case R.id.btnrquest :
                forgotPasswordView();
                return;
        }

    }

    /* --------------------------------------------------------Validate data----------------------------------------------*/

    private void forgotPasswordView() {
     strEmail = etEmail.getText().toString().trim();
     if(strEmail.equals("")){
         Utils.showAlertDialog(activity, "Please Enter Email-Id");
     }else {
         forgotPasswordApi();
     }
    }


    private  void apiUrl(){
        endPoint = Config.Url.forgotPassword;
    }

    /*------------------------------------------------------- forgot api ---------------------------------------------------*/

    private void forgotPasswordApi() {
        if(user.isOnline(activity)){
            dialog = new NewProgressBar(activity);
            dialog.show();
            apiUrl();
            ApiCaller.forgotPassword(activity, endPoint, strEmail, new
                    FutureCallback<ChangePasswordVerifyOtpModel>() {
                        @Override
                        public void onCompleted(Exception e, ChangePasswordVerifyOtpModel result) {
                            if(e!=null){
                                return;
                            }
                            Intent i =new Intent(activity, LoginActivity.class);
                            startActivity(i);
                        }
                    });

        }else {

        }


    }


}
