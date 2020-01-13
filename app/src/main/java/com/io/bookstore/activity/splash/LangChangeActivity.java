package com.io.bookstore.activity.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.io.bookstore.R;
import com.io.bookstore.activity.homeActivity.ui.home.BookstoreBooksActivity;

import java.util.Locale;

public class LangChangeActivity extends AppCompatActivity {

    Button btn_en,btn_kuw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lang_change);
        getSupportActionBar().hide();
        initView();
        bindListner();
    }

    private void initView() {
        btn_kuw = findViewById(R.id.btn_kuw);
        btn_en = findViewById(R.id.btn_en);
    }

    private void bindListner() {
        btn_kuw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Locale locale = new Locale("hi");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, null);
                Toast.makeText(LangChangeActivity.this, "اللغة العربية المختارة!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btn_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, null);
                Toast.makeText(LangChangeActivity.this, "English Language Selected!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_from_left);
                finish();
            }
        });


    }
}
