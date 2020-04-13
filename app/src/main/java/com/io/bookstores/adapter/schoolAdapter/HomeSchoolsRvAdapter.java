package com.io.bookstores.adapter.schoolAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.holder.HomeSchoolHolder;
import com.io.bookstores.holder.StoresHolder;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.schoolModel.GetAllSchoolDataModel;


import java.util.List;

public class HomeSchoolsRvAdapter extends RecyclerView.Adapter<HomeSchoolHolder> {
    private Activity activity;
    List<GetAllSchoolDataModel> list;
    private ItemClickListner itemClickListner;

    public HomeSchoolsRvAdapter(Activity activity, List<GetAllSchoolDataModel> list) {
        this.activity = activity;
        this.list = list;

    }

    @Override
    public HomeSchoolHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_home_school, parent, false);
        return new HomeSchoolHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeSchoolHolder holder, final int position) {
        GetAllSchoolDataModel data = list.get(position);
        holder.name.setText(data.getSchoolName());
        Glide.with(activity).load(Config.imageUrl + data.getAvatarPath()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;
                itemClickListner.onClick(8);
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list.size() > 4) {
            count = 4;
        } else {
            count = list.size();
        }
        return count;
    }
}
