package com.io.bookstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;
import com.io.bookstore.holder.AddressHolder;
import com.io.bookstore.holder.CartHolder;

import java.util.ArrayList;


public class AddressAdapter extends RecyclerView.Adapter<AddressHolder> {
    private Activity activity;
    private ArrayList courseicon;

    public AddressAdapter(Activity activity, ArrayList courseicon) {
        this.activity = activity;
        this.courseicon = courseicon;
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.address_adapter, parent, false);
        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, int position) {
        holder.tv_address.setText(courseicon.get(position).toString());
    }
    @Override
    public int getItemCount() {
        return courseicon.size();
    }
}
