package com.io.bookstore.holder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;


public class CartHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
    public TextView tv_product_name,tv_price,tv_price1,textView7;
    public ImageView imageView5,imageView9;


    public CartHolder(View view) {

        super(view);
        tv_product_name = view.findViewById(R.id.tv_product_name);
        tv_price = view.findViewById(R.id.tv_price);
        tv_price1 = view.findViewById(R.id.tv_price1);
        textView7 = view.findViewById(R.id.textView7);
        imageView5 = view.findViewById(R.id.imageView5);
        imageView9 = view.findViewById(R.id.imageView9);

    }

    @Override
    public void onClick(View view) {
    }
}
