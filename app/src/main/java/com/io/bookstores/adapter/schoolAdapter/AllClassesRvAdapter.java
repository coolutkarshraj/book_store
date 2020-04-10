package com.io.bookstores.adapter.schoolAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.holder.ClassHolder;
import com.io.bookstores.holder.CoursesHolder;
import com.io.bookstores.model.insituteModel.TrendingInstituteDataModel;

import java.util.List;


public class AllClassesRvAdapter extends RecyclerView.Adapter<ClassHolder> {
    private Activity activity;
    private List<TrendingInstituteDataModel> list;


    public AllClassesRvAdapter(FragmentActivity activity, List<TrendingInstituteDataModel> list) {
        this.activity = activity;
        this.list = list;

    }

    @Override
    public ClassHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_classes, parent, false);
        return new ClassHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassHolder holder, final int position) {
        TrendingInstituteDataModel model = list.get(position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
