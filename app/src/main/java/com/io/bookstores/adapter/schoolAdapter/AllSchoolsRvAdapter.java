package com.io.bookstores.adapter.schoolAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.StaticData;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.holder.SchoolsHolder;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.schoolDetial.Data;
import com.io.bookstores.model.schoolDetial.SchoolDetailResponseModel;
import com.io.bookstores.model.schoolModel.GetAllSchoolDataModel;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;


public class AllSchoolsRvAdapter extends RecyclerView.Adapter<SchoolsHolder> {
    private Activity activity;
    private List<GetAllSchoolDataModel> list;
    private ItemClickListner itemClickListner;
    private userOnlineInfo user;
    LocalStorage localStorage;


    public AllSchoolsRvAdapter(Activity activity, List<GetAllSchoolDataModel> list) {
        this.activity = activity;
        this.list = list;

    }

    @Override
    public SchoolsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_school_list, parent, false);
        user = new userOnlineInfo();
        localStorage = new LocalStorage(activity);
        return new SchoolsHolder(view);
    }

    @Override
    public void onBindViewHolder(SchoolsHolder holder, final int position) {
        final GetAllSchoolDataModel model = list.get(position);

        if (localStorage.getString(LocalStorage.islanguage).equals("kuwait")) {
            holder.tvSchoolName.setText(model.getArabicName());
            holder.tvSchoolDesc.setText(model.getArabicDescription());
        } else {
            holder.tvSchoolName.setText(model.getSchoolName());
            holder.tvSchoolDesc.setText(model.getDescription());
        }


        Glide.with(activity).load(Config.imageUrl + model.getAvatarPath()).into(holder.iv_school_logo);
        holder.btn_browese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner = (ItemClickListner) activity;
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.putString(LocalStorage.schoolId, String.valueOf(model.getSchoolId()));
                localStorage.putString(LocalStorage.TYPE, "school");

                itemClickListner.onClick(8);
            }
        });
        holder.iv_school_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(model.getSchoolId());
            }
        });
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(model.getSchoolId());
            }
        });

    }


    public void setFilter(List<GetAllSchoolDataModel> newlist) {
        list = new ArrayList<>();
        list.addAll(newlist);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private void dialog(int schoolId) {
        if (user.isOnline(activity)) {

            ApiCaller.getSchoolDetailApi(activity, Config.Url.schoolDetail + schoolId,
                    new FutureCallback<SchoolDetailResponseModel>() {

                        @Override
                        public void onCompleted(Exception e, SchoolDetailResponseModel result) {
                            if (e != null) {

                                Utils.showAlertDialog(activity, "Something Went Wrong");
                            }
                            if (result != null) {

                                if (result.isStatus()) {

                                    schoolDetailDialog(result.getData());
                                }
                            }
                        }
                    });
        } else {
            Utils.showAlertDialog(activity, "No Internet Connection");
        }
    }

    private void schoolDetailDialog(final Data data) {
        final Dialog dialog = new Dialog(activity,R.style.dialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_school_detail);
        dialog.setTitle("");
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);
        final Button btn_browse = (Button) dialog.findViewById(R.id.btn_browse);
        final Button btn_dismiss = (Button) dialog.findViewById(R.id.btn_dismiss);


        final TextView tv_school_name = (TextView) dialog.findViewById(R.id.tv_school_name);
        final TextView tv_school_Description = (TextView) dialog.findViewById(R.id.tv_school_Description);
        final TextView tv_addess = (TextView) dialog.findViewById(R.id.tv_addess);

        tv_school_name.setText(data.getSchoolName());
        tv_school_Description.setText(data.getDescription());
        tv_addess.setText(data.getAddress().getAddress());


        btn_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                itemClickListner = (ItemClickListner) activity;
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.putString(LocalStorage.schoolId, String.valueOf(data.getSchoolId()));
                localStorage.putString(LocalStorage.TYPE, "school");

                itemClickListner.onClick(8);
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
