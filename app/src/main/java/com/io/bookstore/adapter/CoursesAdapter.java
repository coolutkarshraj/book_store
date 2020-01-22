package com.io.bookstore.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
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
        Glide.with(mContext).load(Config.imageUrl + model.getAvatarPath()).into(holder.iv_courses_thumbnail);
       /* holder.iv_courses_thumbnail.setImageResource(mData.get(position).getThumbnail());*/
        holder.bv_course_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             item.onClickPosition(position);
            }
        });
        holder.bv_course_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.course_dialog);
                dialog.setTitle("");
                final Button btn_Add = (Button) dialog.findViewById(R.id.yes);
                final Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                final TextView etAddress = (TextView) dialog.findViewById(R.id.tv_cname);
                final TextView etPinCode = (TextView) dialog.findViewById(R.id.tv_cd);
                etAddress.setText(mData.get(position).getCourseName());
                etPinCode.setText(mData.get(position).getCourseDescription() );

                btn_Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });


                dialog.show();
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
        Button bv_course_browse,bv_course_location;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_courses_tilte = (TextView) itemView.findViewById(R.id.tv_course_tilte);
            tv_courses_desc =  (TextView) itemView.findViewById(R.id.tv_course_desc) ;
            iv_courses_thumbnail = (ImageView) itemView.findViewById(R.id.iv_course_thumbnail);
            bv_course_browse = (Button) itemView.findViewById(R.id.bv_course_browse);
            bv_course_location = (Button) itemView.findViewById(R.id.bv_course_location);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_course);

        }
    }


}