package com.io.bookstores.adapter.homeAdapter;

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

public class HomeStoreRvAdapter extends RecyclerView.Adapter<StoresHolder> {
    private Activity activity;
    private List<Datum> coursename;
    private ItemClickListner itemClickListner;
    LocalStorage localStorage;

    public HomeStoreRvAdapter(Activity activity, List<Datum> coursename) {
        this.activity = activity;
        this.coursename = coursename;

    }

    @Override
    public StoresHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_view_store, parent, false);
        localStorage = new LocalStorage(activity);
        return new StoresHolder(view);
    }

    @Override
    public void onBindViewHolder(StoresHolder holder, final int position) {

        if (localStorage.getString(LocalStorage.islanguage).equals("kuwait")) {
            holder.name.setText(coursename.get(position).getArabicName());
        } else {
            holder.name.setText(coursename.get(position).name);
        }

        Glide.with(activity).load(Config.imageUrl +coursename.get(position).getAvatarPath()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.putString(LocalStorage.TYPE,"store");
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
