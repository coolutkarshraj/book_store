package com.io.bookstores.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;


/**
 * Created by Utkarsh on 10-12-2019.
 */

public class DiscoverMoreBookHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    public TextView tvTitle;
    public TextView tvMessage;
    public ImageView ivNotification;
    public TextView tvPrice;
    public TextView tvIconText;
    public TextView tvDate;

    public DiscoverMoreBookHolder(View view) {
        super(view);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvMessage = view.findViewById(R.id.tvMessage);
        ivNotification = view.findViewById(R.id.ivNotification);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvIconText = view.findViewById(R.id.tvIconText);
        tvDate = view.findViewById(R.id.tvDate);
    }

    @Override
    public void onClick(View v) {

    }
}
