package com.io.bookstore.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.apicaller.ApiCaller;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.BookModel;
import com.io.bookstore.model.loginModel.LoginModel;
import com.io.bookstore.model.wishlistModel.AddorRemoveWishlistResponseModel;
import com.io.bookstore.model.wishlistModel.GetWishListDataModel;
import com.io.bookstore.utility.NewProgressBar;
import com.io.bookstore.utility.Utils;
import com.io.bookstore.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.List;

public class FavoriteItemsAdapter extends RecyclerView.Adapter<FavoriteItemsAdapter.MyViewHolder> {

    private Context mContext;
    private List<GetWishListDataModel> mData;
    userOnlineInfo user;
    NewProgressBar dialog;
    private LocalStorage localStorage;
    private LoginModel loginModel;

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
        user = new userOnlineInfo();

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GetWishListDataModel model = mData.get(position);


        Glide.with(mContext).load(Config.imageUrl + model.getAvatarPath()).into(holder.img_book_thumbnail);

        holder.clayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeWishListItem(model.getBookId(),position);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeWishListItem(model.getBookId(),position);
            }
        });

    }

    private void removeWishListItem(Integer bookId, final int position) {
        if (user.isOnline(mContext)) {
            dialog = new NewProgressBar(mContext);
            dialog.show();
            LocalStorage localStorage = new LocalStorage(mContext);
            ApiCaller.addOrRemoveWishList((Activity) mContext, Config.Url.addorremoveWishlist + bookId, localStorage.getString(LocalStorage.token),
                    new FutureCallback<AddorRemoveWishlistResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, AddorRemoveWishlistResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog((Activity) mContext, "Something Went Wrong");
                                return;
                            }
                            if (result.getStatus() == true) {
                                Toast.makeText(mContext, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                mData.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeRemoved(position, mData.size());
                                dialog.dismiss();
                            } else {
                                Toast.makeText(mContext, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        }
                    });

        } else {
            Utils.showAlertDialog((Activity) mContext, "No Internet Connection");
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // TextView tv_book_price;
        ImageView img_book_thumbnail;
        CardView cardView;
        ConstraintLayout clayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            //  tv_book_price = (TextView) itemView.findViewById(R.id.tv_book_price) ;
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.iv_favorite);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id_item);
            clayout = (ConstraintLayout) itemView.findViewById(R.id.clayout);

        }
    }


}