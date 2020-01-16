package com.io.bookstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.holder.OrderHolder;
import com.io.bookstore.model.addAddressResponseModel.GetAdminOrderListDataModel;
import com.io.bookstore.model.getAllOrder.Datum;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class AdminOrderAdapter extends RecyclerView.Adapter<OrderHolder> {
    private Activity activity;
    private List<GetAdminOrderListDataModel> courseicon;

    public AdminOrderAdapter(Activity activity, List<GetAdminOrderListDataModel> courseicon) {
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
        GetAdminOrderListDataModel model = courseicon.get(position);
        holder.tv_product_id.setText(courseicon.get(position).toString());
        holder.textView11.setText(model.getCreatedDate());
        holder.textView12.setText("Status-" + " "+model.getOrderStatus());
        holder.textView9.setText("Order -#" + " "+model.getOrderId());
        Glide.with(activity).load(Config.imageUrl +model.getOrderItems().get(0).getBook().getAvatarPath()).into(holder.imageView10);
    }
    @Override
    public int getItemCount() {
        return courseicon.size();
    }
}
