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
import com.io.bookstores.holder.CoursesHolder;
import com.io.bookstores.holder.SchoolsHolder;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.model.insituteModel.TrendingInstituteDataModel;

import java.util.List;


public class AllSchoolsRvAdapter extends RecyclerView.Adapter<SchoolsHolder> {
    private Activity activity;
    private List<TrendingInstituteDataModel> list;


    public AllSchoolsRvAdapter(FragmentActivity activity, List<TrendingInstituteDataModel> list) {
        this.activity = activity;
        this.list = list;

    }

    @Override
    public SchoolsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_school_list, parent, false);
        return new SchoolsHolder(view);
    }

    @Override
    public void onBindViewHolder(SchoolsHolder holder, final int position) {
        TrendingInstituteDataModel model = list.get(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
