package com.io.bookstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.holder.StoresHolder;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.storeModel.Datum;

import java.util.ArrayList;
import java.util.List;

public class SToreAdapter extends RecyclerView.Adapter<StoresHolder> {
    private Activity activity;
    List<Datum> coursename;
    private ItemClickListner itemClickListner;
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
    public void onBindViewHolder(StoresHolder holder, final int position) {
        holder.name.setText(coursename.get(position).name);
        Glide.with(activity).load(Config.imageUrl +coursename.get(position).getAvatarPath()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.putString(LocalStorage.StoreId, String.valueOf(coursename.get(position).storeId));
                itemClickListner.onClick(2);
            }
        });
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
