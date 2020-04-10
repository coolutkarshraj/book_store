package com.io.bookstores.holder;


import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;


public class ClassHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
    public TextView tvClassName;
    public RelativeLayout rlRoot;


    public ClassHolder(View view) {

        super(view);
        tvClassName = view.findViewById(R.id.tv_class_name);

        rlRoot = view.findViewById(R.id.rl_root);

    }

    @Override
    public void onClick(View view) {
    }
}
