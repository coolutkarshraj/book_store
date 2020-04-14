package com.io.bookstores.adapter.schoolAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.holder.ClothHolder;
import com.io.bookstores.holder.SchoolBooksHolder;
import com.io.bookstores.model.insituteModel.TrendingInstituteDataModel;
import com.io.bookstores.model.staticModel.ClothModel;

import java.util.List;


public class StudentBooksRvAdapter extends RecyclerView.Adapter<SchoolBooksHolder> {

    private Activity activity;
    private List<ClothModel> list;


    public StudentBooksRvAdapter(Activity activity, List<ClothModel> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public SchoolBooksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_student_books, parent, false);
        return new SchoolBooksHolder(view);
    }

    @Override
    public void onBindViewHolder(SchoolBooksHolder holder, final int position) {
        ClothModel model = list.get(position);
        holder.ivClothImage.setBackgroundResource(model.getImage());
        holder.tv_price.setText(model.getName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
