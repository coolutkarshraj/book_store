package com.io.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.model.storeModel.Datum;

import java.util.List;

public class BookstoresFilterAdapter extends RecyclerView.Adapter<BookstoresFilterAdapter.MyViewHolder> {

    private Context mContext ;
    List<com.io.bookstore.model.filterByAddress.Datum> mData;
    private ItemClickListner itemClickListner;

    public BookstoresFilterAdapter(Context mContext, List<com.io.bookstore.model.filterByAddress.Datum> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean flag=false;
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_bookstore,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_bookstore_tilte.setText(mData.get(position).getName());
       // holder.tv_bookstore_desc.setText(mData.get(position).getDescription());
        Glide.with(mContext).load(Config.imageUrl +mData.get(position).getAvatarPath()).into(holder.iv_bookstore_thumbnail);
        holder.btn_browese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClickListner = (ItemClickListner) mContext;
                itemClickListner.onClick(2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_bookstore_tilte,tv_bookstore_desc;
        ImageView iv_bookstore_thumbnail;
        CardView cardView ;
        Button btn_browese;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_bookstore_tilte = (TextView) itemView.findViewById(R.id.tv_bookstore_tilte);
            tv_bookstore_desc =  (TextView) itemView.findViewById(R.id.tv_bookstore_desc) ;
            iv_bookstore_thumbnail = (ImageView) itemView.findViewById(R.id.iv_bookstore_thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_bookstore);
            btn_browese = (Button) itemView.findViewById(R.id.btn_browese);

        }
    }


}