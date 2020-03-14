package com.io.bookstores.adapter.admin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.apicaller.ApiCaller;
import com.io.bookstores.listeners.RecyclerViewClickListener;
import com.io.bookstores.localStorage.LocalStorage;
import com.io.bookstores.model.adminResponseModel.AdminBookDataModel;
import com.io.bookstores.model.adminResponseModel.DeleteBookResponseModel;
import com.io.bookstores.utility.NewProgressBar;
import com.io.bookstores.utility.Utils;
import com.io.bookstores.utility.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdminBookListAdapter extends
        RecyclerView.Adapter<AdminBookListAdapter.MyViewHolder> {

    private Context mContext;
    private List<AdminBookDataModel> mData;
    LocalStorage localStorage;
    userOnlineInfo user;
    NewProgressBar dialog;
    Dialog dialogs;
    private RecyclerViewClickListener item;


    public AdminBookListAdapter(Context mContext, List<AdminBookDataModel> mData, RecyclerViewClickListener itemClickListner) {
        this.mContext = mContext;
        this.mData = mData;
        this.item = itemClickListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        boolean flag = false;
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.book_list_item, parent, false);
        localStorage = new LocalStorage(mContext);
        user = new userOnlineInfo();
        dialogs = new Dialog(mContext);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final AdminBookDataModel model = mData.get(position);

        holder.tv_BookName.setText(mData.get(position).getName());
        holder.tv_book_desc.setText(mData.get(position).getDescription());
        holder.tv_price.setText("Price-:"+String.valueOf(mData.get(position).getPrice())+"KD");
        holder.tv_author.setText("Author-:"+mData.get(position).getAuthor());


        Glide.with(mContext).load(Config.imageUrl + model.getAvatarPath()).into(holder.iv_bookstore_thumbnail);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBookApiCall(localStorage.getString(LocalStorage.token), model.getBookId(), position);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.onClickPosition(position);
            }
        });
    }
    public void setFilter(List<AdminBookDataModel> newlist){
        mData=new ArrayList<>();
        mData.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_BookName, tv_book_desc,tv_price,tv_author;
        ImageView iv_bookstore_thumbnail;
        CardView cardView;
        Button btnEdit, btnDelete;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_BookName = (TextView) itemView.findViewById(R.id.tv_bookName);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_book_desc = (TextView) itemView.findViewById(R.id.tv_bookDesc);
            iv_bookstore_thumbnail = (ImageView) itemView.findViewById(R.id.iv_bookstore_thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.cardview_item_bookstore);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.button4);

        }
    }

    private void deleteBookApiCall(String token, Integer bookId, final int position) {

        if (user.isOnline(mContext)) {
            dialog = new NewProgressBar(mContext);
            dialog.show();

            ApiCaller.deleteBook(mContext, Config.Url.adminDeleteBook+"/"+ bookId , localStorage.getString(LocalStorage.token),
                    new FutureCallback<DeleteBookResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, DeleteBookResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Utils.showAlertDialog((Activity) mContext, "Something Went Wrong");
                                return;
                            }

                            if(result != null){
                                if(result.getStatus()== null){
                                    if(result.getMessage().equals("Unauthorized")){
                                        Utils.showAlertDialogAdminLogout((Activity) mContext, "Your Session was expire. please Logout!",localStorage.getUserProfile().getData().getUser().getUserId());
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                }else {
                                    if (result.getStatus() == true) {
                                        dialog.dismiss();
                                        Toast.makeText(mContext, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        mData.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeRemoved(position, mData.size());
                                    } else {
                                        Toast.makeText(mContext, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }

                                }
                            }


                        }
                    });

        } else {
            Utils.showAlertDialog((Activity) mContext, "No Internet Connection");

        }
    }






}