package com.io.bookstore.activity.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.io.bookstore.R;
import com.io.bookstore.activity.homeActivity.MainActivity;
import com.io.bookstore.activity.homeActivity.ui.home.BookstoreBooksActivity;
import com.io.bookstore.localStorage.LocalStorage;

/**
 * Created by Utkarsh Raj 10/12/2019.
 */
public class SplashActivity extends AppCompatActivity {

    Button btn_en,btn_kuw;
    VideoView imageView3;
    LocalStorage localStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        bindListner();
           }

    private void initView() {
        localStorage = new LocalStorage(SplashActivity.this);
        btn_kuw = findViewById(R.id.btn_kuw);
        btn_en = findViewById(R.id.btn_en);
    }

    private void bindListner() {
        btn_kuw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(localStorage.getBoolean(LocalStorage.isFirstLaunch)){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(getApplicationContext(), AppDescriptionSplashActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(localStorage.getBoolean(LocalStorage.isFirstLaunch)){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(getApplicationContext(), AppDescriptionSplashActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
