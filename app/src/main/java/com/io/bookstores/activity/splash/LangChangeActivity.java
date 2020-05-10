package com.io.bookstores.activity.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.localStorage.LocalStorage;

import java.util.Locale;

public class LangChangeActivity extends AppCompatActivity {

    Button btn_en,btn_kuw;
    LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lang_change);
        getSupportActionBar().hide();
        initView();
        bindListner();
    }

    private void initView() {
        btn_kuw = findViewById(R.id.btn_kuw);
        btn_en = findViewById(R.id.btn_en);
        localStorage =new LocalStorage(this);
    }

    private void bindListner() {
        btn_kuw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localStorage.putString(LocalStorage.islanguage,"kuwait");
                Locale locale = new Locale("ar");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.setLayoutDirection(Locale.ENGLISH);
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, null);
               // Toast.makeText(LangChangeActivity.this, "اللغة العربية المختارة!", Toast.LENGTH_LONG).show();
                if(localStorage.getBoolean(LocalStorage.isFirstLaunch) == true){
               /*     if(localStorage.getInt(LocalStorage.role)== 1){
                        Intent i = new Intent(LangChangeActivity.this , BookStoreMainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }*/
                    Intent intent = new Intent(getApplicationContext(), AddsActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        btn_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localStorage.putString(LocalStorage.islanguage,"english");
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, null);
               // Toast.makeText(LangChangeActivity.this, "English Language Selected!", Toast.LENGTH_LONG).show();

                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_from_left);
                if(localStorage.getBoolean(LocalStorage.isFirstLaunch)==true){

               /*     if(localStorage.getInt(LocalStorage.role)== 1){
                        Intent i = new Intent(LangChangeActivity.this , BookStoreMainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }else {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }*/
                    Intent intent = new Intent(getApplicationContext(), AddsActivity.class);
                    startActivity(intent);
                    finish();

                }else {

                    Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });


    }
}
