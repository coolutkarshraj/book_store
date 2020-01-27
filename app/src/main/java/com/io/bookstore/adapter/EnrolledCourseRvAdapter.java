package com.io.bookstore.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.listeners.RecyclerViewClickListener;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.courseModel.CourseDataModel;
import com.io.bookstore.model.courseModel.CourseDetialResponseModel;
import com.io.bookstore.model.courseModel.EnrollCourseDataModel;
import com.io.bookstore.model.courseModel.EnrolledCourseListDataModel;
import com.io.bookstore.model.courseModel.EnrolledCourseListResponseModel;
import com.io.bookstore.utility.Utils;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class EnrolledCourseRvAdapter extends RecyclerView.Adapter<EnrolledCourseRvAdapter.MyViewHolder> {

    private Context mContext;
    private List<EnrolledCourseListDataModel> item;


    public EnrolledCourseRvAdapter(Context mContext, List<EnrolledCourseListDataModel> item) {
        this.mContext = mContext;
        this.item = item;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean flag = false;
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.layout_add_course, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        EnrolledCourseListDataModel model = item.get(position);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));
        getCourseDetial(model.getCourseId(), holder.tv_courses_tilte, holder.tv_courses_desc, holder.iv_courses_thumbnail);

        /* holder.iv_courses_thumbnail.setImageResource(mData.get(position).getThumbnail());*/


    }

    private void getCourseDetial(Integer courseId, final TextView tv_courses_tilte, final TextView tv_courses_desc, final ImageView iv_courses_thumbnail) {
        ApiCaller.courseDetial((Activity) mContext, Config.Url.coursedetial + courseId,
                new FutureCallback<CourseDetialResponseModel>() {

                    @Override
                    public void onCompleted(Exception e, CourseDetialResponseModel result) {
                        if (e != null) {
                            Utils.showAlertDialog((Activity) mContext, "Something Went Wrong");
                        }
                        if (result != null) {
                            if (result.getStatus() == 1) {
                                tv_courses_tilte.setText(result.getData().getCourseName());
                                tv_courses_desc.setText(result.getData().getCourseDescription());
                                Glide.with(mContext).load(Config.imageUrl + result.getData().getAvatarPath()).into(iv_courses_thumbnail);
                            }
                        }


                    }
                });
    }
    public void setFilter(List<EnrolledCourseListDataModel> newlist){
        item=new ArrayList<>();
        item.addAll(newlist);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_courses_tilte, tv_courses_desc;
        ImageView iv_courses_thumbnail;
        Button bv_course_browse;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_courses_tilte = (TextView) itemView.findViewById(R.id.tv_course_tilte);
            tv_courses_desc = (TextView) itemView.findViewById(R.id.tv_course_desc);
            iv_courses_thumbnail = (ImageView) itemView.findViewById(R.id.iv_course_thumbnail);
            bv_course_browse = (Button) itemView.findViewById(R.id.bv_course_browse);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_course);

        }
    }


}