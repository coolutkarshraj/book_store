package com.io.bookstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.holder.CoursesHolder;
import com.io.bookstore.listeners.RecyclerViewClickListener;
import com.io.bookstore.model.insituteModel.InsituiteDataModel;
import com.io.bookstore.model.insituteModel.TrendingInstituteDataModel;
import com.io.bookstore.utility.Utils;

import java.util.ArrayList;
import java.util.List;


public class CourseAdapter extends RecyclerView.Adapter<CoursesHolder> {
    private Activity activity;
    private List<TrendingInstituteDataModel> list;
    private RecyclerViewClickListener item;


    public CourseAdapter(FragmentActivity activity, List<TrendingInstituteDataModel> list, RecyclerViewClickListener item) {
        this.activity = activity;
        this.list = list;
        this.item = item;
    }

    @Override
    public CoursesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_view__courses, parent, false);
        return new CoursesHolder(view);
    }

    @Override
    public void onBindViewHolder(CoursesHolder holder, final int position) {
        TrendingInstituteDataModel model = list.get(position);
        holder.name.setText(model.getInstituteName());
        Glide.with(activity).load(Config.imageUrl + model.getAvatarPath()).into(holder.image);
        holder.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.onClickPosition(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list.size() > 4) {
            count = 4;
        } else {
            count = list.size();
        }
        return count;
    }
}
