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
import com.io.bookstores.model.changePasswordOtpModel.ChangePasswordVerifyOtpModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ForgotPasswodActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    EditText etEmail;
    Button btnRequest;
    Activity activity;
    userOnlineInfo user;
    NewProgressBar dialog;
    String endPoint, strEmail;

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
        back = (ImageView) findViewById(R.id.back);
        etEmail = (EditText) findViewById(R.id.et_email);
        btnRequest = (Button) findViewById(R.id.btnrquest);

    }

    /*-------------------------------------------------- bindListner------------------------------------------------------------*/

    private void bindListner() {
        back.setOnClickListener(this);
        btnRequest.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                return;

            case R.id.btnrquest:
                forgotPasswordView();
                return;
        }

    }

    /* --------------------------------------------------------Validate data----------------------------------------------*/

    private void forgotPasswordView() {
        strEmail = etEmail.getText().toString().trim();
        if (strEmail.equals("")) {
            Utils.showAlertDialog(activity, "Please Enter Email-Id");
        } else if (!isEmailValid(strEmail)) {
            Utils.showAlertDialog(activity, getResources().getString(R.string.email_not_valid));
        } else {
            forgotPasswordApi();
        }
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    private void apiUrl() {
        endPoint = Config.Url.forgotPassword;
    }

    /*------------------------------------------------------- forgot api ---------------------------------------------------*/

    private void forgotPasswordApi() {
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            apiUrl();
            ApiCaller.forgotPassword(activity, endPoint, strEmail, new
                    FutureCallback<ChangePasswordVerifyOtpModel>() {
                        @Override
                        public void onCompleted(Exception e, ChangePasswordVerifyOtpModel result) {
                            if (e != null) {
                                dialog.show();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                                return;
                            }

                            if (result != null) {
                                if (result.getStatus() == null) {
                                    dialog.show();
                                    Utils.showAlertDialog(activity, "Something Went Wrong");
                                } else if (result.getStatus()) {
                                    dialog.show();
                                    Intent i = new Intent(activity, LoginActivity.class);
                                    startActivity(i);
                                    etEmail.getText().clear();
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
                                    etEmail.getText().clear();
                                }
                            } else {
                                dialog.show();
                                Utils.showAlertDialog(activity, "Something Went Wrong");
                            }
                        }
                    });

        } else {
            Utils.showAlertDialog(this, "No Internet Connection");
        }


    }


}
