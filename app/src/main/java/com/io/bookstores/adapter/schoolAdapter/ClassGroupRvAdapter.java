package com.io.bookstores.adapter.schoolAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.holder.ClassGroupHolder;
import com.io.bookstores.model.classModel.ClassDataModel;
import com.io.bookstores.model.insituteModel.TrendingInstituteDataModel;
import com.io.bookstores.model.schoolModel.GetAllSchoolDataModel;

import java.util.ArrayList;
import java.util.List;


public class ClassGroupRvAdapter extends RecyclerView.Adapter<ClassGroupHolder> {
    private Activity activity;
    private List<ClassDataModel> list;


    public ClassGroupRvAdapter(Activity activity, List<ClassDataModel> list) {
        this.activity = activity;
        this.list = list;

    }

    @Override
    public ClassGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_classes, parent, false);
        return new ClassGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassGroupHolder holder, final int position) {
        ClassDataModel model = list.get(position);
        holder.tvClassName.setText(model.getName());
    }

    public void setFilter(List<ClassDataModel> newlist) {
        list = new ArrayList<>();
        list.addAll(newlist);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
