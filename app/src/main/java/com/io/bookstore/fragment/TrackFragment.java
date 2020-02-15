package com.io.bookstore.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.io.bookstore.R;

import static com.io.bookstore.R.drawable.button_shape_red;


public class TrackFragment extends Fragment {


    private String orderStatus;
    private Activity activity;
    private CheckBox checkBox0, checkBox1, checkBox2, checkBox3, checkBox4;
    private ImageView view0, view1, view2, view3, view4;
    private TextView t0,t1,t2,t3,t4;

    public TrackFragment(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tracker, container, false);
        intializeViews(view);
        bindListner();
        startWorking();
        return view;
    }


    private void intializeViews(View view) {
        activity = getActivity();
        checkBox0 = view.findViewById(R.id.checkbox0);
        checkBox1 = view.findViewById(R.id.checkbox1);
        checkBox2 = view.findViewById(R.id.checkbox2);
        checkBox3 = view.findViewById(R.id.checkbox3);
        checkBox4 = view.findViewById(R.id.checkbox4);
        view0 = view.findViewById(R.id.view0);
        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);
        t0 = view.findViewById(R.id.tv_pending);
        t1 = view.findViewById(R.id.tv_accepted);
        t2 = view.findViewById(R.id.tv_shipped);
        t3 = view.findViewById(R.id.tv_out_for_dilvery);
        t4 = view.findViewById(R.id.tv_delivered);
    }

    private void bindListner() {
    }

    @SuppressLint("ResourceAsColor")
    private void startWorking() {
        if (orderStatus.equals("Pending")) {
            checkBox0.setChecked(true);
            t0.setTextColor(ContextCompat.getColor(activity, R.color.blue));

        } else if (orderStatus.equals("Accepted")) {
            checkBox0.setChecked(true);
            t0.setTextColor(ContextCompat.getColor(activity, R.color.blue));

            checkBox1.setChecked(true);
            Glide.with(activity).load(button_shape_red).into(view0);
            
            t1.setTextColor(ContextCompat.getColor(activity, R.color.blue));

        } else if (orderStatus.equals("Shipped")) {
            checkBox0.setChecked(true);
            t0.setTextColor(ContextCompat.getColor(activity, R.color.blue));


            checkBox1.setChecked(true);
            Glide.with(activity).load(button_shape_red).into(view0);
            t1.setTextColor(ContextCompat.getColor(activity, R.color.blue));

            checkBox2.setChecked(true);
            view1.getBackground().setColorFilter(Color.parseColor("#839d68"), PorterDuff.Mode.SRC_ATOP);
            t2.setTextColor(ContextCompat.getColor(activity, R.color.blue));

        }else if(orderStatus.equals("Out For Delivery")){
            checkBox0.setChecked(true);
            t0.setTextColor(ContextCompat.getColor(activity, R.color.blue));


            checkBox1.setChecked(true);
            Glide.with(activity).load(button_shape_red).into(view0);
            t1.setTextColor(ContextCompat.getColor(activity, R.color.blue));

            checkBox2.setChecked(true);
            view1.getBackground().setColorFilter(Color.parseColor("#839d68"), PorterDuff.Mode.SRC_ATOP);
            t2.setTextColor(ContextCompat.getColor(activity, R.color.blue));

            checkBox3.setChecked(true);
            t3.setTextColor(ContextCompat.getColor(activity, R.color.blue));
            view2.getBackground().setColorFilter(Color.parseColor("#839d68"), PorterDuff.Mode.SRC_ATOP);

        }else if(orderStatus.equals("Delivered")){
            checkBox0.setChecked(true);
            t0.setTextColor(ContextCompat.getColor(activity, R.color.blue));


            checkBox1.setChecked(true);
            Glide.with(activity).load(button_shape_red).into(view0);
            t1.setTextColor(ContextCompat.getColor(activity, R.color.blue));

            checkBox2.setChecked(true);
            view1.getBackground().setColorFilter(Color.parseColor("#839d68"), PorterDuff.Mode.SRC_ATOP);
            t2.setTextColor(ContextCompat.getColor(activity, R.color.blue));

            checkBox3.setChecked(true);
            t3.setTextColor(ContextCompat.getColor(activity, R.color.blue));
            view2.getBackground().setColorFilter(Color.parseColor("#839d68"), PorterDuff.Mode.SRC_ATOP);

            checkBox4.setChecked(true);
            t4.setTextColor(ContextCompat.getColor(activity, R.color.blue));
            view3.getBackground().setColorFilter(Color.parseColor("#839d68"), PorterDuff.Mode.SRC_ATOP);
        }
    }


}
