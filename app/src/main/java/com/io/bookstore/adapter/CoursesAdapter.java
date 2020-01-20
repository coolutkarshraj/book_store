package com.io.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;
import com.io.bookstore.listeners.RecyclerViewClickListener;
import com.io.bookstore.model.CourseModel;
import com.io.bookstore.model.courseModel.CourseDataModel;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {

    private Context mContext ;
    private List<CourseDataModel> mData ;
    RecyclerViewClickListener item;


    public CoursesAdapter(Context mContext, List<CourseDataModel> mData,RecyclerViewClickListener item) {
        this.mContext = mContext;
        this.mData = mData;
        this.item = item;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean flag=false;
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_courses,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        CourseDataModel model = mData.get(position);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        holder.tv_courses_tilte.setText(model.getCourseName());
        holder.tv_courses_desc.setText(model.getCourseDescription());
       /* holder.iv_courses_thumbnail.setImageResource(mData.get(position).getThumbnail());*/
        holder.bv_course_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             item.onClickPosition(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_courses_tilte,tv_courses_desc;
        ImageView iv_courses_thumbnail;
        Button bv_course_browse;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_courses_tilte = (TextView) itemView.findViewById(R.id.tv_course_tilte);
            tv_courses_desc =  (TextView) itemView.findViewById(R.id.tv_course_desc) ;
            iv_courses_thumbnail = (ImageView) itemView.findViewById(R.id.iv_course_thumbnail);
            bv_course_browse = (Button) itemView.findViewById(R.id.bv_course_browse);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_course);

        }
    }


}