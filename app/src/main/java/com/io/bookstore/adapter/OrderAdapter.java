package com.io.bookstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.holder.CartHolder;
import com.io.bookstore.holder.OrderHolder;
import com.io.bookstore.listeners.RecyclerViewClickListener;
import com.io.bookstore.model.getAllOrder.Datum;

import java.util.ArrayList;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {
    private Activity activity;
    private List<com.io.bookstore.model.myOrder.Datum> courseicon;
    private RecyclerViewClickListener item;

    public OrderAdapter(Activity activity, List<com.io.bookstore.model.myOrder.Datum> courseicon, RecyclerViewClickListener item) {
        this.activity = activity;
        this.courseicon = courseicon;
        this.item = item;
    }



    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.user_item_order, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, final int position) {
        holder.tv_product_id.setText(courseicon.get(position).toString());
        holder.textView11.setText(courseicon.get(position).getCreatedDate());
        holder.textView12.setText("Status-" + " "+courseicon.get(position).getOrderStatus());
        holder.textView9.setText("Order -#" + " "+courseicon.get(position).getOrderId());
        Glide.with(activity).load(Config.imageUrl +courseicon.get(position).getOrderItems().get(0).getBook().getAvatarPath()).into(holder.imageView10);
        holder.textView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               item.onClickPosition(position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return courseicon.size();
    }
}
