package com.io.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.fragment.CoursesFragment;
import com.io.bookstore.fragment.InstituteFragment;
import com.io.bookstore.listeners.ItemClickListner;
import com.io.bookstore.listeners.RecyclerViewClickListener;
import com.io.bookstore.model.InstituteModel;
import com.io.bookstore.model.insituteModel.InsituiteDataModel;
import com.io.bookstore.model.insituteModel.InsituiteResponseModel;

import java.util.List;

public class InstitutesAdapter extends RecyclerView.Adapter<InstitutesAdapter.MyViewHolder> {

    private Context mContext ;
    private List<InsituiteDataModel> mData ;
    private ItemClickListner itemClickListner;
    CoursesFragment coursesFragment;
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
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InsituiteDataModel model = mData.get(position);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        holder.tv_institute_title.setText(model.getInstituteName());

        holder.bv_institute_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.onClickPosition(position);

            }
        });
        Glide.with(mContext).load(Config.imageUrl + model.getAvatarPath()).into(holder.iv_institute_thumbnail);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_institute_title,tv_institute_desc;
        ImageView iv_institute_thumbnail;
        Button bv_institute_browse;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_institute_title = (TextView) itemView.findViewById(R.id.tv_institute_title);
            tv_institute_desc =  (TextView) itemView.findViewById(R.id.tv_institute_desc) ;
            iv_institute_thumbnail = (ImageView) itemView.findViewById(R.id.iv_institute_thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_institute);
            bv_institute_browse = (Button) itemView.findViewById(R.id.bv_institute_browse);

        }
    }


}