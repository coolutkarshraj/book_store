package com.io.bookstores.adapter.adminAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.activity.splash.ZoomInOutActivity;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.contactUs.AdsDataModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.userOnlineInfo;

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
        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext , ZoomInOutActivity.class);
                i.putExtra("images",Config.imageUrl + model.getAvatarPath());
                mContext.startActivity(i);
            }
        });
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