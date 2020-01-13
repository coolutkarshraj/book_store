package com.io.bookstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.model.BookModel;
import com.io.bookstore.model.wishlistModel.GetWishListDataModel;

import java.util.List;

public class FavoriteItemsAdapter extends RecyclerView.Adapter<FavoriteItemsAdapter.MyViewHolder> {

    private Context mContext;
    private List<GetWishListDataModel> mData;


    public FavoriteItemsAdapter(Context mContext, List<GetWishListDataModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean flag = false;
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_favorite, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        GetWishListDataModel model = mData.get(position);


        Glide.with(mContext).load(Config.imageUrl +model.getAvatarPath()).into(holder.img_book_thumbnail);
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

        // TextView tv_book_price;
        ImageView img_book_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            //  tv_book_price = (TextView) itemView.findViewById(R.id.tv_book_price) ;
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.iv_favorite);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id_item);

        }
    }


}