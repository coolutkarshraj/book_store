package com.io.bookstore.holder;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;


public class OrderHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
    public TextView tv_product_id;


    public OrderHolder(View view) {

        super(view);
        tv_product_id = view.findViewById(R.id.textView9);

    }

    @Override
    public void onClick(View view) {
    }
}
