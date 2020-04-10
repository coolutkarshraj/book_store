package com.io.bookstores.adapter.schoolAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.holder.SchoolCategoriesHolder;
import com.io.bookstores.model.insituteModel.TrendingInstituteDataModel;

import java.util.List;


public class SchoolCategoriesRvAdapter extends RecyclerView.Adapter<SchoolCategoriesHolder> {
    private Activity activity;
    private List<TrendingInstituteDataModel> list;


    public SchoolCategoriesRvAdapter(FragmentActivity activity, List<TrendingInstituteDataModel> list) {
        this.activity = activity;
        this.list = list;

    }

    @Override
    public SchoolCategoriesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_classes, parent, false);
        return new SchoolCategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(SchoolCategoriesHolder holder, final int position) {
        TrendingInstituteDataModel model = list.get(position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
