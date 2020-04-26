package com.io.bookstores.adapter.schoolAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.productModel.ProductSizesItem;

import java.util.List;

public class ItemSizeAdapter extends RecyclerView.Adapter<ItemSizeAdapter.MyViewHolder> {

    private Context mContext;
    private List<ProductSizesItem> mData;
    private ItemClickListner itemClickListner;
    private ItemClickListner recyclerViewClickListener;
    int postionn = -1;
    String name = " ";
    LocalStorage localStorage;


    public ItemSizeAdapter(Context mContext, List<ProductSizesItem> mData, ItemClickListner itemClickListner) {
        this.mContext = mContext;
        this.mData = mData;
        this.itemClickListner = itemClickListner;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_class_subcategory, parent, false);
        localStorage = new LocalStorage(mContext);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductSizesItem data = mData.get(position);
        holder.tv_name.setText(data.getMinSize() + " - " + data.getMaxSize());

        holder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postionn = position;
                itemClickListner.onClick(data.getProductSizeId());
                localStorage.putString(LocalStorage.SIZEEID, String.valueOf(data.getProductSizeId()));
                localStorage.putString(LocalStorage.PQUANTITY, String.valueOf(data.getQuantity()));
                notifyDataSetChanged();
            }
        });
        if (postionn == position) {

            holder.rl_root.setBackgroundResource(R.drawable.selected_btn);
            holder.tv_name.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.rl_root.setBackgroundResource(R.drawable.unselected_btn);
            holder.tv_name.setTextColor(Color.parseColor("#4058595b"));
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        RelativeLayout rl_root;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            rl_root = (RelativeLayout) itemView.findViewById(R.id.rl_root);


        }
    }


}