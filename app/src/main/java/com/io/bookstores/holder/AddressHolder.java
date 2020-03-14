package com.io.bookstores.holder;


import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;


public class AddressHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
    public TextView tv_address;
    public TextView tv_name;
    public TextView tv_phone;
    public RadioButton rv_select_Address;


    public AddressHolder(View view) {

        super(view);
        tv_address = view.findViewById(R.id.tv_address);
        tv_name = view.findViewById(R.id.tv_name);
        tv_phone = view.findViewById(R.id.tv_phone);
        rv_select_Address = view.findViewById(R.id.rv_select_Address);

    }

    @Override
    public void onClick(View view) {
    }
}
