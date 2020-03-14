package com.io.bookstores.activity.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.io.bookstores.R;
import com.io.bookstores.activity.authentication.LoginActivity;
import com.io.bookstores.activity.homeActivity.MainActivity;
import com.io.bookstores.localStorage.LocalStorage;

import java.util.Timer;
import java.util.TimerTask;

public class AppDescriptionSplashActivity extends AppCompatActivity {

    LocalStorage preferenceManager;
    LinearLayout Layout_bars;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    int[] screens;
    ViewPager vp;
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    MyViewPagerAdapter myvpAdapter;
    ImageView[] bottomBars;
    TextView tv_skip,tv_next;
    int selPosition = -1;
    int currentPage = 0;
    Timer timer;
    LocalStorage localStorage;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            ColoredBars(position);
            selPosition= position;
            currentPage = position;
            if (position == screens.length - 1) {

            } else {
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initViwew();
        bindListner();
        moveScreen();

    }

    private void moveScreen() {
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == screens.length) {
                    currentPage = 0;
                }
                vp.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void bindListner() {
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localStorage.putBooleAan(LocalStorage.isFirstLaunch,true);
                Intent intent = new Intent(AppDescriptionSplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selPosition == 2){
                    localStorage.putBooleAan(LocalStorage.isFirstLaunch,true);
                    String data = localStorage.getString(LocalStorage.LOGGEDINDATA);
                    Intent intent = new Intent(AppDescriptionSplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    next(getWindow().getDecorView().findViewById(android.R.id.content));
                }
            }
        });
    }

    private void initViwew() {
        localStorage = new LocalStorage(AppDescriptionSplashActivity.this);
        localStorage.putBooleAan(LocalStorage.isFirstLaunch,true);
        vp = (ViewPager) findViewById(R.id.view_pager);
        tv_skip = (TextView) findViewById(R.id.tv_skip);
        tv_next = (TextView) findViewById(R.id.tv_next);
        Layout_bars = (LinearLayout) findViewById(R.id.layoutBars);
        screens = new int[]{
                R.layout.intro_screen1,
                R.layout.intro_screen2,
                R.layout.intro_screen3,
        };
        myvpAdapter = new MyViewPagerAdapter();
        vp.setAdapter(myvpAdapter);
        preferenceManager = new LocalStorage(this);
        vp.addOnPageChangeListener(viewPagerPageChangeListener);
        ColoredBars(0);
    }

    public void next(View v) {
        int i = getItem(+1);
        if (i < screens.length) {
            vp.setCurrentItem(i);
        } else {
           // launchMain();
        }
    }

    private int getItem(int i) {
        return vp.getCurrentItem() + i;
    }

    private void ColoredBars(int thisScreen) {
       bottomBars = new ImageView[screens.length];

        Layout_bars.removeAllViews();
        for (int i = 0; i < bottomBars.length; i++) {
            bottomBars[i] = new ImageView(this);
            if(i==thisScreen)
                bottomBars[i].setImageDrawable(getResources().getDrawable(R.drawable.item_selected));
            else
                bottomBars[i].setImageDrawable(getResources().getDrawable(R.drawable.item_unselected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            Layout_bars.addView(bottomBars[i], params);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater inflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
           inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(screens[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return screens.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);
        }

        @Override
        public boolean isViewFromObject(View v, Object object) {
            return v == object;
        }
    }
}