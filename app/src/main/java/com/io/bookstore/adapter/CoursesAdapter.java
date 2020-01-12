package com.io.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;
import com.io.bookstore.model.CourseModel;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {

    private Context mContext ;
    private List<CourseModel> mData ;


    public CoursesAdapter(Context mContext, List<CourseModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
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
        holder.tv_courses_tilte.setText(mData.get(position).getTitle());
        holder.tv_courses_desc.setText(mData.get(position).getDescription());
        holder.iv_courses_thumbnail.setImageResource(mData.get(position).getThumbnail());
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

        TextView tv_courses_tilte,tv_courses_desc;
        ImageView iv_courses_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_courses_tilte = (TextView) itemView.findViewById(R.id.tv_course_tilte);
            tv_courses_desc =  (TextView) itemView.findViewById(R.id.tv_course_desc) ;
            iv_courses_thumbnail = (ImageView) itemView.findViewById(R.id.iv_course_thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_course);

        }
    }


}