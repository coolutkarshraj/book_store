package com.io.bookstore.holder;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;


public class AddressHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
    public TextView tv_address;


    public AddressHolder(View view) {

        super(view);
        tv_address = view.findViewById(R.id.tv_address);

    }

    @Override
    public void onClick(View view) {
    }
}
