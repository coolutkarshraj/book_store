package com.io.bookstores.adapter.homeAdapter;

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
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.insituteModel.TrendingInstituteDataModel;

import java.util.List;


public class HomeCourseRvAdapter extends RecyclerView.Adapter<CoursesHolder> {
    private Activity activity;
    private List<TrendingInstituteDataModel> list;
    private RecyclerViewClickListener item;
    LocalStorage localStorage;


    public HomeCourseRvAdapter(FragmentActivity activity, List<TrendingInstituteDataModel> list, RecyclerViewClickListener item) {
        this.activity = activity;
        this.list = list;
        this.item = item;
    }

    @Override
    public CoursesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_view__courses, parent, false);
        localStorage = new LocalStorage(activity);
        return new CoursesHolder(view);
    }

    @Override
    public void onBindViewHolder(CoursesHolder holder, final int position) {
        TrendingInstituteDataModel model = list.get(position);
        if (localStorage.getString(LocalStorage.islanguage).equals("kuwait")) {
            holder.name.setText(model.getArabicName());
        } else {
            holder.name.setText(model.getInstituteName());
        }

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
