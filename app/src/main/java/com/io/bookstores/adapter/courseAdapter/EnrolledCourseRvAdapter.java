package com.io.bookstores.adapter.courseAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.courseModel.CourseDetialResponseModel;
import com.io.bookstores.model.courseModel.EnrolledCourseListDataModel;
import com.io.bookstores.utility.Utils;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

public class EnrolledCourseRvAdapter extends RecyclerView.Adapter<EnrolledCourseRvAdapter.MyViewHolder> {

    private Context mContext;
    private List<EnrolledCourseListDataModel> item;
    String name = "", descrption = "";
    int price;


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
        //  holder.cardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));
        getCourseDetial(model.getCourseId(), holder.tv_courses_tilte, holder.tv_courses_desc, holder.iv_courses_thumbnail);

        /* holder.iv_courses_thumbnail.setImageResource(mData.get(position).getThumbnail());*/
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
                etAddress.setText(name);
                etPinCode.setText(descrption);
                tv_cprice.setText(""+price);

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

    }

    private void getCourseDetial(Integer courseId, final TextView tv_courses_tilte, final TextView tv_courses_desc, final ImageView iv_courses_thumbnail) {
        final LocalStorage localStorage = new LocalStorage(mContext);
        ApiCaller.courseDetial((Activity) mContext, Config.Url.coursedetial + courseId,
                new FutureCallback<CourseDetialResponseModel>() {

                    @Override
                    public void onCompleted(Exception e, CourseDetialResponseModel result) {
                        if (e != null) {
                            Utils.showAlertDialog((Activity) mContext, "Something Went Wrong");
                        }

                        if(result != null){
                            if(result.getStatus()== null){
                                if(result.getMessage().equals("Unauthorized")){
                                    Utils.showAlertDialogLogout((Activity) mContext, "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                }
                            }else {
                                if (result.getStatus() == 1) {
                                    tv_courses_tilte.setText(result.getData().getCourseName());
                                    tv_courses_desc.setText(result.getData().getCourseDescription());
                                    name = result.getData().getCourseName();
                                    descrption = result.getData().getCourseDescription();
                                    price = result.getData().getPrice();
                                    Glide.with(mContext).load(Config.imageUrl + result.getData().getAvatarPath()).into(iv_courses_thumbnail);
                                } else {
                                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                                }
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