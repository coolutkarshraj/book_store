package com.io.bookstores.activity.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.activity.homeActivity.MainActivity;
import com.io.bookstores.adapter.adminAdapter.AddsAdapter;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.bookStore.BookStoreMainActivity;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.contactUs.AdsDataModel;
import com.io.bookstores.model.contactUs.AdsResponseModel;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.List;

public class AddsActivity extends AppCompatActivity implements View.OnClickListener {


    private Activity activity;
    private Button btn_skip;
    private RecyclerView recyclerViewAdds;
    private AddsAdapter addsAdapter;
    private userOnlineInfo user;
    private LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_adds);
        intailzeViews();
        bindListner();
        startWorking();
    }


    private void intailzeViews() {
        activity = AddsActivity.this;
        user = new userOnlineInfo();
        localStorage = new LocalStorage(activity);
        btn_skip = findViewById(R.id.btn_skip);
        recyclerViewAdds = findViewById(R.id.rv_adds);
        recyclerViewAdds.setLayoutManager(new GridLayoutManager(activity, 2));
    }

    private void bindListner() {
        btn_skip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_skip:
                if(localStorage.getInt(LocalStorage.role)== 1){
                    Intent i = new Intent(AddsActivity.this , BookStoreMainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                return;

        }

    }

    private void startWorking() {
        getAllAds();
    }

    private void getAllAds() {

        if (user.isOnline(activity)) {

            ApiCaller.getAdvertisment(activity, Config.Url.getAddsList, new FutureCallback<AdsResponseModel>() {
                @Override
                public void onCompleted(Exception e, AdsResponseModel result) {
                    if (e != null) {
                        Utils.showAlertDialog(activity, "Something Went Wrong");
                        return;
                    }

                    if (result != null) {
                        if (result.getStatus() == true) {
                           recyclerViewDataSetUp(result.getData());
                        } else {
                            Utils.showAlertDialog(activity, "Something Went Wrong");
                        }
                    }

                }
            });


        } else {

            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }

    private void recyclerViewDataSetUp(List<AdsDataModel> data) {
        addsAdapter = new AddsAdapter(activity,data);
        recyclerViewAdds.setAdapter(addsAdapter);
    }

}
