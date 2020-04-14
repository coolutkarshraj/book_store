package com.io.bookstores.holder;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.io.bookstores.R;


public class ClassCategoryHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    public TextView tvClassName;
    public RelativeLayout rlRoot;
    public MaterialCardView cardview_item_category;


    public ClassCategoryHolder(View view) {

        super(view);
        tvClassName = view.findViewById(R.id.tv_class_name_detial);
        rlRoot = view.findViewById(R.id.card_view);
        cardview_item_category = view.findViewById(R.id.cardview_item_category);

    }

    @Override
    public void onClick(View view) {
    }
}
