package com.io.bookstores.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;

public class StoresHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {

    public ImageView image;
    public TextView name;

    public StoresHolder(View view) {

        super(view);
        image = view.findViewById(R.id.iv_image);
        name = view.findViewById(R.id.tv_vec_name);

    }

    @Override
    public void onClick(View view) {

    }
}
