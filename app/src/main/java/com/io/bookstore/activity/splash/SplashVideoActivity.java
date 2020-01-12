package com.io.bookstore.activity.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.io.bookstore.R;


public class SplashVideoActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_video);


        getSupportActionBar().hide();


        videoView = (VideoView) findViewById(R.id.vvAnim);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animation);

        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (isFinishing())
                    return;

                startActivity(new Intent(SplashVideoActivity.this, LangChangeActivity.class));
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_from_left);
                finish();
            }
        });

        videoView.start();
    }
}
