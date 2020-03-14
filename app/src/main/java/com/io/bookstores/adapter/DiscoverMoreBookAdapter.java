package com.io.bookstores.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.holder.DiscoverMoreBookHolder;


/**
 * Created by Utkarsh Raj 10/12/2019.
 */

public class DiscoverMoreBookAdapter extends RecyclerView.Adapter<DiscoverMoreBookHolder> {
    private Activity activity;

    public DiscoverMoreBookAdapter(Activity activity) {
        this.activity = activity;
    }



    @Override
    public DiscoverMoreBookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_discover_book_adapter, parent, false);
        return new DiscoverMoreBookHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoverMoreBookHolder holder, int position) {
        holder.tvTitle.setText("");
        holder.tvMessage.setText("");
        holder.tvDate.setText("");
    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
