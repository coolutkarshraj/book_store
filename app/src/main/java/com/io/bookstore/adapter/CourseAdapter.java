package com.io.bookstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;
import com.io.bookstore.holder.CoursesHolder;
import com.io.bookstore.utility.Utils;

import java.util.ArrayList;


public class CourseAdapter extends RecyclerView.Adapter<CoursesHolder> {
    private Activity activity;
    private ArrayList coursename;
    private ArrayList courseicon;

    public CourseAdapter(Activity activity, ArrayList coursename, ArrayList courseicon) {
        this.activity = activity;
        this.courseicon = courseicon;
        this.coursename = coursename;
    }

    @Override
    public CoursesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_view__courses, parent, false);
        return new CoursesHolder(view);
    }

    @Override
    public void onBindViewHolder(CoursesHolder holder, int position) {
        holder.name.setText(coursename.get(position).toString());
        holder.image.setImageResource((Integer) courseicon.get(position));
    }
    @Override
    public int getItemCount() {
        return courseicon.size();
    }
}
