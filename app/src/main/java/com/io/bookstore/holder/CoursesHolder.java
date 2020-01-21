package com.io.bookstore.holder;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;


public class CoursesHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
    public ImageView image;
    public TextView name;
    public LinearLayout ll_view;


    public CoursesHolder(View view) {

        super(view);
        image = view.findViewById(R.id.iv_image);
        name = view.findViewById(R.id.tv_vec_name);
        ll_view = view.findViewById(R.id.ll_view);

    }

    @Override
    public void onClick(View view) {
    }
}
