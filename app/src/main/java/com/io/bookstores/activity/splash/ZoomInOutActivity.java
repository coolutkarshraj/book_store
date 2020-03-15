package com.io.bookstores.activity.splash;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.io.bookstores.R;

public class ZoomInOutActivity extends AppCompatActivity implements View.OnClickListener {


    private Activity activity;
    private PhotoView photoView;
    private ImageView ivback, ivcancel;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_zoom_in_out);
        intializeView();
        getDataFromIntent();
        startWorking();
    }


    private void intializeView() {
        activity = ZoomInOutActivity.this;
        photoView = findViewById(R.id.photo_view);
        ivback = (ImageView) findViewById(R.id.menu);
        ivcancel = (ImageView) findViewById(R.id.iv_cancel);
        ivback.setOnClickListener(this);
        ivcancel.setOnClickListener(this);
    }

    private void getDataFromIntent() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            image = extras.getString("images");
        }
    }

    private void startWorking() {
        Glide.with(activity).load(image).into(photoView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu:
                onBackPressed();
                return;
            case R.id.iv_cancel:
                onBackPressed();
                return;
        }

    }
}
