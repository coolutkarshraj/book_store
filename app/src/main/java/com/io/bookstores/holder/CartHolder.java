package com.io.bookstores.holder;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;


public class CartHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
    public TextView tv_product_name,tv_price,tv_price1,textView7,textView8;
    public ImageView imageView5,imageView9,iv_UpdateQuantity,mark_fav,mark_fav_red;
    public Button btnminus,btnplus;
    public LinearLayout ll_layout;


    public CartHolder(View view) {
        super(view);
        tv_product_name = view.findViewById(R.id.tv_product_name);
        tv_price = view.findViewById(R.id.tv_price);
        tv_price1 = view.findViewById(R.id.tv_price1);
        textView7 = view.findViewById(R.id.textView7);
        imageView5 = view.findViewById(R.id.imageView5);
        imageView9 = view.findViewById(R.id.imageView9);
        textView8 = view.findViewById(R.id.tv_qty);
        btnminus = view.findViewById(R.id.iv_minus);
        btnplus = view.findViewById(R.id.iv_plus);
        ll_layout = view.findViewById(R.id.ll_layout);
        mark_fav = (ImageView) itemView.findViewById(R.id.mark_fav);
        mark_fav_red = (ImageView) itemView.findViewById(R.id.mark_fav_red);
       // iv_UpdateQuantity = view.findViewById(R.id.imageView8);

    }

    @Override
    public void onClick(View view) {
    }
}
