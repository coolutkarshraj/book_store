package com.io.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.model.InstituteModel;
import com.io.bookstore.model.insituteModel.InsituiteDataModel;
import com.io.bookstore.model.insituteModel.InsituiteResponseModel;

import java.util.List;

public class InstitutesAdapter extends RecyclerView.Adapter<InstitutesAdapter.MyViewHolder> {

    private Context mContext ;
    private List<InsituiteDataModel> mData ;


    public InstitutesAdapter(Context mContext, List<InsituiteDataModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
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
     /*   holder.tv_institute_desc.setText(mData.get(position).getDescription());
        holder.iv_institute_thumbnail.setImageResource(mData.get(position).getThumbnail());*/

        Glide.with(mContext).load(Config.imageUrl + model.getAvatarPath()).into(holder.iv_institute_thumbnail);
     /*   holder.fav_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.fav_white.getId())
                {
                    case R.id.iv_fav_white:
                        holder.fav_white.setImageResource(R.drawable.ic_favorite_blue_24dp);
                        holder.fav_white.setId(R.id.book_title_id);
                        break;
                    default:
                        holder.fav_white.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        holder.fav_white.setId(R.id.iv_fav_white);
                }
            }
        });
        /*
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,Book_Activity.class);

                // passing data to the book activity
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Description",mData.get(position).getDescription());
                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                // start the activity
                mContext.startActivity(intent);

            }
            */
        // });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_institute_title,tv_institute_desc;
        ImageView iv_institute_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_institute_title = (TextView) itemView.findViewById(R.id.tv_institute_title);
            tv_institute_desc =  (TextView) itemView.findViewById(R.id.tv_institute_desc) ;
            iv_institute_thumbnail = (ImageView) itemView.findViewById(R.id.iv_institute_thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_institute);

        }
    }


}