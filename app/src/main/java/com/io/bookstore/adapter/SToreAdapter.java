package com.io.bookstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.holder.StoresHolder;
import com.io.bookstore.model.storeModel.Datum;

import java.util.ArrayList;
import java.util.List;

public class SToreAdapter extends RecyclerView.Adapter<StoresHolder> {
    private Activity activity;
    List<Datum> coursename;
    public SToreAdapter(Activity activity, List<Datum> coursename) {
        this.activity = activity;
        this.coursename = coursename;
    }

    @Override
    public StoresHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_view_store, parent, false);
        return new StoresHolder(view);
    }

    @Override
    public void onBindViewHolder(StoresHolder holder, int position) {
        holder.name.setText(coursename.get(position).name);
        Glide.with(activity).load(Config.imageUrl +coursename.get(position).getAvatarPath()).into(holder.image);

    }
    @Override
    public int getItemCount() {
        int count = 0;
        if(coursename.size() > 4){
         count = 4;
        }else {
            count = coursename.size();
        }
        return count;
    }
}
