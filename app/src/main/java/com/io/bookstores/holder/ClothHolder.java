package com.io.bookstores.holder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;


public class ClothHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    public TextView tv_cloth_size,tv_price;
    public ImageView ivClothImage,mark_fav,mark_fav_red,mark_cart;


    public ClothHolder(View view) {

        super(view);
        tv_price = view.findViewById(R.id.tv_price);
        tv_cloth_size = view.findViewById(R.id.tv_cloth_size);
        ivClothImage = view.findViewById(R.id.iv_image);
        mark_fav = view.findViewById(R.id.mark_fav);
        mark_fav_red = view.findViewById(R.id.mark_fav_red);
        mark_cart = view.findViewById(R.id.mark_cart);

    }

    @Override
    public void onClick(View view) {
    }
}
