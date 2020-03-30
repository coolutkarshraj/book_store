package com.io.bookstores.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.model.filterStore.Datum;

import java.util.List;

public class BookstoresFilterAdapter extends RecyclerView.Adapter<BookstoresFilterAdapter.MyViewHolder> {

    private Context mContext ;
    List<Datum> mData;
    private ItemClickListner itemClickListner;
    private String Lat;
    private String Longi;


    public BookstoresFilterAdapter(Context mContext, List<Datum> mData) {
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

        holder.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lat = mData.get(position).getAddress().getLat();
                Longi = mData.get(position).getAddress().getLong();

            if (Lat.equals("") || Lat == null && Longi.equals("") || Longi == null) {
                    final Dialog dialog = new Dialog(mContext);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;
                    dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.activity_add_adress_new);
                    dialog.setTitle("");
                    final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);
                    final Button btn_Add = (Button) dialog.findViewById(R.id.btn_Add);
                    //final Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                    final TextView etAddress = (TextView) dialog.findViewById(R.id.et_address);
                    final TextView etCity = (TextView) dialog.findViewById(R.id.et_city);
                    final TextView etState = (TextView) dialog.findViewById(R.id.et_state);
                    final TextView etPinCode = (TextView) dialog.findViewById(R.id.et_pincode);
                    etAddress.setText(mData.get(position).getName());
                    etState.setText(mData.get(position).getEmail());
                    etCity.setText(String.valueOf(mData.get(position).getPhone()));
                    etPinCode.setText(mData.get(position).getAddress().getAddress() + "," +
                            mData.get(position).getAddress().getCity() + "," + mData.get(position).getAddress().getLandmark());

                    btn_Add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                        }
                    });


                    Clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();
                        }
                    });
                    dialog.show();
               } else {
                //Uri gmmIntentUri = Uri.parse("geo:" + Lat + "," + Longi + "");
                Uri navigationIntentUri = Uri.parse("google.navigation:b=" + Lat + "," + Longi);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    mContext.startActivity(mapIntent);
                }


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
        Button btn_browese, button4;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_bookstore_tilte = (TextView) itemView.findViewById(R.id.tv_bookstore_tilte);
            tv_bookstore_desc =  (TextView) itemView.findViewById(R.id.tv_bookstore_desc) ;
            iv_bookstore_thumbnail = (ImageView) itemView.findViewById(R.id.iv_bookstore_thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_bookstore);
            btn_browese = (Button) itemView.findViewById(R.id.btn_browese);
            button4 = (Button) itemView.findViewById(R.id.button4);

        }
    }


}