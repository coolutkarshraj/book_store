package com.io.bookstores.holder;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;


public class SchoolsHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
    public TextView tvSchoolName;
    public TextView tvSchoolDesc;
    public ImageView iv_school_logo;
    public Button btn_browese,btnDetail;
    public RadioButton rv_select_Address;


    public SchoolsHolder(View view) {

        super(view);
        btn_browese = view.findViewById(R.id.btn_browese);
        btnDetail = view.findViewById(R.id.button4);
        tvSchoolDesc = view.findViewById(R.id.tv_school_description);
        tvSchoolName = view.findViewById(R.id.tv_school_name);
        iv_school_logo = view.findViewById(R.id.iv_school_logo);

    }

    @Override
    public void onClick(View view) {
    }
}
