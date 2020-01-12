package com.io.bookstore.activity.checkoutActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.io.bookstore.R;
import com.io.bookstore.activity.homeActivity.MainActivity;

public class PaymentConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation2);

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(PaymentConfirmationActivity.this, MainActivity.class));
                        finish();
                    }
                }, 5000);
    }
}
