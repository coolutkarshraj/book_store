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
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.bookListModel.Datum;
import com.io.bookstores.model.insituteModel.TrendingInstituteDataModel;
import com.io.bookstores.model.schoolModel.GetAllSchoolDataModel;

import java.util.ArrayList;
import java.util.List;


public class AllSchoolsRvAdapter extends RecyclerView.Adapter<SchoolsHolder> {
    private Activity activity;
    private List<GetAllSchoolDataModel> list;
    private ItemClickListner itemClickListner;


    public AllSchoolsRvAdapter(Activity activity, List<GetAllSchoolDataModel> list) {
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
        GetAllSchoolDataModel model = list.get(position);
        holder.tvSchoolName.setText(model.getSchoolName());
        holder.tvSchoolDesc.setText(model.getDescription());
        Glide.with(activity).load(Config.imageUrl + model.getAvatarPath()).into(holder.iv_school_logo);
        holder.btn_browese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;
                itemClickListner.onClick(8);
            }
        });

    }

    public void setFilter(List<GetAllSchoolDataModel> newlist) {
        list = new ArrayList<>();
        list.addAll(newlist);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
