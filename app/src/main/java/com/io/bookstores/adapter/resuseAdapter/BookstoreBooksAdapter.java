package com.io.bookstores.adapter.resuseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.model.BookModel;

import java.util.List;

public class BookstoreBooksAdapter extends RecyclerView.Adapter<BookstoreBooksAdapter.MyViewHolder> {

    private Context mContext ;
    private List<BookModel> mData ;


    public BookstoreBooksAdapter(Context mContext, List<BookModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean flag=false;
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        int i =(mData.get(position).getPrice());
        holder.tv_book_price.setText(Integer.toString(i));
        holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.fav_white.setOnClickListener(new View.OnClickListener() {
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

        TextView tv_book_price;
        ImageView img_book_thumbnail, fav_white;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_price = (TextView) itemView.findViewById(R.id.tv_book_price) ;
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            fav_white = (ImageView) itemView.findViewById(R.id.iv_fav_white);


        }
    }


}