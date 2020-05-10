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
import com.io.bookstores.model.classModel.ClassCategoryDataModel;

import java.util.ArrayList;
import java.util.List;


public class ClassCategoryRvAdapter extends RecyclerView.Adapter<ClassCategoryHolder> {

    private Activity activity;
    private List<ClassCategoryDataModel> list;
    ItemClickListner itemClickListner;
    LocalStorage localStorage;


    public ClassCategoryRvAdapter(Activity activity, List<ClassCategoryDataModel> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public ClassCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_school_detial_list, parent, false);
        localStorage = new LocalStorage(activity);
        return new ClassCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassCategoryHolder holder, final int position) {
        final ClassCategoryDataModel model = list.get(position);
        if (localStorage.getString(LocalStorage.islanguage).equals("kuwait")) {
            holder.tvClassName.setText(model.getArabicName());
        } else {
            holder.tvClassName.setText(model.getName());
        }
        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;
                if (model.getName().equals("Clothes") || model.getName().equals("clothes") || model.getName().equals("clothes")) {
                    StaticData.type = 1;
                } else {
                    StaticData.type = 2;

                }
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.putString(LocalStorage.classCategoryId, String.valueOf(model.getClassCategoryId()));
                itemClickListner.onClick(11);

            }
        });
        holder.cardview_item_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;
               // Toast.makeText(activity, "classCategoryId" + model.getClassCategoryId(), Toast.LENGTH_SHORT).show();
                if (model.getName().equals("Clothes")) {
                    StaticData.type = 1;

                } else {
                    StaticData.type = 2;

                }
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.putString(LocalStorage.classCategoryId, String.valueOf(model.getClassCategoryId()));
                itemClickListner.onClick(11);

            }
        });


    }

    public void setFilter(List<ClassCategoryDataModel> newlist) {
        list = new ArrayList<>();
        list.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
