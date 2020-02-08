package com.io.bookstore.activity.checkoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.io.bookstore.R;
import com.io.bookstore.localStorage.DbHelper;

public class ProcessingActivity extends AppCompatActivity {

    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);
        dbHelper = new DbHelper(this);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        dbHelper.deleteAll();
                        startActivity(new Intent(ProcessingActivity.this,PaymentConfirmationActivity.class));
                        finish();
                    }
                }, 5000);
    }
}
