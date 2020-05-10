package com.io.bookstores.adapter.courseAdapter;

import android.app.Activity;
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
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.fragment.courseFragment.AllCoursesListFragment;
import com.io.bookstores.listeners.ItemClickListner;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.insituteModel.InsituiteDataModel;
import com.io.bookstores.model.instituteDetial.InsituiteDetialResponseModel;
import com.io.bookstores.utility.Utils;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

public class InstitutesAdapter extends RecyclerView.Adapter<InstitutesAdapter.MyViewHolder> {

    private Context mContext ;
    private List<InsituiteDataModel> mData ;
    private ItemClickListner itemClickListner;
    AllCoursesListFragment allCoursesListFragment;
    String strName;
    String strAddress;
    String strState;
    String strCity;
    LocalStorage localStorage;

    private RecyclerViewClickListener item;


    public InstitutesAdapter(Context mContext, List<InsituiteDataModel> mData, RecyclerViewClickListener item) {
        this.mContext = mContext;
        this.mData = mData;
        this.item = item;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean flag=false;
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_institute,parent,false);
        localStorage = new LocalStorage(mContext);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InsituiteDataModel model = mData.get(position);
       // holder.cardView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        if (localStorage.getString(LocalStorage.islanguage).equals("kuwait")) {
            holder.tv_institute_title.setText(model.getArabicName());
            holder.tv_institute_desc.setText(model.getArabicDescription());
        } else {
            holder.tv_institute_title.setText(model.getInstituteName());
            holder.tv_institute_desc.setText(model.getDescription());
        }


        holder.bv_institute_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.onClickPosition(position);

            }
        });
        holder.bv_institute_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCall(mData.get(position).getInstituteId());
            }
        });




        Glide.with(mContext).load(Config.imageUrl + model.getAvatarPath()).into(holder.iv_institute_thumbnail);

    }

    private void apiCall(Integer instituteId) {
        ApiCaller.getInstuteDetial((Activity) mContext, Config.Url.insituteDetial+instituteId,
                new FutureCallback<InsituiteDetialResponseModel>() {

                    @Override
                    public void onCompleted(Exception e, InsituiteDetialResponseModel result) {
                        if (e != null) {
                            Utils.showAlertDialog((Activity) mContext, "Something Went Wrong");
                        }
                        if (result != null) {
                            if (result.getStatus()) {
                                strName = result.getData().getInstituteName();
                                strAddress = result.getData().getAddress().getAddress();
                                strCity = result.getData().getAddress().getCity();
                                strState = result.getData().getAddress().getState();
                                dialogadd();
                            }
                        }
                        ;


                    }
                });
    }

    private void dialogadd() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_institue_location);
        dialog.setTitle("");
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.clear);
        final Button btn_Add = (Button) dialog.findViewById(R.id.btn_Add);
        //final Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final TextView etAddress = (TextView) dialog.findViewById(R.id.et_address);
        final TextView etCity = (TextView) dialog.findViewById(R.id.et_city);
        final TextView etState = (TextView) dialog.findViewById(R.id.et_state);
        final TextView etPinCode = (TextView) dialog.findViewById(R.id.et_pincode);
        etAddress.setText(strName);
        etState.setText(strState);
        etCity.setText(strCity);
        etPinCode.setText(strAddress);

        btn_Add.setOnClickListener(new View.OnClickListener() {
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

    public void setFilter(List<InsituiteDataModel> newlist){
        mData=new ArrayList<>();
        mData.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_institute_title,tv_institute_desc;
        ImageView iv_institute_thumbnail;
        Button bv_institute_browse, bv_institute_location;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_institute_title = (TextView) itemView.findViewById(R.id.tv_institute_title);
            tv_institute_desc =  (TextView) itemView.findViewById(R.id.tv_institute_desc) ;
            iv_institute_thumbnail = (ImageView) itemView.findViewById(R.id.iv_institute_thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_institute);
            bv_institute_browse = (Button) itemView.findViewById(R.id.bv_institute_browse);
            bv_institute_location = (Button) itemView.findViewById(R.id.bv_institute_location);

        }
    }


}