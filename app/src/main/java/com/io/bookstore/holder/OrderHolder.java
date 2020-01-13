package com.io.bookstore.holder;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;


public class OrderHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
    public TextView tv_product_id;
    public TextView textView11;
    public TextView textView12;
    public TextView textView9;


    public OrderHolder(View view) {

        super(view);
        tv_product_id = view.findViewById(R.id.textView9);
        textView11 = view.findViewById(R.id.textView11);
        textView12 = view.findViewById(R.id.textView12);
        textView9 = view.findViewById(R.id.textView9);

    }

    @Override
    public void onClick(View view) {
    }
}
