package com.io.bookstores.adapter.categoryAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.categoryModel.CategoryData;

import java.util.List;

public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.MyViewHolder> {

    private Context mContext;
    private List<CategoryData> mData;
    private ItemClickListner itemClickListner;

    public CategoryGridAdapter(Context mContext, List<CategoryData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_category_grid, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (StaticData.selectedLanguage.equals("kuwait")) {
            holder.tv_category.setText(mData.get(position).getArabicName());
        } else {
            holder.tv_category.setText(mData.get(position).getName());
        }
        Glide.with(mContext).load(Config.imageUrl + mData.get(position).getImagePath()).into(holder.iv_category);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.filter = false;
                itemClickListner = (ItemClickListner) mContext;
                LocalStorage localStorage = new LocalStorage(mContext);
                assert itemClickListner != null;
                localStorage.putString(LocalStorage.CategoryId, String.valueOf(mData.get(position).getCategoryId()));
                itemClickListner.onClick(3);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.filter = false;
                itemClickListner = (ItemClickListner) mContext;
                LocalStorage localStorage = new LocalStorage(mContext);
                assert itemClickListner != null;
                localStorage.putString(LocalStorage.CategoryId, String.valueOf(mData.get(position).getCategoryId()));
                itemClickListner.onClick(3);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_category;
        ImageView iv_category;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category = (TextView) itemView.findViewById(R.id.tv_category);
            iv_category = (ImageView) itemView.findViewById(R.id.iv_category);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_category);

        }
    }


}