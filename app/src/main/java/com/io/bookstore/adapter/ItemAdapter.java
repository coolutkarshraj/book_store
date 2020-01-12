package com.io.bookstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;
import com.io.bookstore.holder.itemHolder;
import com.io.bookstore.utility.Utils;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<itemHolder> {
    private Activity activity;
    private ArrayList itemname;
    private ArrayList item;
    public ItemAdapter(Activity activity, ArrayList itemname, ArrayList item) {
        this.activity = activity;
        this.itemname = itemname;
        this.item = item;
    }

    @Override
    public itemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_view_purchase_item, parent, false);
        return new itemHolder(view);
    }

    @Override
    public void onBindViewHolder(itemHolder holder, int position) {
        holder.name.setText(itemname.get(position).toString());
        holder.image.setImageResource((Integer) item.get(position));
    }
    @Override
    public int getItemCount() {
        return itemname.size();
    }
}
