package com.io.bookstore.holder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;


public class OrderHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
    public TextView tv_product_id;
    public TextView textView11;
    public TextView textView12;
    public TextView textView9;
    public TextView textView13;
    public ImageView imageView10;
    public ImageView iv_editOrder;



    public OrderHolder(View view) {

        super(view);
        tv_product_id = view.findViewById(R.id.textView9);
        textView11 = view.findViewById(R.id.textView11);
        textView12 = view.findViewById(R.id.textView12);
        textView9 = view.findViewById(R.id.textView9);
        textView13 = view.findViewById(R.id.textView13);
        imageView10 = view.findViewById(R.id.imageView10);
        iv_editOrder = view.findViewById(R.id.iv_editOrder);

    }

    @Override
    public void onClick(View view) {
    }
}
