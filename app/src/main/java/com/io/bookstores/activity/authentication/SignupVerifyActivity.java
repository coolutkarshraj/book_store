package com.io.bookstores.activity.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.verifyOtpModel.VerifyOtpModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

public class SignupVerifyActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    Button changePassword;
    Activity activity;
    EditText otpEdit1,otpEdit2,otpEdit3,otpEdit4;
    String userId;
    private LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_forgot_password);
        initializeViews();
        bindListner();
    }

    private void initializeViews() {
        activity = SignupVerifyActivity.this;
        localStorage = new LocalStorage(activity);
        otpEdit1 = findViewById(R.id.otpEdit1);
        otpEdit2 = findViewById(R.id.otpEdit2);
        otpEdit3 = findViewById(R.id.otpEdit3);
        otpEdit4 = findViewById(R.id.otpEdit4);
        activity = SignupVerifyActivity.this;
        back = (ImageView)findViewById(R.id.back);
        changePassword = (Button) findViewById(R.id.btnsubmit);
        userId = getIntent().getStringExtra("userId");
    }

    private void bindListner() {
        back.setOnClickListener(this);
        changePassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back :
                onBackPressed();
                return;

            case R.id.btnsubmit :
                String otp = otpEdit1.getText().toString()+otpEdit2.getText().toString()+
                        otpEdit3.getText().toString()+otpEdit4.getText().toString();
                callApiToRegisterCutomer(otp);

                return;
        }

    }

    private void callApiToRegisterCutomer(String otp) {
        if(otp.length()<4){
            Utils.showAlertDialog(activity, "Please Enter valid Otp");
        }

        userOnlineInfo user = new userOnlineInfo();
        if (user.isOnline(activity)) {
            final NewProgressBar dialog = new NewProgressBar(activity);
            dialog.show();
            ApiCaller.verifyOTP(activity, Config.Url.verifyemail, Integer.parseInt(otp),userId,
                    new FutureCallback<VerifyOtpModel>() {
                        @Override
                        public void onCompleted(Exception e, VerifyOtpModel result) {
                            dialog.dismiss();
                            if(e!=null){
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }
                            if(result.getMessage().toLowerCase().equals("Otp wrong , try again".toLowerCase())){
                                Utils.showAlertDialog(activity, "Wrong OTP");
                                return;
                            }
                            navigateToHomeActivit();
                        }
                    });

        }else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }

    }

    private void navigateToHomeActivit() {
        Toast.makeText(this,"Thank You For Registering, Please Login To Continue",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity, LoginActivity.class);
        startActivity(intent);
    }

}
