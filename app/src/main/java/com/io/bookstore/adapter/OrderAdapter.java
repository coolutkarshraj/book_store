package com.io.bookstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;
import com.io.bookstore.holder.CartHolder;
import com.io.bookstore.holder.OrderHolder;
import com.io.bookstore.model.getAllOrder.Datum;

import java.util.ArrayList;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {
    private Activity activity;
    private List<Datum> courseicon;

    public OrderAdapter(Activity activity, List<Datum> courseicon) {
        this.activity = activity;
        this.courseicon = courseicon;
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_view_order, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        holder.tv_product_id.setText(courseicon.get(position).toString());
        holder.textView11.setText(courseicon.get(position).getCreatedDate());
        holder.textView12.setText("Status-" + " "+courseicon.get(position).getOrderStatus());
        holder.textView9.setText("Order -#" + " "+courseicon.get(position).getOrderId());
    }
    @Override
    public int getItemCount() {
        return courseicon.size();
    }
}
