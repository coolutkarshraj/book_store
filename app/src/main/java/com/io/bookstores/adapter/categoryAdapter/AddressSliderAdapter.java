package com.io.bookstores.adapter.categoryAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.model.dilvery.DilveryAddressDataModel;

import java.util.List;

public class AddressSliderAdapter extends RecyclerView.Adapter<AddressSliderAdapter.MyViewHolder> {

    private Context mContext ;
    private List<DilveryAddressDataModel> mData;
    private ItemClickListner itemClickListner;

    public AddressSliderAdapter(Context mContext, List<DilveryAddressDataModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_address_slide,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DilveryAddressDataModel model = mData.get(position);
        if (StaticData.selectedLanguage.equals("kuwait")) {
            holder.tv_name.setText(model.getArabicName());
        } else {
            holder.tv_name.setText(model.getName());
        }
       // Toast.makeText(mContext, ""+model.getCity(), Toast.LENGTH_SHORT).show();
        holder.ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.address = model.getName();
                itemClickListner = (ItemClickListner)mContext;
                assert itemClickListner != null;
                itemClickListner.onClick(4);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        LinearLayout ll_layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name_a);
            ll_layout = (LinearLayout) itemView.findViewById(R.id.ll_layout);

        }
    }


}