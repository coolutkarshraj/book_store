package com.io.bookstores.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.holder.StoresHolder;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.storeModel.Datum;

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
