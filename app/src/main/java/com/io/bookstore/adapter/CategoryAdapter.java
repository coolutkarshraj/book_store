package com.io.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.fragment.CategoryListFragment;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.model.InstituteModel;
import com.io.bookstore.model.categoryModel.CategoryData;
import com.io.bookstore.model.categoryModel.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private Context mContext ;
    private List<CategoryData> mData ;
    private ItemClickListner itemClickListner;

    public CategoryAdapter(Context mContext,List<CategoryData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_category,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_category.setText(mData.get(position).getName());
        Glide.with(mContext).load(Config.imageUrl +mData.get(position).getImagePath()).into(holder.iv_category);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) mContext;
                assert itemClickListner != null;
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
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category = (TextView) itemView.findViewById(R.id.tv_category);
            iv_category = (ImageView) itemView.findViewById(R.id.iv_category);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_category);

        }
    }


}