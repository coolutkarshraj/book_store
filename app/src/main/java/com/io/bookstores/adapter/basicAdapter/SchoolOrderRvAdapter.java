package com.io.bookstores.adapter.basicAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.holder.OrderHolder;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.model.schoolOrderList.DataItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class SchoolOrderRvAdapter extends RecyclerView.Adapter<OrderHolder> {
    private Activity activity;
    private List<DataItem>  courseicon;
    private RecyclerViewClickListener item;

    public SchoolOrderRvAdapter(FragmentActivity activity, List<DataItem> data, RecyclerViewClickListener item) {
        this.activity = activity;
        this.courseicon = data;
        this.item = item;
    }



    @NotNull
    @Override
    public OrderHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.user_item_order, parent, false);
        return new OrderHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(OrderHolder holder, final int position) {
        holder.tv_product_id.setText(courseicon.get(position).toString());
        holder.textView11.setText(courseicon.get(position).getCreatedDate());
        holder.textView12.setText("Status-" + " "+courseicon.get(position).getOrderStatus());
        holder.textView9.setText("Order -#" + " "+courseicon.get(position).getSchoolOrderId());
        Glide.with(activity).load(Config.imageUrl +courseicon.get(position).getOrderItems().get(0).getClassProduct().getAvatarPath()).into(holder.imageView10);
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
