package com.io.bookstores.adapter.schoolAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.model.classModel.ClassSubCategoryDataModel;

import java.util.List;

public class ClassSubCategoryRvAdapter extends RecyclerView.Adapter<ClassSubCategoryRvAdapter.MyViewHolder> {

    private Context mContext;
    private List<ClassSubCategoryDataModel> mData;
    private ItemClickListner itemClickListner;
    private RecyclerViewClickListener recyclerViewClickListener;
    int postionn = 0;


    public ClassSubCategoryRvAdapter(Context mContext, List<ClassSubCategoryDataModel> mData, RecyclerViewClickListener recyclerViewClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_class_subcategory, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ClassSubCategoryDataModel data = mData.get(position);
        holder.tv_name.setText(data.getName());
        holder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postionn = position;
              //  Toast.makeText(mContext, "sub id" + data.getClassSubCategoryId(), Toast.LENGTH_SHORT).show();
                recyclerViewClickListener.onClickPosition(data.getClassSubCategoryId());
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