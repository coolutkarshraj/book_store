package com.io.bookstores.holder;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;


public class SchoolDetailsHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    public TextView tv_School_Detail_;
    public ImageView iv_school_icon;


    public SchoolDetailsHolder(View view) {

        super(view);
       // tv_School_Detail_ = view.findViewById(R.id.tv_School_Detail_);
        iv_school_icon = view.findViewById(R.id.iv_school_detail);

    }

    @Override
    public void onClick(View view) {
    }
}
