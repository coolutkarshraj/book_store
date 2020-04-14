package com.io.bookstores.adapter.schoolAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.holder.ClassCategoryHolder;
import com.io.bookstores.holder.ClassGroupHolder;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.classModel.ClassCategoryDataModel;
import com.io.bookstores.model.classModel.ClassDataModel;
import com.io.bookstores.model.insituteModel.TrendingInstituteDataModel;

import java.util.ArrayList;
import java.util.List;


public class ClassCategoryRvAdapter extends RecyclerView.Adapter<ClassCategoryHolder> {

    private Activity activity;
    private List<ClassCategoryDataModel> list;
    ItemClickListner itemClickListner;


    public ClassCategoryRvAdapter(Activity activity, List<ClassCategoryDataModel> list) {
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
        final ClassCategoryDataModel model = list.get(position);
        holder.tvClassName.setText(model.getName());
        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;

                if (model.getName().equals("Clothes")) {
                    StaticData.type = 1;

                } else {
                    StaticData.type = 2;

                }
                itemClickListner.onClick(10);

            }
        });
        holder.cardview_item_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;
                if (model.getName().equals("Clothes")) {
                    StaticData.type = 1;

                } else {
                    StaticData.type = 2;

                }
                itemClickListner.onClick(10);

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
