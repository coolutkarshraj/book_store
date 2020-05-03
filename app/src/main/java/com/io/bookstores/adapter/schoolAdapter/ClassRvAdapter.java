package com.io.bookstores.adapter.schoolAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.holder.ClassCategoryHolder;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.classList.DataItem;
import com.io.bookstores.model.classModel.ClassCategoryDataModel;

import java.util.ArrayList;
import java.util.List;


public class ClassRvAdapter extends RecyclerView.Adapter<ClassCategoryHolder> {

    private Activity activity;
    private List<DataItem> list;
    ItemClickListner itemClickListner;


    public ClassRvAdapter(Activity activity, List<DataItem> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public ClassCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_school_detial_list, parent, false);
        return new ClassCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassCategoryHolder holder, final int position) {
        final DataItem model = list.get(position);
        holder.tvClassName.setText(model.getLabel());
        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.putString(LocalStorage.classId, String.valueOf(model.getClassId()));
                itemClickListner.onClick(10);

            }
        });
        holder.cardview_item_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.putString(LocalStorage.classId, String.valueOf(model.getClassId()));
                itemClickListner.onClick(10);

            }
        });


    }

    public void setFilter(List<DataItem> newlist) {
        list = new ArrayList<>();
        list.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
