package com.io.bookstore.adapter.admin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.listeners.RecyclerViewClickListener;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.adminResponseModel.AdminBookDataModel;
import com.io.bookstore.model.adminResponseModel.DeleteBookResponseModel;
import com.io.bookstore.model.contactUs.AdsDataModel;
import com.io.bookstore.model.sliderAdModel.AdData;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

public class AddsAdapter extends
        RecyclerView.Adapter<AddsAdapter.MyViewHolder> {

    private Context mContext;
    private List<AdsDataModel> mData;
    LocalStorage localStorage;
    userOnlineInfo user;
    NewProgressBar dialog;
    Dialog dialogs;
    private RecyclerViewClickListener item;


    public AddsAdapter(Context mContext, List<AdsDataModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean flag = false;
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_ads, parent, false);
        localStorage = new LocalStorage(mContext);
        user = new userOnlineInfo();
        dialogs = new Dialog(mContext);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final AdsDataModel model = mData.get(position);

        Glide.with(mContext).load(Config.imageUrl + model.getAvatarPath()).into(holder.iv_image);

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_image;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);


        }
    }








}