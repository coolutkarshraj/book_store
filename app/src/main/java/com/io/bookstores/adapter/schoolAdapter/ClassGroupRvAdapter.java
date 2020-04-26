package com.io.bookstores.adapter.schoolAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.holder.ClassGroupHolder;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.classModel.ClassDataModel;

import java.util.ArrayList;
import java.util.List;


public class ClassGroupRvAdapter extends RecyclerView.Adapter<ClassGroupHolder> {
    private Activity activity;
    private List<ClassDataModel> list;
    private ItemClickListner itemClickListner;


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
        final ClassDataModel model = list.get(position);
        holder.tvClassName.setText(model.getName());
        holder.rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;
                LocalStorage localStorage = new LocalStorage(activity);
               // Toast.makeText(activity, "classgroupId" + model.getClassGroupId(), Toast.LENGTH_SHORT).show();
                localStorage.putString(LocalStorage.classGroupId, String.valueOf(model.getClassGroupId()));
                localStorage.putString(LocalStorage.TYPE, "school");
                itemClickListner.onClick(9);
            }
        });
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
