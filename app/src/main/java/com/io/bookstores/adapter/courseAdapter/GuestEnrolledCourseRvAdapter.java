package com.io.bookstores.adapter.courseAdapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.model.guestCoursedModel.DataItem;

import java.util.List;

public class GuestEnrolledCourseRvAdapter extends RecyclerView.Adapter<GuestEnrolledCourseRvAdapter.MyViewHolder> {

    private Context mContext;
    private List<DataItem> item;


    public GuestEnrolledCourseRvAdapter(Context mContext, List<DataItem> item) {
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
        DataItem model = item.get(position);
        //  holder.cardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));
        holder.tv_courses_tilte.setText(model.getCourse().getCourseName());
        holder.tv_courses_desc.setText(model.getCourse().getCourseDescription());
        Glide.with(mContext).load(Config.imageUrl + model.getCourse().getAvatarPath()).into(holder.iv_courses_thumbnail);

        holder.bv_course_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                final ImageView clear = (ImageView) dialog.findViewById(R.id.clear);
                final TextView etAddress = (TextView) dialog.findViewById(R.id.tv_cname);
                final TextView etPinCode = (TextView) dialog.findViewById(R.id.tv_cd);
                final TextView tv_cprice = (TextView) dialog.findViewById(R.id.tv_cprice);
                etAddress.setText(item.get(position).getCourse().getCourseName());
                etPinCode.setText(item.get(position).getCourse().getCourseDescription());
                tv_cprice.setText("" + item.get(position).getCourse().getPrice());

                btn_Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        /* holder.iv_courses_thumbnail.setImageResource(mData.get(position).getThumbnail());*/


    }


    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_courses_tilte, tv_courses_desc;
        ImageView iv_courses_thumbnail;
        Button bv_course_location;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_courses_tilte = (TextView) itemView.findViewById(R.id.tv_course_tilte);
            tv_courses_desc = (TextView) itemView.findViewById(R.id.tv_course_desc);
            iv_courses_thumbnail = (ImageView) itemView.findViewById(R.id.iv_course_thumbnail);
            bv_course_location = (Button) itemView.findViewById(R.id.bv_course_location);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_course);

        }
    }


}